package lexer;

import java.util.ArrayList;

public class Token {

    public final TokenType type;
    public String value;
    public int line;
    public int start;
    public int end;

    /** Use this ctor to create programm independent tokens */
    public Token(TokenType type, String value) {
        this(type, value, -1, 0, value.length() - 1);
    }

    /** @param line a value of -1 indicates that the token
     *  doesnt belong to a file and thus was created artificially.*/
    public Token(TokenType type, String value, int line, int start, int end) {
        this.type = type;
        this.value = value;
        this.line = line;
        this.start = start;
        this.end = end;
    }

    public static Token[] toArray(ArrayList<Token> list) {
        Token[] tokens = new Token[list.size()];
        return list.toArray(tokens);
    }

    //TODO bad implementation
    public String getDescription() {
        String position = line + "," + start;
        if (start != end)
            position = line + "," + start + ":" + end;

        String descr = ("[" + type + "] " + position);
        if (value != null)
            descr = ("[" + type + ", " + value + "] " + position);

        return descr;
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

    //TODO boolean und char
    public boolean isPrimitive() {
        return switch (type) {
        case KW_BYTE -> true;
        case KW_SHORT -> true;
        case KW_INT -> true;
        case KW_LONG -> true;
        case KW_FLOAT -> true;
        case KW_DOUBLE -> true;
        default -> false;
        };
    }
}
