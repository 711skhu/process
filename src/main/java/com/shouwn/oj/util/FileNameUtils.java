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
		if (sourceCode.substring(0, 7).equals("package")) {
			String regex = "package[\\s]*([a-zA-Zㄱ-ㅎㅏ-ㅣ가-힣0-9._$]+)[\\s]*;";
			packageName = getMatchLetter(regex, sourceCode);
			if (packageName == null) {
				throw new RuntimeException();
			}
		} else {
			packageName = "";
		}
		return (packageName.isEmpty()) ? packageName : packageName + ".";
	}

	public static String getSourceFileClassName(String sourceCode) {
		String regex = "[\\s]*[public||private||protected][\\s]*class[\\s]*([a-zA-Zㄱ-ㅎㅏ-ㅣ가-힣0-9._$]+)[\\s]*\\{";
		String className = getMatchLetter(regex, sourceCode);
		if (className == null) {
			throw new RuntimeException();
		} else {
			return className;
		}
	}
}
