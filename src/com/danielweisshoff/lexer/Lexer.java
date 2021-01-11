package com.danielweisshoff.lexer;

import java.util.ArrayList;
import java.util.HashMap;

/*TODO
 * Wenn der Text mit Leerzeichen endet, gibt es Fehler
 *
 * Problem mit Unaryoperator
 * 1-1  wird falsch gewertet
 *
 * lasttoken entfernen und ll(1) einf?hren
 */
public class Lexer {

    public static String VERSION = "V 0.8";
    private final String text;
    private int charIndex = -1;
    private char currentChar;
    private final HashMap<Character, TokenType> tokenMap = new HashMap<>();
    private final String[] keywords = new String[]{"int", "con", "fnc", "ntr", "cls"};
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

    public Token[] tokenizeText() {
        ArrayList<Token> tokens = new ArrayList<>();

        while (charIndex < text.length()) {
            if (tokenMap.containsKey(currentChar)) {
                tokens.add(new Token(tokenMap.get(currentChar), ""));
                advance();
            } else if (Character.isAlphabetic(currentChar)) {
                tokens.add(buildIdentifierToken());
            } else if (Character.isDigit(currentChar)) {
                tokens.add(buildNumberToken());
            } else if (currentChar == ' ') {
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
                    case '-' -> tokens.add(buildUnaryNumberToken());
                    default -> advance();
                }
            }
        }
        tokens.add(new Token(TokenType.EOF, ""));
        return Token.toArray(tokens);
    }

    public Token nextToken() {
        if (charIndex >= text.length())
            return new Token(TokenType.EOF, "");

        Token token = null;
        while (token == null && charIndex < text.length()) {
            if (tokenMap.containsKey(currentChar)) {
                token = new Token(tokenMap.get(currentChar), "");
                advance();
            } else if (Character.isAlphabetic(currentChar)) {
                token = buildIdentifierToken();
            } else if (Character.isDigit(currentChar)) {
                token = buildNumberToken();
            } else {
                switch (currentChar) {
                    case '"' -> token = (buildStringToken());
                    case '#' -> skipComment();
                    case '=' -> token = buildComparisonToken('=');
                    case '<' -> token = buildComparisonToken('<');
                    case '>' -> token = buildComparisonToken('>');
                    case '!' -> token = buildComparisonToken('!');
                    case '-' -> token = buildUnaryNumberToken();
                    default -> advance();
                }
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

    private Token buildTabToken() {
        Token t = null;
        int whitespaceCount = 1;
        advance();
        while (currentChar == ' ') {
            whitespaceCount++;
            advance();
        }
        if (whitespaceCount >= 4)
            t = new Token(TokenType.TAB, "" + (int) Math.floor(whitespaceCount / 4));
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
                advance();
            } else
                return;
        }
    }

    /**
     * Hier k?nnen alle Einzeltokens eingetragen werden
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
