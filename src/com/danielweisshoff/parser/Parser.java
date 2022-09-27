package com.danielweisshoff.parser;

import java.util.Stack;

import com.danielweisshoff.interpreter.builtin.functions.BuiltInFunction;
import com.danielweisshoff.lexer.Token;
import com.danielweisshoff.lexer.TokenType;
import com.danielweisshoff.logger.Logger;
import com.danielweisshoff.parser.PError.*;
import com.danielweisshoff.parser.nodesystem.Data;
import com.danielweisshoff.parser.nodesystem.DataType;
import com.danielweisshoff.parser.nodesystem.node.*;
import com.danielweisshoff.parser.nodesystem.node.binaryoperations.*;
import com.danielweisshoff.parser.nodesystem.node.data.*;
import com.danielweisshoff.parser.nodesystem.node.data.assigning.*;
import com.danielweisshoff.parser.nodesystem.node.data.assigning.shortcuts.*;
import com.danielweisshoff.parser.nodesystem.node.data.primitives.*;
import com.danielweisshoff.parser.nodesystem.node.logic.*;
import com.danielweisshoff.parser.nodesystem.node.logic.conditions.*;
import com.danielweisshoff.parser.nodesystem.node.loops.*;
import com.danielweisshoff.parser.semantic.ConversionChecker;
import com.danielweisshoff.parser.symboltable.SymbolTableManager;
import com.danielweisshoff.parser.symboltable.VariableEntry;

//TODO addInstruction schlecht gelöst
/**
 * Converts tokens to a runnable AST
 */
public class Parser {

    public static boolean debug = true;

    private BlockNode root;

    private Token curToken;
    private int position = -1;

    private Token[] tokens;

    private boolean error = false;
    private boolean lock = false; //if instructions are added or not

    //For block building
    private Stack<BlockNode> scopeNode = new Stack<>();
    private int scopeDepth = 0;

    //variables
    private SymbolTableManager stm = new SymbolTableManager();

    public Parser() {
        BuiltInFunction.registerAll();
        root = new BlockNode();
        scopeNode.add(root);
    }

    public void parseBlock() {

    }

    public Node parseInstruction(Token[] tokens) {
        this.tokens = tokens;
        position = -1;
        advance();

        Node instruction = null;

        //if tab count is reduced, scope out
        tryScopeOut();

        switch (curToken.type()) {
        case KW_IF -> instruction = parseIf();
        case KW_ELSE -> instruction = parseElse();
        case KW_ELIF -> instruction = parseElif();
        //PRIMITIVES
        case KW_BYTE, KW_SHORT, KW_INT, KW_LONG, KW_FLOAT, KW_DOUBLE -> {
            if (next(TokenType.STAR)) {
                if (next(3, TokenType.EQUAL))
                    instruction = parsePtrInitialization();
                else
                    instruction = parsePtrDeclaration();
            }
            // VAR_INITIALIZATION
            else if (next(2, TokenType.EQUAL))
                instruction = parseVarInitialization();
            // ARRAY_INITIALIZATION
            else if (next(2, TokenType.O_BLOCK_BRACKET)) {
                instruction = parseArrayInitialization();
            }
            // VAR_DECLARATION
            else
                instruction = parseVarDeclaration();
        }
        // LOOPS
        case KW_FOR -> instruction = parseFor();
        case KW_DO -> instruction = parseDoWhile();
        case KW_WHILE -> instruction = parseWhile();
        //
        case IDENTIFIER -> {
            //FUNCTION
            if (next(TokenType.O_ROUND_BRACKET))
                instruction = parseFunctionCall();
            // x = EXPR
            else if (next(TokenType.EQUAL))
                instruction = parseVarDefinition();
            else if (next(TokenType.MINUS))
                instruction = parsePostDecrement(true);
            else if (next(TokenType.PLUS))
                instruction = parsePostIncrement(true);
            else if (next(TokenType.O_BLOCK_BRACKET)) {
                if (next(4, TokenType.EQUAL))
                    instruction = parseArraySetField();
                else
                    instruction = parseArrayGetField();

            }
        }
        //
        case PLUS -> instruction = parsePreIncrement(true);
        case MINUS -> instruction = parsePreDecrement(true);
        case STAR -> instruction = parsePtrDefinition();
        case EOF -> {
            return null;
        }
        default -> new UnimplementedError("[PARSER] Action for Token " + curToken.type() + " not implemented",
                curToken);
        }

        endOfInstruction();

        if (error)
            new UnimplementedError("Unknown instruction:\n", curToken);

        return instruction;
    }

