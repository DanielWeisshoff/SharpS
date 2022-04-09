package com.danielweisshoff;

import com.danielweisshoff.lexer.Token;
import com.danielweisshoff.logger.Log;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

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
			Scanner scanner = new Scanner(programPath).useDelimiter("(\\b|\\B)");
			while (scanner.hasNext())
				program.append(scanner.next());
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

	public static void writeLog(Log l) {
		writeText(logPath, l.getLogFormat(), true);
	}

	public static void clearLog() {
		writeText(logPath, "", false);
	}

	/*public File getFile(String totalPath) {
	    ClassLoader classLoader = getClass().getClassLoader();
	    return new File(classLoader.getResource(totalPath).getFile());
	}*/
}
