package com.danielweisshoff.parser.parser.arithmetic;

import com.danielweisshoff.lexer.TokenType;
import com.danielweisshoff.parser.PError.UnimplementedError;
import com.danielweisshoff.parser.nodesystem.node.binaryoperations.NumberNode;
import com.danielweisshoff.parser.nodesystem.node.data.primitives.*;
import com.danielweisshoff.parser.nodesystem.node.data.primitives.PrimitiveNode;
import com.danielweisshoff.parser.parser.Parser;
import com.danielweisshoff.parser.parser.array.ArrayGetField;
import com.danielweisshoff.parser.parser.shortcuts.PostDecrement;
import com.danielweisshoff.parser.parser.shortcuts.PostIncrement;
import com.danielweisshoff.parser.parser.shortcuts.PreDecrement;
import com.danielweisshoff.parser.parser.shortcuts.PreIncrement;
import com.danielweisshoff.parser.semantic.ConversionChecker;

public class Factor {

    public static Parser p;

    public static NumberNode parse(Parser p) {
        Factor.p = p;

        NumberNode n = null;
        switch (p.curToken.type()) {
        case INTEGER:
            n = parseInteger(p.curToken.value);
            p.advance();
            break;
        case FLOATING_POINT:
            n = parseFloatingPoint(p.curToken.value);
            p.advance();
            break;
        case MINUS:
            p.advance();
            switch (p.curToken.type()) {
            // - DIGIT
            case INTEGER:
                n = parseInteger('-' + p.curToken.value);
                p.advance();
                break;
            // - DIGIT
            case FLOATING_POINT:
                n = parseFloatingPoint('-' + p.curToken.value);
                p.advance();
                break;
            // - ( EXPR )
            case O_ROUND_BRACKET:
                p.advance();
                n = Expression.parse(p);
                p.assume(TokenType.C_ROUND_BRACKET, "Expression error. Bracket not properly closed");
                break;
            // - ID
            case IDENTIFIER:
                String varName = p.curToken.value;
                p.advance();
                n = p.stm.findVariable(varName).node;
                break;
            // - - ID
            case MINUS:
                p.retreat();
                n = PreDecrement.parse(p, false);
                break;
            default:
                new UnimplementedError("error parsing factor with -", p.curToken);
            }
            break;
        case PLUS:
            // + + ID	
            n = PreIncrement.parse(p, false);
            break;
        case O_ROUND_BRACKET:
            // ( EXPR )
            p.advance();

            n = Expression.parse(p);
            p.assume(TokenType.C_ROUND_BRACKET, "Expression error. Bracket not properly closed");
            break;
        case IDENTIFIER:
            // ID - -	
            if (p.next(TokenType.MINUS) && p.next(2, TokenType.MINUS))
                n = PostDecrement.parse(p, false);
            // ID + +
            else if (p.next(TokenType.PLUS) && p.next(2, TokenType.PLUS))
                n = PostIncrement.parse(p, false);
            // ID [ EXPR ]
            else if (p.next(TokenType.O_BLOCK_BRACKET)) {
                n = ArrayGetField.parse(p);
                // ID
            } else {
                String varName = p.curToken.value;
                p.advance();
                n = p.stm.findVariable(varName).node;
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
