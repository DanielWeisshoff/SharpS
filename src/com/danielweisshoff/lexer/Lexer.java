package com.danielweisshoff.lexer;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author danie
 */
public class Lexer {

    public static String VERSION = "V 0.7";
    private HashMap<Character, TokenType> tokenMap = new HashMap<Character, TokenType>();
    private String text;
    private int charIndex = 0;
    private char currentChar;
<<<<<<< Updated upstream
    private char nextChar;
=======
    private final HashMap<Character, TokenType> tokenMap = new HashMap<>();
    private final String[] keywords = new String[]{"int", "ntr", "cls"};
    private Token lastToken;
>>>>>>> Stashed changes

    public Lexer(String text) {
        this.text = text;
        initializeSingleCharacterTokens();
    }

    public ArrayList<Token> tokenizeText() {
        ArrayList<Token> tokens = new ArrayList<Token>();

        while (charIndex < text.length()) {
            currentChar = text.charAt(charIndex);
            if (tokenMap.containsKey(currentChar)) {
<<<<<<< Updated upstream
                tokens.add(new Token(
                        tokenMap.get(currentChar), null));
            } else if (Character
                    .isAlphabetic(currentChar)) {
=======
                tokens.add(new Token(tokenMap.get(currentChar), ""));
                advance();
            } else if (Character.isAlphabetic(currentChar)) {
>>>>>>> Stashed changes
                tokens.add(buildIdentifierToken());
            } else if (Character.isDigit(currentChar)) {
                tokens.add(buildNumberToken());
            } else if (currentChar == '"') {
                tokens.add(buildStringToken());
            } else if (currentChar == '#') {
                skipComment();
            }

            charIndex++;
        }
<<<<<<< Updated upstream
        return tokens;
=======
        tokens.add(new Token(TokenType.EOF, ""));
        return Token.toArray(tokens);
>>>>>>> Stashed changes
    }

    public Token nextToken() {

        if (charIndex >= text.length())
            return new Token(TokenType.EOF, "");

        Token token = null;

        while (token == null) {
            currentChar = text.charAt(charIndex);
            if (tokenMap.containsKey(currentChar)) {
<<<<<<< Updated upstream
                token = new Token(
                        tokenMap.get(currentChar),
                        null);
            } else if (Character
                    .isAlphabetic(currentChar)) {
=======
                token = new Token(tokenMap.get(currentChar), "");
                advance();
            } else if (Character.isAlphabetic(currentChar)) {
>>>>>>> Stashed changes
                token = buildIdentifierToken();
            } else if (Character.isDigit(currentChar)) {
                token = buildNumberToken();
            } else if (currentChar == '"') {
                token = buildStringToken();
            } else if (currentChar == '#') {
                skipComment();
            }
            charIndex++;
        }
        return token;
    }

    private Token buildIdentifierToken() {
        int start = charIndex;

        while (charIndex < text.length() - 1) {
            nextChar = text.charAt(charIndex + 1);
            if (Character.isLetterOrDigit(nextChar)) {
                charIndex++;
            } else
                break;
        }
        return new Token(TokenType.IDENTIFIER,
                text.substring(start, charIndex + 1));
    }

    private Token buildNumberToken() {
        int start = charIndex;
        boolean isFloat = false;
        while (charIndex < text.length() - 1) {
            nextChar = text.charAt(charIndex + 1);
            if (Character.isDigit(nextChar)
                    || nextChar == '.') {
                if (nextChar == '.')
                    isFloat = true;
                charIndex++;
            } else
                break;
        }
        if (isFloat)
            return new Token(TokenType.FLOAT,
                    text.substring(start, charIndex + 1));
        return new Token(TokenType.NUMBER,
<<<<<<< Updated upstream
                text.substring(start, charIndex + 1));
=======
                text.substring(start, charIndex));
    }

    private Token buildUnaryNumberToken() {
        boolean canBeUnary = false;
        if (lastToken == null)
            canBeUnary = true;
        else if (lastToken.isOP())
            canBeUnary = true;

        if (canBeUnary) {
            advance();
            if (Character.isDigit(currentChar)) {
                Token t = buildNumberToken();
                return new Token(t.type(), "" + -Double.parseDouble(t.getValue()));
            }
        }
        advance();
        return new Token(TokenType.SUB, "");
    }

    private Token buildComparisonToken(char c) {
        advance();
        if (charIndex < text.length()) {
            if (currentChar == '=') {
                advance();
                return new Token(TokenType.COMPARISON, c + "=");
            }
        }
        return new Token(TokenType.ASSIGN, "" + c);
>>>>>>> Stashed changes
    }

    private Token buildStringToken() {
        int start = charIndex;

        while (charIndex < text.length() - 1) {
            nextChar = text.charAt(charIndex + 1);
            if (nextChar != '"') {
                charIndex++;
            } else {
                charIndex++;
                break;
            }
        }
        return new Token(TokenType.STRING,
                text.substring(start + 1, charIndex));
    }

    private void skipComment() {
        while (charIndex < text.length() - 1) {
            if (currentChar != '\n') {
                charIndex++;
                currentChar = text.charAt(charIndex);
            } else
                return;
        }
    }

    /**
     * Hier können alle Einzeltokens eingetragen werden
     *
     * @return
     */
    private void initializeSingleCharacterTokens() {
        tokenMap.put('+', TokenType.ADD);
        tokenMap.put('-', TokenType.SUB);
        tokenMap.put('*', TokenType.MUL);
        tokenMap.put('/', TokenType.DIV);
        tokenMap.put('=', TokenType.EQUALS);
        tokenMap.put('(', TokenType.O_ROUND_BRACKET);
        tokenMap.put(')', TokenType.C_ROUND_BRACKET);
        tokenMap.put('\n', TokenType.NEWLINE);
        tokenMap.put('.', TokenType.DOT);
        tokenMap.put(',', TokenType.COMMA);
        tokenMap.put(':', TokenType.COLON);

//		tokenMap.put('[', TokenType.O_SQUARE_BRACKET);
//		tokenMap.put(']', TokenType.C_SQUARE_BRACKET);
//		tokenMap.put('<', TokenType.O_ANGLE_BRACKET);
//		tokenMap.put('>', TokenType.C_ANGLE_BRACKET);
    }

}