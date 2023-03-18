package parser.parser.arithmetic;

import lexer.TokenType;
import parser.PError.UnimplementedError;
import parser.nodesystem.node.binaryoperations.NumberNode;
import parser.nodesystem.node.data.primitives.ByteNode;
import parser.nodesystem.node.data.primitives.DoubleNode;
import parser.nodesystem.node.data.primitives.FloatNode;
import parser.nodesystem.node.data.primitives.IntegerNode;
import parser.nodesystem.node.data.primitives.LongNode;
import parser.nodesystem.node.data.primitives.PrimitiveNode;
import parser.nodesystem.node.data.primitives.ShortNode;
import parser.nodesystem.node.data.var.VariableNode;
import parser.parser.Parser;
import parser.parser.array.ArrayGetField;
import parser.parser.shortcuts.PostDecrement;
import parser.parser.shortcuts.PostIncrement;
import parser.parser.shortcuts.PreDecrement;
import parser.parser.shortcuts.PreIncrement;
import parser.semantic.ConversionChecker;
import parser.symboltable.VariableEntry;

public class Factor {

    public static Parser p;

    public static NumberNode parse(Parser p) {
        Factor.p = p;

        NumberNode n = null;
        switch (p.curToken.type) {
        case INTEGER:
            n = parseInteger(p.curToken.value);
            p.eat();
            break;
        case FLOATING_POINT:
            n = parseFloatingPoint(p.curToken.value);
            p.eat();
            break;
        case MINUS:
            p.eat();
            switch (p.curToken.type) {
            // - DIGIT
            case INTEGER:
                n = parseInteger('-' + p.curToken.value);
                p.eat();
                break;
            // - DIGIT
            case FLOATING_POINT:
                n = parseFloatingPoint('-' + p.curToken.value);
                p.eat();
                break;
            // - ( EXPR )
            case O_ROUND_BRACKET:
                p.eat();
                n = Expression.parse(p);
                p.eat(TokenType.C_ROUND_BRACKET, "Bracket not properly closed");
                break;
            // - ID
            case IDENTIFIER:
                String varName = p.curToken.value;
                p.eat();
                n = new VariableNode(varName);
                break;
            // - - ID
            case MINUS:
                p.retreat();
                n = PreDecrement.parse(p);
                break;
            default:
                new UnimplementedError("error parsing factor with -", p.curToken);
            }
            break;
        case PLUS:
            // + + ID	
            n = PreIncrement.parse(p);
            break;
        case O_ROUND_BRACKET:
            // ( EXPR )
            p.eat();

            n = Expression.parse(p);
            p.eat(TokenType.C_ROUND_BRACKET, "bracket not properly closed");
            break;
        case IDENTIFIER:
            // ID - -	
            if (p.next(TokenType.MINUS) && p.next(2, TokenType.MINUS))
                n = PostDecrement.parse(p);
            // ID + +
            else if (p.next(TokenType.PLUS) && p.next(2, TokenType.PLUS))
                n = PostIncrement.parse(p);
            // ID [ EXPR ]
            else if (p.next(TokenType.O_BLOCK_BRACKET)) {
                n = ArrayGetField.parse(p);
                // ID
            } else {
                String varName = p.curToken.value;
                p.eat();
                VariableEntry ve = p.stm.findVariable(varName);

                //TODO only temporary -> put in semantic part
                if (ve == null) {

                    System.out.println("ERROR: var " + varName + " not declared");
                    System.exit(1);
                } else
                    n = new VariableNode(varName);
            }
            break;
        default:
            new UnimplementedError("parseFactor()", p.curToken);
        }
        return n;
    }

    //TODO move to semantics
    //converts an Integer Number into the best fitting primitive
    private static PrimitiveNode parseInteger(String value) {
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
            new UnimplementedError(value + " is not an Integer?!", p.curToken);
            return null;
        }
    }

    //TODO move to semantics
    //converts a Floating-Point Number into the best fitting primitive
    private static PrimitiveNode parseFloatingPoint(String value) {
        //? float
        if (ConversionChecker.isFloat(value))
            return new FloatNode(Float.parseFloat(value));
        //? double
        else if (ConversionChecker.isDouble(value))
            return new DoubleNode(Double.parseDouble(value));
        else {
            new UnimplementedError(value + " is not a Floating Point Number?!", p.curToken);
            return null;
        }
    }
}
