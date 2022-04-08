package com.danielweisshoff;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;

import com.danielweisshoff.interpreter.Interpreter;
import com.danielweisshoff.lexer.Lexer;
import com.danielweisshoff.lexer.Token;
import com.danielweisshoff.lexer.TokenType;
import com.danielweisshoff.parser.Parser;
import com.danielweisshoff.parser.nodesystem.node.Node;

public class Shell {

	public static boolean benchmark = true;

	public static void main(String[] args) {

		if (benchmark)
			benchmark();
		else
			run();
	}

	public static void run() {

		Goethe.clearLog();

		String text = Goethe.getText();
		Lexer lexer = new Lexer(text);
		Parser parser = new Parser();

		Token[] line;

		while (lexer.hasNextLine()) {
			line = lexer.nextLine();
			if (line.length == 0 || (line.length == 1 && line[0].type() == TokenType.TAB))
				continue;

			parser.parseLine(line);
		}

		//INTERPRETATION
		Interpreter interpreter = new Interpreter();
		interpreter.stepper = false;

		Node ast = parser.getAST();
		interpreter.interpret(ast);
	}

	//Modules are running separated and are benchmarked
	public static void benchmark() {
		Goethe.clearLog();

		String text = Goethe.getText();
		Lexer lexer = new Lexer(text);
		Parser parser = new Parser();

		Token[] line;
		ArrayList<Token[]> tokens = new ArrayList<>();

		//LEXING
		start();
		while (lexer.hasNextLine()) {
			line = lexer.nextLine();
			if (line.length == 0 || (line.length == 1 && line[0].type() == TokenType.TAB))
				continue;
			tokens.add(line);
		}
		stop("LEXER");
		System.out.println(benchmarks);

		//PARSING
		start();
		for (Token[] t : tokens) {
			parser.parseLine(t);
		}
		stop("PARSER");

		//INTERPRETATION
		Interpreter interpreter = new Interpreter();
		interpreter.stepper = false;
		Node ast = parser.getAST();

		//INTERPRETING
		start();
		interpreter.interpret(ast);
		stop("INTERPRETER");

		System.out.println(benchmarks);
		System.out.println("all done in " + benchmarkMS + " ms");
	}

	private static Instant start, end;
	private static String benchmarks = "";
	private static long benchmarkMS;

	public static void start() {
		start = Instant.now();
	}

	public static void stop(String msg) {
		end = Instant.now();
		long time = Duration.between(start, end).toMillis();
		benchmarks += msg + " done in " + time + "ms\n";
		benchmarkMS += time;
	}

}
