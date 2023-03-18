package lexer;

import java.util.HashMap;

public class Terminals {
    public static final HashMap<Character, TokenType> terminals;
    static {
        terminals = new HashMap<>();

        //Add all terminals
        terminals.put('+', TokenType.PLUS);
        terminals.put('-', TokenType.MINUS);
        terminals.put('*', TokenType.STAR);
        terminals.put('/', TokenType.SLASH);
        terminals.put('(', TokenType.O_ROUND_BRACKET);
        terminals.put(')', TokenType.C_ROUND_BRACKET);
        terminals.put('.', TokenType.DOT);
        terminals.put(',', TokenType.COMMA);
        terminals.put(':', TokenType.COLON);
        terminals.put('%', TokenType.PERCENT);
        terminals.put('&', TokenType.AND);
        terminals.put('|', TokenType.PIPE);
        terminals.put('\0', TokenType.EOF);
        terminals.put('[', TokenType.O_BLOCK_BRACKET);
        terminals.put(']', TokenType.C_BLOCK_BRACKET);
    }
}