    private void addInstruction(Node instruction) {
        if (!lock)
            scopeNode.peek().add(instruction);

        if (debug) {
            String nodeName = instruction.getClass().getSimpleName();
            String tableName = stm.getCurrentTable().getName();
            Logger.log(nodeName + " added to scope " + tableName);
        }
    }

    private void advance() {
        advance(1);
    }

    private void advance(int steps) {
        for (int i = 0; i < steps; i++) {
            position++;
            if (position < tokens.length)
                curToken = tokens[position];
            else {
                curToken = new Token(TokenType.EOL, "End of Line", -1, -1, -1);
                break;
            }
        }
    }

    private void retreat() {
        retreat(1);
    }

    private void retreat(int times) {
        for (int i = 0; i < times; i++)
            position--;

        if (position > -1)
            curToken = tokens[position];
    }

    /**
     * Vergleicht den aktuellen Token
     */
    public boolean is(TokenType type) {
        return curToken.type() == type;
    }

    public boolean is(String value) {
        return is(value);
    }

    /**
     * Check, if current token type equals r, if not print error
     */
    private void assume(TokenType t, String error) {
        if (is(t))
            advance();
        else
            new UnimplementedError(error, curToken);
    }

    private Token next() {
        return next(1);
    }

    private Token next(int lookahead) {
        if (position < tokens.length - lookahead)
            return tokens[position + lookahead];
        //TODO col and pos weird
        return new Token(TokenType.EOL, "", -1, -1, -1);
    }

    private boolean next(int lookahead, TokenType t) {
        return next(lookahead).type() == t;
    }

    private boolean next(TokenType t) {
        return next().type() == t;
    }

    private void endOfInstruction() {
        if (position < tokens.length)
            new ExpectedInstructionEndError(tokensToEnd());
    }

    private Token[] tokensToEnd() {
        Token[] toks = new Token[tokens.length - position];
        int counter = 0;
        for (int i = position; i < tokens.length; i++)
            toks[counter++] = tokens[i];

        return toks;
    }

    //returns the latest IfNode in the present scope
    //that doesnt have an else-block
    private IfNode findIfWithoutElseBlock(BlockNode n) {
        //get the latest IfNode from current scope
        IfNode in = null;
        for (int i = n.children.size() - 1; i >= 0; i--)
            if (n.children.get(i).nodeType == NodeType.IF_NODE) {
                in = (IfNode) n.children.get(i);
                break;
            }
        if (in == null)
            new UnimplementedError("else statement doesnt have corresponding if", curToken);

        return in.elseBlock == null ? in : findIfWithoutElseBlock(in.elseBlock);
    }

    private void tryScopeOut() {
        int curScope = 0;
        if (is(TokenType.TAB)) {
            curScope = Integer.parseInt(curToken.value);
            advance();
        }
        //Rausscopen, falls noetig
        if (curScope < scopeDepth) {
            int diff = scopeDepth - (curScope);
            scopeOut(diff);
        }
    }

    private BlockNode scopeIn(String scope) {
        BlockNode bn = new BlockNode();
        stm.newScope(scope);
        scopeDepth++;
        scopeNode.add(bn);

        return bn;
    }

    private void scopeOut(int amount) {
        for (int i = 0; i < amount; i++) {
            stm.endScope();
            scopeNode.pop();
            scopeDepth--;
        }
    }

    public void printSymbolTable() {
        stm.print();
    }

    //TODO in Helferklasse auslagern
    private DataType getPrimitiveType(TokenType keyword) {
        DataType type = null;
        switch (keyword) {
        case KW_BYTE -> type = DataType.BYTE;
        case KW_SHORT -> type = DataType.SHORT;
        case KW_INT -> type = DataType.INT;
        case KW_LONG -> type = DataType.LONG;
        case KW_FLOAT -> type = DataType.FLOAT;
        case KW_DOUBLE -> type = DataType.DOUBLE;
        default -> new UnimplementedError("parser: unknown primitive type " + keyword, curToken);
        }
        return type;
    }
    /*
     * Parsing Methoden
     * 
     */

