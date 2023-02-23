package parser.PError;

import lexer.Token;

public class UnimplementedError extends PError {

    public UnimplementedError(String msg, Token... tokens) {
        super("Unimplemented Error: " + msg, tokens);
    }

}
