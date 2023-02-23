package lexer;

import java.util.ArrayList;

public class Token {

    private final TokenType type;
    public String value;
    public int line;
    public int start;
    public int end;

    /**
     *  Used by the lexer
     * @param line specifies the line in the .#s file where
     * the token is. a value of -1 indicates that the token
     * doesnt belong to a file and thus was created artificially.
     * 
     */
    public Token(TokenType type, String value, int line, int start, int end) {
        this.type = type;
        this.value = value;
        this.line = line;
        this.start = start;
        this.end = end;
    }

    /**
     * Use this ctor to create programm independent tokens
     */
    public Token(TokenType type, String value) {
        this.type = type;
        this.value = value;
        line = -1;
        start = 0;
        end = value.length() - 1;
    }

    public static boolean areSameCategoryOP(Token t1, Token t2) {
        return t1.isLineOP() && t2.isLineOP() || t1.isDotOP() && t2.isDotOP();
    }

    public static Token[] toArray(ArrayList<Token> list) {
        Token[] tokens = new Token[list.size()];
        return list.toArray(tokens);
    }

    public String getDescription() {

        String position = "";

        if (start == end)
            position = line + "," + start;
        else
            position = line + "," + start + ":" + end;

        if (value != null)
            return ("[" + type + ", " + value + "] " + position);
        else
            return ("[" + type + "] " + position);
    }

    public TokenType type() {
        return type;
    }

    public boolean isLineOP() {
        return type == TokenType.PLUS || type == TokenType.MINUS;
    }

    public boolean isDotOP() {
        return type == TokenType.STAR || type == TokenType.SLASH;
    }

    public boolean isOP() {
        return isLineOP() || isDotOP() || type == TokenType.PERCENT;
    }

    public boolean isEOF() {
        return type == TokenType.EOF;
    }

    public boolean isNumeric() {
        return type == TokenType.INTEGER || type == TokenType.FLOATING_POINT;
    }

    //TODO boolean?
    public boolean isPrimitive() {

        return switch (type) {
        case KW_BYTE -> true;
        case KW_SHORT -> true;
        case KW_INT -> true;
        case KW_LONG -> true;
        case KW_FLOAT -> true;
        case KW_DOUBLE -> true;
        default -> unknownPrimitiveError();
        };
    }

    //TODO just helper for now
    private boolean unknownPrimitiveError() {
        System.out.println("Unknown primitive '" + type + "'");
        System.exit(0);
        return false;
    }
}