    private IfNode parseIf() {
        assume(TokenType.KW_IF, "Keyword IF missing");
        assume(TokenType.O_ROUND_BRACKET, "Parameterlist not found");

        ConditionNode condition = parseBool();

        assume(TokenType.C_ROUND_BRACKET, "Parameterlist not closed");
        assume(TokenType.COLON, "if-block missing");

        IfNode in = new IfNode(condition);

        addInstruction(in);
        in.condBlock = scopeIn("if-body");

        return in;
    }

    private IfNode parseElif() {
        assume(TokenType.KW_ELIF, "Keyword ELIF missing");

        IfNode in = findIfWithoutElseBlock(scopeNode.peek());
        in.elseBlock = scopeIn("else-body");

        assume(TokenType.O_ROUND_BRACKET, "Parameterlist not found");

        ConditionNode condition = parseBool();

        assume(TokenType.C_ROUND_BRACKET, "Parameterlist not closed");
        assume(TokenType.COLON, "elif-block missing");

        IfNode elif = new IfNode(condition);

        addInstruction(elif);
        elif.condBlock = scopeIn("elif-body");

        in.elseBlock.add(elif);

        return elif;
    }

    private IfNode parseElse() {
        assume(TokenType.KW_ELSE, "Keyword ELSE missing");
        IfNode in = findIfWithoutElseBlock(scopeNode.peek());

        assume(TokenType.COLON, "unknown syntax for else statement");

        in.elseBlock = scopeIn("else-body");
        return in;
    }

    private ConditionNode parseBool() {
        ConditionNode left = parsePredicate();

        ConditionNode cn = null;

        while (is(TokenType.AND) || is(TokenType.OR)) {
            // PREDICATE && PREDICATE
            if (is(TokenType.AND) && next(TokenType.AND)) {
                advance(2);
                cn = new BooleanAndNode();
            }
            //PREDICATE || PREDICATE
            else if (is(TokenType.OR) && next(TokenType.OR)) {
                advance(2);
                cn = new BooleanOrNode();
            }

            ConditionNode right = parsePredicate();
            cn.left = left;
            cn.right = right;
            left = cn;
        }
        return left;
    }

    private ConditionNode parsePredicate() {

        switch (curToken.type()) {
        case O_ROUND_BRACKET:
            advance();
            ConditionNode n = parseBool();
            assume(TokenType.C_ROUND_BRACKET, "Expression error. Bracket not properly closed");
            return n;
        case KW_TRUE:
            advance();
            return new TrueNode();
        case KW_FALSE:
            advance();
            return new FalseNode();
        default:
            Node leftExpr = parseExpression();
            Token compareType = curToken;
            advance();
            Node rightExpr = parseExpression();

            return switch (compareType.value) {
            case "<" -> new LessNode(leftExpr, rightExpr);
            case "<=" -> new LessEqualNode(leftExpr, rightExpr);
            case ">" -> new MoreNode(leftExpr, rightExpr);
            case ">=" -> new MoreEqualNode(leftExpr, rightExpr);
            case "==" -> new EqualNode(leftExpr, rightExpr);
            case "!=" -> new NotEqualNode(leftExpr, rightExpr);
            default -> null;
            };
        }
    }

    private NumberNode parseExpression() {
        NumberNode left = parseTerm();

        BinaryOperationNode op = null;

        while (curToken.isLineOP()) {

            if (curToken.type() == TokenType.PLUS)
                op = new BinaryAddNode();
            else if (curToken.type() == TokenType.MINUS)
                op = new BinarySubNode();

            advance();

            NumberNode right = parseTerm();
            op.left = left;
            op.right = right;
            left = op;
        }
        return left;
    }

