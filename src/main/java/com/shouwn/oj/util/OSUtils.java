package com.shouwn.oj.util;

public class OSUtils {

	public static String osType() {
		String os = System.getProperty("os.name").toLowerCase();
		return os;
	}
}
