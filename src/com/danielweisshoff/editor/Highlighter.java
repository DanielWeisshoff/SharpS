package com.danielweisshoff.editor;

import com.danielweisshoff.lexer.Token;
import com.danielweisshoff.lexer.TokenType;

/*
 * TODO
 *  - Problem mit Kommentaren, da sie vom Lexer aussortiert werden
 *  - Soll smart gemacht werden. Farbverteilung durch durchgehen der Tokens wie beim Parser
 */

/**
 * Really bad implementation. Atm this ist just for testing and not a reliable option!!!11!!one!1
 */
public class Highlighter {

	public static String ERROR;
	public static String KEYWORD;
	public static String IDENTIFIER;
	public static String STRING;
	public static String NUMBER;
	public static String DOT;
	public static String PARENTHESES;
	public static String DEFAULT;
	public static String CLASS;
	public static String COMMENT;
	public static String OPERATOR;
	// public static String ATTRIBUTE; //in rot
	//public static String FUNCTION;  //in blau, wie operator
	//public static String STATIC;   // in rot, wie attribute

	private static boolean nextIsClass = false;

	public static void Highlight(Token[] tokens) {
		for (Token t : tokens) {
			if (t.getValue().equals("print")) // Nur zum schnellen Testen xD
				print(OPERATOR + t.getValue() + " ");
			else if (t.getValue().equals("str")) // Nur zum schnellen Testen xD
				print(CLASS + t.getValue() + " ");
			else if (t.getValue().equals("test")) // Nur zum schnellen Testen xD
				print(OPERATOR + t.getValue() + " ");
			else if (nextIsClass) {
				print(CLASS + t.getValue() + " ");
				nextIsClass = false;
			} else {
				switch (t.type()) {
				case ADD -> print(OPERATOR + " + ");
				case SUB -> print(OPERATOR + " - ");
				case MUL -> print(OPERATOR + " * ");
				case DIV -> print(OPERATOR + " / ");
				case EQUAL -> print(OPERATOR + " = ");
				case COLON -> print(OPERATOR + ": ");
				case DOT -> print(DOT + ".");
				case COMMA -> print(DOT + ",");
				case O_ROUND_BRACKET -> print(PARENTHESES + "(");
				case C_ROUND_BRACKET -> print(PARENTHESES + ")");
				case KEYWORD -> print(KEYWORD + t.getValue() + " ");
				case IDENTIFIER -> print(IDENTIFIER + t.getValue() + " ");
				case NUMBER, FLOAT -> print(NUMBER + t.getValue() + " ");
				case STRING -> print(STRING + '"' + t.getValue() + '"');
				case NEWLINE -> print("\n");
				case COMMENT -> print(COMMENT + t.getValue());
				case TAB -> tabulate(Integer.parseInt(t.getValue()));
				default -> print(t.getValue());
				}
				if (t.type() == TokenType.KEYWORD && t.getValue().equals("cls")) {
					nextIsClass = true;
				}
			}
		}
		System.out.println("\n\n");
	}

	private static void print(String msg) {
		System.out.print(msg + "\u001b[0m");
	}

	private static void tabulate(int tabs) {
		for (int i = 0; i < tabs; i++)
			print("\t");
	}

	public static void printColors() {
		for (int colorId = 0; colorId < 256; colorId++) {
			System.out.print(colorId);
			System.out.print("\u001b[38;5;" + colorId + "m Beispieltext ");
			System.out.println("\u001b[48;5;" + colorId + "m  Easter Egg!\u001b[0m");
		}
	}

	public static void loadTheme(ColorTheme theme) {
		for (ColorSet set : theme.colorTheme()) {
			switch (set.getSyntax()) {
			case "error" -> ERROR = "\u001b[38;5;" + set.getColor() + "m";
			case "keyword" -> KEYWORD = "\u001b[38;5;" + set.getColor() + "m";
			case "identifier" -> IDENTIFIER = "\u001b[38;5;" + set.getColor() + "m";
			case "string" -> STRING = "\u001b[38;5;" + set.getColor() + "m";
			case "number" -> NUMBER = "\u001b[38;5;" + set.getColor() + "m";
			case "dot" -> DOT = "\u001b[38;5;" + set.getColor() + "m";
			case "parentheses" -> PARENTHESES = "\u001b[38;5;" + set.getColor() + "m";
			case "default" -> DEFAULT = "\u001b[38;5;" + set.getColor() + "m";
			case "class" -> CLASS = "\u001b[38;5;" + set.getColor() + "m";
			case "comment" -> COMMENT = "\u001b[38;5;" + set.getColor() + "m";
			case "operator" -> OPERATOR = "\u001b[38;5;" + set.getColor() + "m";
			}
		}
	}
	/*
	 *   \u001b[38;5;0m             //Textfarbe Nr. 0
	 *   \u001b[48;5;0m             //Hintergrundfarbe Nr. 0
	 *
	 *   \u001b[1m                  //Bold
	 *   \u001b[4m                  //Underlined
	 *   \u001b[7m                  //Reversed
	 *   \u001b[0m                  //Reset
	 */
}
