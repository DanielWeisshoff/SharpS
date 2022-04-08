package com.danielweisshoff.utils;

import java.time.Duration;
import java.time.Instant;

//basic stopwatch for benchmarks
public class StopWatch {

	private static Instant start, end;
	private static long roundTime;

	public static void start() {
		start = Instant.now();
	}

	public static void round() {
		end = Instant.now();
		roundTime += Duration.between(start, end).toMillis();
	}

	public static void stop(String msg) {
		end = Instant.now();
		long time = Duration.between(start, end).toMillis();
		System.out.println(msg + " took " + time + "ms");
	}

	public static void stopRound(String msg) {
		end = Instant.now();
		System.out.println(msg + " took " + roundTime + "ms");
	}
}