    private NumberNode parseTerm() {
        NumberNode left = parseFactor();

        BinaryOperationNode op = null;
        while (curToken.isDotOP() || is(TokenType.PERCENT)) {

            if (is(TokenType.STAR))
                op = new BinaryMulNode();
            else if (is(TokenType.SLASH))
                op = new BinaryDivNode();
            else if (is(TokenType.PERCENT))
                op = new BinaryModNode();

            advance();

            NumberNode right = parseFactor();
            op.left = left;
            op.right = right;
            left = op;
        }
        return left;
    }

    private NumberNode parseFactor() {

        NumberNode n = null;
        switch (curToken.type()) {
        case INTEGER:
            n = parseInteger(curToken.value);
            advance();
            break;
        case FLOATING_POINT:
            n = parseFloatingPoint(curToken.value);
            advance();
            break;
        case MINUS:
            advance();
            switch (curToken.type()) {
            // - DIGIT
            case INTEGER:
                n = parseInteger('-' + curToken.value);
                advance();
                break;
            // - DIGIT
            case FLOATING_POINT:
                n = parseFloatingPoint('-' + curToken.value);
                advance();
                break;
            // - ( EXPR )
            case O_ROUND_BRACKET:
                advance();
                n = parseExpression();
                assume(TokenType.C_ROUND_BRACKET, "Expression error. Bracket not properly closed");
                break;
            // - ID
            case IDENTIFIER:
                String varName = curToken.value;
                advance();
                n = stm.findVariable(varName).node;
                break;
            // - - ID
            case MINUS:
                retreat();
                n = parsePreDecrement(false);
                break;
            default:
                new UnimplementedError("error parsing factor with -", curToken);
            }
            break;
        case PLUS:
            // + + ID	
            n = parsePreIncrement(false);
            break;
        case O_ROUND_BRACKET:
            // ( EXPR )
            advance();

            n = parseExpression();
            assume(TokenType.C_ROUND_BRACKET, "Expression error. Bracket not properly closed");
            break;
        case IDENTIFIER:
            // ID - -	
            if (next(TokenType.MINUS) && next(2, TokenType.MINUS))
                n = parsePostDecrement(false);
            // ID + +
            else if (next(TokenType.PLUS) && next(2, TokenType.PLUS))
                n = parsePostIncrement(false);
            // ID [ EXPR ]
            else if (next(TokenType.O_BLOCK_BRACKET)) {
                n = parseArrayGetField();
                // ID
            } else {
                String varName = curToken.value;
                advance();
                n = stm.findVariable(varName).node;
            }
            break;
        default:
            new UnimplementedError("parseFactor()", curToken);
        }
        return n;
    }

    //TODO move to semantics
    //converts an Integer Number into the best fitting primitive
    private PrimitiveNode parseInteger(String value) {
        //? byte
        if (ConversionChecker.isByte(value))
            return new ByteNode(Byte.parseByte(value));
        //? short
        else if (ConversionChecker.isShort(value))
            return new ShortNode(Short.parseShort(value));
        //? int
        else if (ConversionChecker.isInt(value))
            return new IntegerNode(Integer.parseInt(value));
        //? long
        else if (ConversionChecker.isLong(value))
            return new LongNode(Long.parseLong(value));
        else {
            new UnimplementedError(value + " is not an Integer?!", curToken);
            return null;
        }
    }

    //TODO move to semantics
    //converts a Floating-Point Number into the best fitting primitive
    private PrimitiveNode parseFloatingPoint(String value) {
        //? float
        if (ConversionChecker.isFloat(value))
            return new FloatNode(Float.parseFloat(value));
        //? double
        else if (ConversionChecker.isDouble(value))
            return new DoubleNode(Double.parseDouble(value));
        else {
            new UnimplementedError(value + " is not a Floating Point Number?!", curToken);
            return null;
        }
    }

    private CallNode parseFunctionCall() {
        String name = curToken.value;
        assume(TokenType.IDENTIFIER, "Functionname missing");

        assume(TokenType.O_ROUND_BRACKET, "Parameterlist not opened");

        String params = parseParameters();

        assume(TokenType.C_ROUND_BRACKET, "Parameterlist not closed");

        CallNode cn = new CallNode(name);
        addInstruction(cn);

        return cn;
    }

