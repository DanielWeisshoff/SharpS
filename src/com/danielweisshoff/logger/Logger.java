package com.danielweisshoff.logger;

import com.danielweisshoff.Goethe;

import java.util.ArrayList;

public class Logger {

	public static boolean writeLogs = true;

	private static final ArrayList<Log> messages = new ArrayList<>();

	public static void log(Object o) {
		String msg = o.toString();
		Log l = new Log(msg);
		messages.add(l);

		if (writeLogs)
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
