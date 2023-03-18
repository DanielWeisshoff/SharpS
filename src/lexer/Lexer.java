package lexer;

import java.util.ArrayList;
import java.util.HashMap;

import logger.Channel;
import logger.Logger;

//TODO? zusammenbau von COMPARISON in Parser verschieben
public class Lexer {

    public static boolean debug = false;

    private static HashMap<Character, TokenType> terminals = Terminals.terminals;
    private String text;
    private char currentChar;

    private int absPos;
    private int linePos;
    private int colPos;
    //the col position at which the current token starts
    private int tokenStart = 0;

    private void reset() {
        absPos = -1;
        colPos = -1;
        linePos = 1;

        advance();
    }

    private void advance() {
        colPos++;
        absPos++;

        currentChar = hasNext() ? text.charAt(absPos) : '\0';
    }

    public Token[] tokenize(String text) {
        this.text = text;
        reset();

        ArrayList<Token> tokens = new ArrayList<>();

        while (hasNext()) {
            if (currentChar == '\n') {
                tokens.add(new Token(TokenType.NEWLINE, "", linePos, tokenStart, colPos));
                advance();
                linePos++;
                tokenStart = 0;
                colPos = 0;
            } else if (terminals.containsKey(currentChar)) {
                tokens.add(new Token(terminals.get(currentChar), "", linePos, tokenStart, colPos));
                advance();
            } else if (Character.isAlphabetic(currentChar)) {
                tokens.add(lexIdentifierOrKeyword());
            } else if (Character.isDigit(currentChar)) {
                tokens.add(lexNumber());
            } else if (currentChar == ' ' || currentChar == '\t') {
                Token t = lexWhitespace();
                if (t != null)
                    tokens.add(t);
            } else {
                switch (currentChar) {
                case '"' -> tokens.add(lexString());
                case '#' -> lexComment();
                case '=' -> tokens.add(lexComparison('='));
                case '<' -> tokens.add(lexComparison('<'));
                case '>' -> tokens.add(lexComparison('>'));
                case '!' -> tokens.add(lexComparison('!'));
                default -> advance();
                }
            }
            tokenStart = colPos;
        }

        if (debug)
            for (Token t : tokens)
                Logger.log(t.getDescription(), Channel.LEXER);

        return Token.toArray(tokens);
    }

    //
    // lexing stuff
    //
    private Token lexIdentifierOrKeyword() {
        int start = absPos;

        while (hasNext() && Character.isLetterOrDigit(currentChar))
            advance();

        String subString = text.substring(start, absPos);

        return switch (subString) {
        //PRIMITIVES
        case Keywords.BYTE -> new Token(TokenType.KW_BYTE, null, linePos, tokenStart, colPos - 1);
        case Keywords.SHORT -> new Token(TokenType.KW_SHORT, null, linePos, tokenStart, colPos - 1);
        case Keywords.INT -> new Token(TokenType.KW_INT, null, linePos, tokenStart, colPos - 1);
        case Keywords.LONG -> new Token(TokenType.KW_LONG, null, linePos, tokenStart, colPos - 1);
        case Keywords.FLOAT -> new Token(TokenType.KW_FLOAT, null, linePos, tokenStart, colPos - 1);
        case Keywords.DOUBLE -> new Token(TokenType.KW_DOUBLE, null, linePos, tokenStart, colPos - 1);
        //BOOLEAN
        case Keywords.TRUE -> new Token(TokenType.KW_TRUE, null, linePos, tokenStart, colPos - 1);
        case Keywords.FALSE -> new Token(TokenType.KW_FALSE, null, linePos, tokenStart, colPos - 1);
        //CONTROL STRUCTURES
        case Keywords.IF -> new Token(TokenType.KW_IF, null, linePos, tokenStart, colPos - 1);
        case Keywords.ELSE -> new Token(TokenType.KW_ELSE, null, linePos, tokenStart, colPos - 1);
        case Keywords.ELSEIF -> new Token(TokenType.KW_ELIF, null, linePos, tokenStart, colPos - 1);
        //LOOPS
        case Keywords.WHILE -> new Token(TokenType.KW_WHILE, null, linePos, tokenStart, colPos - 1);
        case Keywords.FOR -> new Token(TokenType.KW_FOR, null, linePos, tokenStart, colPos - 1);
        case Keywords.DO -> new Token(TokenType.KW_DO, null, linePos, tokenStart, colPos - 1);
        //OTHER
        case Keywords.FUNCTION -> new Token(TokenType.KW_FNC, null, linePos, tokenStart, colPos - 1);
        case Keywords.OUT -> new Token(TokenType.KW_OUT, null, linePos, tokenStart, colPos - 1);
        default -> new Token(TokenType.IDENTIFIER, subString, linePos, tokenStart, colPos - 1);
        };
    }

    private Token lexWhitespace() {
        int whitespace = 0;

        while (hasNext()) {
            if (currentChar == ' ')
                whitespace++;
            else if (currentChar == '\t')
                whitespace += 4;
            else
                break;
            advance();
        }

        if (whitespace >= 4) {
            String value = "" + (int) Math.floor(whitespace / 4);
            return new Token(TokenType.TAB, value, linePos, tokenStart, colPos - 1);
        }
        return null;
    }

    private Token lexNumber() {
        TokenType type = TokenType.INTEGER;

        int start = absPos;
        advance();
        while (absPos < text.length()) {
            if (Character.isDigit(currentChar) || currentChar == '.' || currentChar == '_') {
                if (currentChar == '_')
                    advance();
                else if (currentChar == '.')
                    if (type == TokenType.FLOATING_POINT)
                        break;
                    else
                        type = TokenType.FLOATING_POINT;
                advance();
            } else
                break;
        }
        String value = text.substring(start, absPos).replace("_", "");
        return new Token(type, value, linePos, tokenStart, colPos - 1);
    }

    private Token lexComparison(char c) {
        advance();
        if (currentChar == '=') {
            advance();
            return new Token(TokenType.COMPARISON, c + "=", linePos, tokenStart, colPos - 1);
        } else
            return new Token(TokenType.EQUAL, c + "", linePos, tokenStart, colPos - 1);
    }

    private Token lexString() {
        advance();
        int start = absPos;

        while (hasNext() && currentChar != '"')
            advance();
        //skip quotation mark
        advance();

        String value = text.substring(start, absPos - 1);
        return new Token(TokenType.STRING, value, linePos, tokenStart, colPos - 1);
    }

    private void lexComment() {
        while (hasNext() && currentChar != '\n')
            advance();
    }

    public boolean hasNext() {
        return absPos < text.length();
    }
}
