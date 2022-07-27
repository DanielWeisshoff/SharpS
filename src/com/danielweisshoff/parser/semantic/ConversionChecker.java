package com.danielweisshoff.parser.semantic;

//checks if the given expression can be casted to the primitive type
public class ConversionChecker {

	public static boolean isByte(String value) {
		try {
			Byte.parseByte(value);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public static boolean isShort(String value) {
		try {
			Short.parseShort(value);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public static boolean isInt(String value) {
		try {
			Integer.parseInt("" + value);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public static boolean isLong(String value) {
		try {
			Long.parseLong(value);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public static boolean isFloat(String value) {
		try {
			Float.parseFloat(value);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public static boolean isDouble(String value) {
		try {
			Double.parseDouble("" + value);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}
