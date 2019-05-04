package com.shouwn.oj.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileNameUtils {
	private static String getMatchLetter(String regex, String sourceCdoe) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(sourceCdoe);
		while (matcher.find()) {
			return matcher.group(1);
		}
		return null;
	}

	public static String getSourceFilePackageName(String sourceCode) {
		String packageName;
		String regex = "package[\\s]*([a-zA-Zㄱ-ㅎㅏ-ㅣ가-힣0-9.]+)[\\s]*;";
		packageName = getMatchLetter(regex, sourceCode);
		return (packageName == null) ? "" : packageName + ".";
	}

	public static String getSourceFileClassName(String sourceCode) {
		String regex = "[\\s]*public[\\s]*class[\\s]*([a-zA-Zㄱ-ㅎㅏ-ㅣ가-힣0-9.]+)[\\s]*";
		return getMatchLetter(regex, sourceCode);
	}
}
