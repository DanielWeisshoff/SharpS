package com.danielweisshoff.lexer;

import java.util.ArrayList;
import java.util.HashMap;

//TODO? implement RegRex bcause its faster
//TODO zusammenbau von COMPARISON in Parser verschieben
public class Lexer {

    public static String VERSION = "V 0.8.1";

    private final HashMap<Character, TokenType> tokenMap = new HashMap<>();
    private String text;
    private int charIndex = -1;
    private char currentChar;
    private boolean hasNext = true; //if the pointer is at the end or not

    //
    private int line = 1;
    //the column pos of the current char
    private int curColumn = 0;
    //the position at which the current token starts
    private int tokenStart = 0;

    public Lexer(String text) {
        this.text = text;
        initializeSingleCharTokens();
        advance();
    }

    private void advance() {
        curColumn++;
        charIndex++;

        if (charIndex < text.length())
            currentChar = text.charAt(charIndex);
    }

    public Token[] next() {
        ArrayList<Token> tokens = new ArrayList<>();

        while (charIndex < text.length()) {
            if (currentChar == '\n') {
                tokens.add(new Token(TokenType.NEWLINE, "", line, tokenStart, curColumn));
                advance();
                line++;
                tokenStart = 0;
                curColumn = 0;
            } else if (tokenMap.containsKey(currentChar)) {
                tokens.add(new Token(tokenMap.get(currentChar), "", line, tokenStart, curColumn));
                advance();
            } else if (Character.isAlphabetic(currentChar)) {
                tokens.add(buildIdentifierOrKeywordToken());
            } else if (Character.isDigit(currentChar)) {
                tokens.add(buildNumberToken());
            } else if (currentChar == ' ' || currentChar == '\t') {
                Token t = buildTabToken();
                if (t != null)
                    tokens.add(t);
            } else {
                switch (currentChar) {
                case '"' -> tokens.add(buildStringToken());
                case '#' -> skipComment();
                case '=' -> tokens.add(buildComparisonToken('='));
                case '<' -> tokens.add(buildComparisonToken('<'));
                case '>' -> tokens.add(buildComparisonToken('>'));
                case '!' -> tokens.add(buildComparisonToken('!'));
                default -> advance();
                }
            }
            tokenStart = curColumn;
        }

        //Newline skippen
        advance();

        //check if file at end 
        if (charIndex >= text.length())
            hasNext = false;

        return Token.toArray(tokens);
    }

    public boolean hasNextLine() {
        return hasNext;
    }

    //
    // building stuff
    //
    private Token buildIdentifierOrKeywordToken() {
        int start = charIndex;
        advance();
        while (charIndex < text.length()) {
            if (Character.isLetterOrDigit(currentChar)) {
                advance();
            } else
                break;
        }
        String subString = text.substring(start, charIndex);
        return switch (subString) {
        //PRIMITIVES
        case "bte" -> new Token(TokenType.KW_BYTE, null, line, tokenStart, curColumn - 1);
        case "sht" -> new Token(TokenType.KW_SHORT, null, line, tokenStart, curColumn - 1);
        case "int" -> new Token(TokenType.KW_INT, null, line, tokenStart, curColumn - 1);
        case "lng" -> new Token(TokenType.KW_LONG, null, line, tokenStart, curColumn - 1);
        case "flt" -> new Token(TokenType.KW_FLOAT, null, line, tokenStart, curColumn - 1);
        case "dbl" -> new Token(TokenType.KW_DOUBLE, null, line, tokenStart, curColumn - 1);
        //BOOLEAN
        case "true" -> new Token(TokenType.KW_TRUE, null, line, tokenStart, curColumn - 1);
        case "false" -> new Token(TokenType.KW_FALSE, null, line, tokenStart, curColumn - 1);
        //
        case "if" -> new Token(TokenType.KW_IF, null, line, tokenStart, curColumn - 1);
        case "else" -> new Token(TokenType.KW_ELSE, null, line, tokenStart, curColumn - 1);
        case "elif" -> new Token(TokenType.KW_ELIF, null, line, tokenStart, curColumn - 1);
        case "fnc" -> new Token(TokenType.KW_FNC, null, line, tokenStart, curColumn - 1);
        case "while" -> new Token(TokenType.KW_WHILE, null, line, tokenStart, curColumn - 1);
        case "for" -> new Token(TokenType.KW_FOR, null, line, tokenStart, curColumn - 1);
        case "do" -> new Token(TokenType.KW_DO, null, line, tokenStart, curColumn - 1);
        case "out" -> new Token(TokenType.KW_OUT, null, line, tokenStart, curColumn - 1);
        default -> new Token(TokenType.IDENTIFIER, subString, line, tokenStart, curColumn - 1);
        };
    }

