package parser.parser;

import lexer.TokenType;
import parser.PError.UnimplementedError;
import parser.nodesystem.node.Node;
import parser.parser.array.ArrayGetField;
import parser.parser.array.ArrayInitialization;
import parser.parser.array.ArraySetField;
import parser.parser.loops.DoWhile;
import parser.parser.loops.For;
import parser.parser.loops.While;
import parser.parser.pointer.PtrDeclaration;
import parser.parser.pointer.PtrDefinition;
import parser.parser.pointer.PtrInitialization;
import parser.parser.shortcuts.PostDecrement;
import parser.parser.shortcuts.PostIncrement;
import parser.parser.shortcuts.PreDecrement;
import parser.parser.shortcuts.PreIncrement;

public class Instruction {

    public static Node parse(Parser p) {

        p.BOI();

        Node instruction = null;

        if (p.is(TokenType.TAB))
            p.eat();

        switch (p.curToken.type) {
        case KW_IF -> instruction = If.parse(p);
        case KW_ELSE -> instruction = Else.parse(p);
        case KW_ELIF -> instruction = Elif.parse(p);
        // Printing
        case KW_OUT -> instruction = Out.parse(p);
        //PRIMITIVES
        case KW_BYTE, KW_SHORT, KW_INT, KW_LONG, KW_FLOAT, KW_DOUBLE -> {
            if (p.next(TokenType.STAR)) {
                if (p.next(3, TokenType.EQUAL))
                    instruction = PtrInitialization.parse(p);
                else
                    instruction = PtrDeclaration.parse(p);
            }
            // VAR_INITIALIZATION
            else if (p.next(2, TokenType.EQUAL))
                instruction = VarInitialization.parse(p);
            // ARRAY_INITIALIZATION
            else if (p.next(2, TokenType.O_BLOCK_BRACKET)) {
                instruction = ArrayInitialization.parse(p);
            }
            // VAR_DECLARATION
            else
                instruction = VarDeclaration.parse(p);
        }
        // LOOPS
        case KW_FOR -> instruction = For.parse(p);
        case KW_DO -> instruction = DoWhile.parse(p);
        case KW_WHILE -> instruction = While.parse(p);
        //
        case IDENTIFIER -> {
            //FUNCTION
            if (p.next(TokenType.O_ROUND_BRACKET))
                instruction = FunctionCall.parse(p);
            // x = EXPR
            else if (p.next(TokenType.EQUAL))
                instruction = VarDefinition.parse(p);
            else if (p.next(TokenType.MINUS))
                instruction = PostDecrement.parse(p);
            else if (p.next(TokenType.PLUS))
                instruction = PostIncrement.parse(p);
            else if (p.next(TokenType.O_BLOCK_BRACKET)) {
                if (p.next(4, TokenType.EQUAL))
                    instruction = ArraySetField.parse(p);
                else
                    instruction = ArrayGetField.parse(p);
            } else
                new UnimplementedError("Unknown identifier '" + p.curToken.value + "'", p.curToken);
        }
        //
        case PLUS -> instruction = PreIncrement.parse(p);
        case MINUS -> instruction = PreDecrement.parse(p);
        case STAR -> instruction = PtrDefinition.parse(p);
        case EOF -> instruction = null;
        default -> {
            String error = "[INSTRUCTION] Action for Token " + p.curToken.type + " not implemented";
            new UnimplementedError(error, p.curToken);
        }
        }

        p.BOI();

        return instruction;
    }
}