    private String parseParameters() {
        String params = "";

        while (!is(TokenType.C_ROUND_BRACKET)) {
            if (is(TokenType.INTEGER) || is(TokenType.FLOATING_POINT) || is(TokenType.IDENTIFIER)) {
                params += curToken.value;
                advance();
                if (is(TokenType.COMMA)) {
                    params += ", ";
                    advance();
                } else
                    break;
            }
        }
        return params;
    }

    private DeclareNode parseVarDeclaration() {
        TokenType keyword = curToken.type();
        advance();

        //getting the primitive type
        DataType type = getPrimitiveType(keyword);

        String name = curToken.value;
        assume(TokenType.IDENTIFIER, "Fehler beim Deklarieren einer Variable");
        DeclareNode dn = new DeclareNode(name, type);

        //schauen, ob variable schon existiert
        if (stm.lookupVariable(name))
            new UnimplementedError("var '" + name + "': " + type + " is already declared", curToken);

        //Variable eintragen
        long id = IdRegistry.newID();
        VariableNode vn = new VariableNode(name, type);
        vn.data = new Data();

        VariableEntry ve = new VariableEntry(name, id, vn);
        stm.addVariable(id, ve);

        addInstruction(dn);
        return dn;
    }

    private AssignNode parseVarDefinition() {
        if (is(TokenType.MINUS))
            return parsePreDecrement(false);
        else if (is(TokenType.PLUS))
            return parsePreIncrement(false);

        String varName = curToken.value;
        assume(TokenType.IDENTIFIER, "var for definition missing");

        //check if var is already defined
        if (!stm.lookupVariable(varName))
            new UnimplementedError("var '" + varName + "' not declared", curToken);

        AssignNode an = null;
        Node expr = null;
        switch (curToken.type()) {
        // ID = EXPR
        case EQUAL:
            advance();
            expr = parseExpression();
            an = new DefineNode(varName, expr);
            an.expression = expr;
            break;
        // ID + =  EXPR || ID + +
        case PLUS:
            if (next(TokenType.PLUS)) {
                retreat();
                an = parsePostIncrement(false);
                break;
            }

            advance();
            assume(TokenType.EQUAL, "+=");

            expr = parseExpression();
            an = new DefineNode(varName, expr);
            an.expression = expr;
            break;
        // ID - =  EXPR || ID - -
        case MINUS:
            if (next(TokenType.MINUS)) {
                retreat();
                an = parsePostDecrement(false);
                break;
            }

            advance();
            assume(TokenType.EQUAL, "+=");

            expr = parseExpression();
            an = new DefineNode(varName, expr);
            an.expression = expr;
            break;
        // ID * =  EXPR
        case STAR:
            advance();
            assume(TokenType.EQUAL, "+=");

            expr = parseExpression();
            an = new DefineNode(varName, expr);
            an.expression = expr;
            break;
        // ID / =  EXPR
        case SLASH:
            advance();
            assume(TokenType.EQUAL, "+=");

            expr = parseExpression();
            an = new DefineNode(varName, expr);
            an.expression = expr;
            break;
        // ID % =  EXPR
        case PERCENT:
            advance();
            assume(TokenType.EQUAL, "+=");

            expr = parseExpression();
            an = new DefineNode(varName, expr);
            an.expression = expr;
            break;
        }

        addInstruction(an);
        return an;
    }

    private VarInitNode parseVarInitialization() {
        // DECLARATION = EXPR
        DeclareNode dn = parseVarDeclaration();

        assume(TokenType.EQUAL, "init '=' missing");

        NumberNode expr = parseExpression();
        stm.findVariable(dn.getName()).node = expr;

        VarInitNode in = new VarInitNode(dn.getName(), dn.dataType, expr);

        addInstruction(in);
        return in;
    }

    private DeclareNode parsePtrDeclaration() {
        // PRIMITIVE PTR

        TokenType keyword = curToken.type();
        //TODO getting the primitive type of the pointer
        //DataType type = getPrimitiveType(keyword);

        advance();
        String name = parsePointer().name;

        //schauen, ob variable schon existiert
        if (stm.lookupVariable(name))
            new UnimplementedError("var '" + name + "': " + DataType.POINTER + " is already declared", curToken);

        //Variable eintragen
        long id = IdRegistry.newID();
        PointerNode pn = new PointerNode(name, "nullptr", getPrimitiveType(keyword));
        VariableEntry ve = new VariableEntry(name, id, pn);
        stm.addVariable(id, ve);

        DeclareNode dn = new DeclareNode(name, DataType.POINTER);
        addInstruction(dn);
        return dn;
    }