    // "int", "if", "else", "elif", "fnc", "while", "for", "do" 
    private Token buildTabToken() {
        Token t = null;
        int whitespaceCount = 0;

        while (charIndex < text.length()) {
            if (currentChar == ' ')
                whitespaceCount++;
            else if (currentChar == '\t')
                whitespaceCount += 4;
            else
                break;
            advance();
        }
        if (whitespaceCount >= 4)
            t = new Token(TokenType.TAB, "" + (int) Math.floor(whitespaceCount / 4), line, tokenStart, curColumn - 1);
        return t;
    }

    private Token buildNumberToken() {
        int start = charIndex;
        boolean isFloat = false;
        advance();
        while (charIndex < text.length()) {
            if (Character.isDigit(currentChar) || currentChar == '.') {
                if (currentChar == '.')
                    isFloat = true;
                advance();
            } else
                break;
        }
        if (isFloat)
            return new Token(TokenType.FLOATING_POINT, text.substring(start, charIndex), line, tokenStart,
                    curColumn - 1);
        return new Token(TokenType.INTEGER, text.substring(start, charIndex), line, tokenStart, curColumn - 1);
    }

    private Token buildComparisonToken(char c) {
        advance();
        if (charIndex < text.length()) {
            if (currentChar == '=') {
                advance();
                return new Token(TokenType.COMPARISON, c + "=", line, tokenStart, curColumn - 1);
            }
        }
        return new Token(TokenType.EQUAL, "" + c, line, tokenStart, curColumn - 1);
    }

    private Token buildStringToken() {
        advance();
        int start = charIndex;

        while (charIndex < text.length()) {
            if (currentChar != '"') {
                advance();
            } else {
                advance();
                break;
            }
        }
        return new Token(TokenType.STRING, text.substring(start, charIndex - 1), line, tokenStart, curColumn - 1);
    }

    private void skipComment() {
        while (charIndex < text.length()) {
            if (currentChar != '\n') {
                advance();
            } else
                return;
        }
    }

    /*
     * Hier können alle Einzeltokens eingetragen werden
     */
    private void initializeSingleCharTokens() {
        tokenMap.put('+', TokenType.PLUS);
        tokenMap.put('-', TokenType.MINUS);
        tokenMap.put('*', TokenType.STAR);
        tokenMap.put('/', TokenType.SLASH);
        tokenMap.put('(', TokenType.O_ROUND_BRACKET);
        tokenMap.put(')', TokenType.C_ROUND_BRACKET);
        tokenMap.put('.', TokenType.DOT);
        tokenMap.put(',', TokenType.COMMA);
        tokenMap.put(':', TokenType.COLON);
        tokenMap.put('%', TokenType.PERCENT);
        tokenMap.put('&', TokenType.AND);
        tokenMap.put('|', TokenType.PIPE);
        tokenMap.put('\0', TokenType.EOF);
        tokenMap.put('[', TokenType.O_BLOCK_BRACKET);
        tokenMap.put(']', TokenType.C_BLOCK_BRACKET);
    }
}
