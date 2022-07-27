package com.danielweisshoff.parser.PError;

import com.danielweisshoff.lexer.Token;

public class ExpectedInstructionEndError extends PError {

	public ExpectedInstructionEndError(Token... tokens) {
		super("Expected end of instruction", tokens);
	}

}
