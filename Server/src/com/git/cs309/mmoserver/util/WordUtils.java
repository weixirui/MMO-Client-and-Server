package com.git.cs309.mmoserver.util;

public final class WordUtils {
	public static String capitalizeText(String text) {
		String[] words = text.split(" ");
		String finished = "";
		boolean cont = false;
		for (String word : words) {
			if (cont) {
				finished += " ";
			}
			finished += Character.toUpperCase(word.charAt(0)) + word.substring(1).toLowerCase();
			cont = true;
		}
		return finished;
	}
}
