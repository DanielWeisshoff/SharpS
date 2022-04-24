package com.danielweisshoff;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import com.danielweisshoff.lexer.Token;
import com.danielweisshoff.logger.Log;

/**
 * Responsible for all file writing and reading
 */
public class Goethe {

	private static final File lexerPath = new File("src/com/danielweisshoff/lexer.log");
	private static final File logPath = new File("src/com/danielweisshoff/log.log");
	private static final File programPath = new File("src/com/danielweisshoff/program.#s"); //ß = \u00df

	public static String getText() {
		StringBuilder program = new StringBuilder();
		try {
			BufferedReader scanner = new BufferedReader(new FileReader(programPath));//.useDelimiter("(\\b|\\B)");

			String line;
			while ((line = scanner.readLine()) != null)
				program.append(line + '\n');
			scanner.close();
		} catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
		}
		return program.toString();
	}

	public static void writeText(File file, String text, boolean append) {
		try {
			FileWriter myWriter = new FileWriter(file, append);
			myWriter.write(text);
			myWriter.close();
		} catch (IOException e) {
			System.out.println(e.getLocalizedMessage());
		}
	}

	public static void writeTokens(Token[] tokens) {
		StringBuilder tokenStr = new StringBuilder();
		for (Token t : tokens) {
			tokenStr.append(t.getDescription());
			tokenStr.append("\n");
		}
		writeText(lexerPath, tokenStr.toString(), false);
	}

	public static void writeLogs(ArrayList<Log> logs) {
		try {
			PrintWriter pw = new PrintWriter(logPath);
			for (Log l : logs)
				pw.println(l.getMessage());
			pw.close();
		} catch (Exception e) {
		}
	}

	public static void clearLog() {
		writeText(logPath, "", false);
	}
}
