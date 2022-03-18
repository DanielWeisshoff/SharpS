package com.danielweisshoff.parser.builders;

import com.danielweisshoff.lexer.TokenType;
import com.danielweisshoff.parser.Parser;

public class ParameterBuilder {

	/*TODO
	 *erstmals nur placeholder
	 */
	public static void buildParameters(Parser parser) {
		while (parser.curToken.type() != TokenType.C_ROUND_BRACKET)
			parser.advance();
		parser.advance();
	}
}
