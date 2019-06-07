package com.shouwn.oj.util;

import java.io.BufferedReader;
import java.io.IOException;

public class StringUtils {

	public static String inputStreamToString(BufferedReader bufferedReader) throws IOException {
		StringBuilder sb = new StringBuilder();

		String line;
		while ((line = bufferedReader.readLine()) != null) {
			sb.append(line);
		}

		bufferedReader.close();

		String string = sb.toString();

		return string.length() == 0 ? null : string;
	}
}