    private PointerNode parsePtrDefinition() {
        PointerNode pn = parsePointer();
        switch (curToken.type()) {
        // PTR = ADRESS
        case EQUAL:
            advance();
            pn.adress = parseAdress();
            break;
        // PTR + = EXPR
        case PLUS:
            //TODO
            assume(TokenType.EQUAL, "pointer def = missing");
            parseExpression();
            break;
        //PTR - = EXPR
        case MINUS:
            //TODO
            assume(TokenType.EQUAL, "pointer def = missing");
            parseExpression();
            break;
        default:
            new UnimplementedError("error parsing pointer def", curToken);
        }

        addInstruction(pn);
        return pn;
    }

    private PtrInitNode parsePtrInitialization() {
        // PRIMITIVE PTR = ADRESS

        TokenType keyword = curToken.type();
        //TODO getting the primitive type of the pointer
        //DataType type = getPrimitiveType(keyword);

        advance();
        String name = parsePointer().name;

        //schauen, ob variable schon existiert
        if (stm.lookupVariable(name))
            new UnimplementedError("var '" + name + "': " + DataType.POINTER + " is already declared", curToken);

        assume(TokenType.EQUAL, "= sign missing for ptr init");

        String adress = parseAdress();
        VariableEntry var = stm.findVariable(adress);
        //Variable eintragen
        long id = IdRegistry.newID();
        PtrInitNode pn = new PtrInitNode(name, getPrimitiveType(keyword), adress);
        VariableEntry ve = new VariableEntry(name, id, pn);

        stm.addVariable(id, ve);
        addInstruction(pn);
        return pn;
    }

    //
    //LOOPS
    //

    private WhileNode parseWhile() {
        // while ( BOOL ) : BLOCK
        assume(TokenType.KW_WHILE, "Keyword WHILE missing");
        assume(TokenType.O_ROUND_BRACKET, "Missing open bracket for while-loop");

        ConditionNode cn = parseBool();

        assume(TokenType.C_ROUND_BRACKET, "Missing close bracket for while-loop");
        assume(TokenType.COLON, "while-body not defined");

        WhileNode wn = new WhileNode(cn);

        addInstruction(wn);
        wn.whileBlock = scopeIn("while-body");

        return wn;
    }

    private DoWhileNode parseDoWhile() {
        // do while ( BOOL ) : BLOCK
        assume(TokenType.KW_DO, "Keyword DO missing");
        assume(TokenType.KW_WHILE, "Keyword WHILE missing");
        assume(TokenType.O_ROUND_BRACKET, "Missing open bracket for while-loop");

        ConditionNode cn = parseBool();

        assume(TokenType.C_ROUND_BRACKET, "Missing close bracket for while-loop");
        assume(TokenType.COLON, "while-body not defined");

        DoWhileNode dwn = new DoWhileNode(cn);

        addInstruction(dwn);
        dwn.whileBlock = scopeIn("do-while-body");

        return dwn;
    }

    private ForNode parseFor() {
        // for ( INITIALIZATION , BOOL , DEFINITION ) : BLOCK
        ForNode fn = new ForNode();
        addInstruction(fn);

        scopeIn("for-init");

        assume(TokenType.KW_FOR, "Keyword FOR missing");
        assume(TokenType.O_ROUND_BRACKET, "Missing open bracket for for-loop");

        VarInitNode in = parseVarInitialization();
        addInstruction(in);

        assume(TokenType.COMMA, "comma missing");

        ConditionNode cn = parseBool();

        assume(TokenType.COMMA, "comma missing");

        AssignNode an = parseVarDefinition();

        assume(TokenType.C_ROUND_BRACKET, "Missing closed bracket for for-loop");
        assume(TokenType.COLON, "for-body-declarator missing");

        fn.init = in;
        fn.condition = cn;
        fn.assignment = an;

        fn.block = scopeIn("for-body");

        System.out.println("added for to " + stm.getCurrentTable().getName());
        return fn;
    }

