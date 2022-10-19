package com.danielweisshoff.parser.PError;

import com.danielweisshoff.lexer.Token;

public class UnimplementedError extends PError {

    public UnimplementedError(String msg, Token... tokens) {
        super("Unimplemented Error: " + msg, tokens);
    }

}
