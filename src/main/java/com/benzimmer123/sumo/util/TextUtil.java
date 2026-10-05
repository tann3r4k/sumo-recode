package com.benzimmer123.sumo.util;

public final class TextUtil {

	private TextUtil() {
	}

	public static String capitalize(String text) {
		if (text == null || text.isEmpty()) {
			return text;
		}
		String[] parts = text.split(" ");
		StringBuilder out = new StringBuilder();
		for (int i = 0; i < parts.length; i++) {
			if (i > 0) {
				out.append(' ');
			}
			String part = parts[i];
			if (part.isEmpty()) {
				continue;
			}
			out.append(Character.toUpperCase(part.charAt(0)));
			if (part.length() > 1) {
				out.append(part.substring(1).toLowerCase());
			}
		}
		return out.toString();
	}
}
