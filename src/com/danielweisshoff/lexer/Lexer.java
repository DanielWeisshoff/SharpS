package com.danielweisshoff.lexer;

import java.util.ArrayList;
import java.util.HashMap;

public class Lexer {

    public static String VERSION = "V 0.8";
    private final String text;
    private int charIndex = -1;
    private char currentChar;

    private final HashMap<Character, TokenType> tokenMap = new HashMap<>();
    private final String[] keywords = new String[]{"int", "dbl", "str", "if", "else", "for", "while"};
    private Token lastToken;

    public Lexer(String text) {
        this.text = text;
        initializeSingleCharacterTokens();
        advance();
    }

    private void advance() {
        charIndex++;
        if (charIndex < text.length())
            currentChar = text.charAt(charIndex);
    }

    @Deprecated
    public Token[] tokenizeText() {
        ArrayList<Token> tokens = new ArrayList<>();

        advance();
        System.out.println(charIndex);
        while (charIndex < text.length()) {
            if (tokenMap.containsKey(currentChar)) {
                tokens.add(new Token(tokenMap.get(currentChar), null));
            } else if (Character.isAlphabetic(currentChar)) {
                tokens.add(buildIdentifierToken());
            } else if (Character.isDigit(currentChar))
                tokens.add(buildNumberToken());
            else if (currentChar == '"')
                tokens.add(buildStringToken());
            else if (currentChar == '#')
                skipComment();
        }
        return Token.toArray(tokens);
    }

    public Token nextToken() {
        if (charIndex >= text.length())
            return new Token(TokenType.EOF, null);

        Token token = null;
        while (token == null && charIndex < text.length()) {
            if (tokenMap.containsKey(currentChar)) {
                token = new Token(tokenMap.get(currentChar), null);
                advance();
            } else if (Character.isAlphabetic(currentChar)) {
                token = buildIdentifierToken();
            } else if (Character.isDigit(currentChar)) {
                token = buildNumberToken();
            } else if (currentChar == '"') {
                token = buildStringToken();
            } else if (currentChar == '#') {
                skipComment();
            } else if (currentChar == '=') {
                token = buildEqualsToken();
            } else if (currentChar == '<') {
                token = buildLessThanToken();
            } else if (currentChar == '>') {
                token = buildMoreThanToken();
            } else if (currentChar == '!') {
                token = buildNotEqualToken();
            } else if (currentChar == '-') {
                token = buildUnaryNumberToken();
            } else {
                advance();
            }
        }
        lastToken = token;
        return token;
    }

    private Token buildIdentifierToken() {
        int start = charIndex;
        advance();
        while (charIndex < text.length()) {
            if (Character.isLetterOrDigit(currentChar)) {
                advance();
            } else
                break;
        }
        String subString = text.substring(start, charIndex);
        for (String s : keywords)
            if (subString.equals(s))
                return new Token(TokenType.KEYWORD, subString);
        return new Token(TokenType.IDENTIFIER, subString);
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
            return new Token(TokenType.FLOAT,
                    text.substring(start, charIndex));
        return new Token(TokenType.NUMBER,
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
        return new Token(TokenType.SUB, null);
    }

    private Token buildEqualsToken() {
        advance();
        if (charIndex < text.length()) {
            if (currentChar == '=') {
                advance();
                return new Token(TokenType.EQUALS, null);
            }
        }
        return new Token(TokenType.ASSIGN, null);
    }

    private Token buildLessThanToken() {
        advance();
        if (charIndex < text.length()) {
            if (currentChar == '=') {
                advance();
                return new Token(TokenType.LESSOREQUAL, null);
            }
        }
        return new Token(TokenType.LESSTHAN, null);
    }

    private Token buildMoreThanToken() {
        advance();
        if (charIndex < text.length()) {
            if (currentChar == '=') {
                advance();
                return new Token(TokenType.MOREOREQUAL, null);
            }
        }
        return new Token(TokenType.MORETHAN, null);
    }

    private Token buildNotEqualToken() {
        advance();
        if (charIndex < text.length()) {
            if (currentChar == '=') {
                advance();
                return new Token(TokenType.NOTEQUAL, null);
            }
        }
        return new Token(TokenType.NOT, null);
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
        return new Token(TokenType.STRING,
                text.substring(start, charIndex - 1));
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
     */
    private void initializeSingleCharacterTokens() {
        tokenMap.put('+', TokenType.ADD);
        tokenMap.put('*', TokenType.MUL);
        tokenMap.put('/', TokenType.DIV);
        tokenMap.put('(', TokenType.O_ROUND_BRACKET);
        tokenMap.put(')', TokenType.C_ROUND_BRACKET);
        tokenMap.put('\n', TokenType.NEWLINE);
        tokenMap.put('.', TokenType.DOT);
        tokenMap.put(',', TokenType.COMMA);
        tokenMap.put(':', TokenType.COLON);
    }
}
