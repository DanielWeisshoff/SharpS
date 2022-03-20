package com.danielweisshoff.parser.builders;

import com.danielweisshoff.lexer.TokenType;
import com.danielweisshoff.parser.Parser;

public class ParameterBuilder {

	/*
	 *erstmals nur placeholder
	 */
	public static String buildParameters(Parser p) {
		String params = "";

		while (p.curToken.type() != TokenType.C_ROUND_BRACKET) {
			if (p.curToken.type() == TokenType.NUMBER || p.curToken.type() == TokenType.IDENTIFIER) {
				params += p.curToken.getValue();
				p.advance();
				if (p.curToken.type() == TokenType.COMMA) {
					params += ", ";
					p.advance();
				} else
					break;

			}
		}
		p.advance();

		return params;
	}
}