    private AssignNode parsePostIncrement(boolean isStandalone) {
        // ID + +
        String varName = curToken.value;
        assume(TokenType.IDENTIFIER, "post-increment-assignment var missing");

        assume(TokenType.PLUS, "Incrementor + missing");
        assume(TokenType.PLUS, "Incrementor + missing");

        PostIncrementNode lin = new PostIncrementNode(varName);

        if (isStandalone)
            addInstruction(lin);
        return lin;
    }

    private AssignNode parsePostDecrement(boolean isStandalone) {
        // ID - -
        String varName = curToken.value;
        assume(TokenType.IDENTIFIER, "post-decrement-assignment var missing");

        assume(TokenType.MINUS, "Decrementor - missing");
        assume(TokenType.MINUS, "Decrementor - missing");

        System.out.println("currently at: " + curToken.type());
        PostDecrementNode ldn = new PostDecrementNode(varName);

        if (isStandalone)
            addInstruction(ldn);
        return ldn;
    }

    private AssignNode parsePreIncrement(boolean isStandalone) {
        // + + ID
        assume(TokenType.PLUS, "Expected + for incrementing");
        assume(TokenType.PLUS, "Expected + for incrementing");

        String varName = curToken.value;
        assume(TokenType.IDENTIFIER, "pre-increment-assignment var missing");

        PreIncrementNode in = new PreIncrementNode(varName);

        if (isStandalone)
            addInstruction(in);
        return in;
    }

    private AssignNode parsePreDecrement(boolean isStandalone) {
        // - - ID
        assume(TokenType.MINUS, "Expected - for decrementing");
        assume(TokenType.MINUS, "Expected - for decrementing");

        String varName = curToken.value;
        assume(TokenType.IDENTIFIER, "pre-decrement-assignment var missing");

        PreDecrementNode dn = new PreDecrementNode(varName);

        if (isStandalone)
            addInstruction(dn);
        return dn;
    }

    private PointerNode parsePointer() {
        // * ID
        assume(TokenType.STAR, "pointer * missing");
        String name = curToken.value;
        assume(TokenType.IDENTIFIER, "pointer name missing");

        return new PointerNode(name, null, null);
    }

    private String parseAdress() {
        // & ID
        assume(TokenType.AND, "adress & missing");
        String name = curToken.value;
        assume(TokenType.IDENTIFIER, "adress name missing");

        return name;
    }

    private ArrInitNode parseArrayInitialization() {
        // KW ID [ EXPR ]
        TokenType keyword = curToken.type();
        advance();

        String name = curToken.value;
        advance();

        assume(TokenType.O_BLOCK_BRACKET, "missing [ for index");
        NumberNode size = parseExpression();
        assume(TokenType.C_BLOCK_BRACKET, "missing ] for index");

        DataType dataType = getPrimitiveType(keyword);
        ArrInitNode an = new ArrInitNode(name, dataType, size);
        addInstruction(an);

        return an;
    }

    private Node parseArraySetField() {
        // ID [ EXPR ] = EXPR
        String name = curToken.value;
        advance();

        assume(TokenType.O_BLOCK_BRACKET, "[ missing");
        NumberNode index = parseExpression();
        assume(TokenType.C_BLOCK_BRACKET, "] missing");

        assume(TokenType.EQUAL, "= missing");
        NumberNode value = parseExpression();

        ArrSetFieldNode asfn = new ArrSetFieldNode(name, index, value);
        addInstruction(asfn);

        return null;
    }

    private NumberNode parseArrayGetField() {
        // ID [ EXPR ]
        String name = curToken.value;
        advance();

        assume(TokenType.O_BLOCK_BRACKET, "[ missing");
        NumberNode index = parseExpression();
        assume(TokenType.C_BLOCK_BRACKET, "] missing");

        ArrGetFieldNode agfn = new ArrGetFieldNode(name, index);
        return agfn;
    }

    public BlockNode getAST() {
        return root;
    }
}