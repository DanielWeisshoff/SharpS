package parser.PError;

import lexer.Token;

public class ExpectedInstructionEndError extends PError {

    public ExpectedInstructionEndError(Token... tokens) {
        super("Expected end of instruction", tokens);
    }

}
