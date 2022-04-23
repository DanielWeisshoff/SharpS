package com.danielweisshoff.logger;

import java.util.ArrayList;

import com.danielweisshoff.Goethe;

public class Logger {

	public static boolean enabled = true;

	private static final ArrayList<Log> messages = new ArrayList<>();

	public static void log(String msg) {
		if (enabled)
			messages.add(new Log(msg));
	}

	public static void writeLogs() {
		for (Log l : messages)
			Goethe.writeLog(l);
	}

	public static void printAll() {
		for (Log l : messages) {
			System.out.println(l.getMessage());
		}
	}

	public static void clear() {
		messages.clear();
	}
}
