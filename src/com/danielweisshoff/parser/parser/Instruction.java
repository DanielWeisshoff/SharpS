package com.danielweisshoff.parser.parser;

import com.danielweisshoff.lexer.TokenType;
import com.danielweisshoff.parser.PError.UnimplementedError;
import com.danielweisshoff.parser.nodesystem.node.Node;
import com.danielweisshoff.parser.parser.array.ArrayGetField;
import com.danielweisshoff.parser.parser.array.ArrayInitialization;
import com.danielweisshoff.parser.parser.array.ArraySetField;
import com.danielweisshoff.parser.parser.loops.DoWhile;
import com.danielweisshoff.parser.parser.loops.For;
import com.danielweisshoff.parser.parser.loops.While;
import com.danielweisshoff.parser.parser.pointer.PtrDeclaration;
import com.danielweisshoff.parser.parser.pointer.PtrDefinition;
import com.danielweisshoff.parser.parser.shortcuts.PostDecrement;
import com.danielweisshoff.parser.parser.shortcuts.PostIncrement;
import com.danielweisshoff.parser.parser.shortcuts.PreDecrement;
import com.danielweisshoff.parser.parser.shortcuts.PreIncrement;

public class Instruction {

    public static Node parse(Parser p) {

        Node instruction = null;

        switch (p.curToken.type()) {
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
                instruction = PostDecrement.parse(p, true);
            else if (p.next(TokenType.PLUS))
                instruction = PostIncrement.parse(p, true);
            else if (p.next(TokenType.O_BLOCK_BRACKET)) {
                if (p.next(4, TokenType.EQUAL))
                    instruction = ArraySetField.parse(p);
                else
                    instruction = ArrayGetField.parse(p);

            }
        }
        //
        case PLUS -> instruction = PreIncrement.parse(p, true);
        case MINUS -> instruction = PreDecrement.parse(p, true);
        case STAR -> instruction = PtrDefinition.parse(p);
        case EOF -> {
            return null;
        }
        default -> {
            String error = "[INSTRUCTION] Action for Token " + p.curToken.type() + " not implemented";
            new UnimplementedError(error, p.curToken);
        }
        }

        return instruction;
    }

    private static void endOfInstruction(Parser p) {
        if (!p.is(TokenType.NEWLINE))
            new UnimplementedError("expected end of instruction but found " + p.curToken.type(), p.curToken);
    }
}
