package com.shouwn.oj.run;

public class JavaRunner extends Runner {

	public JavaRunner(String packageName, String fileName, String directoryPath) {
		super(new String[] { "cmd.exe", "/c", "java", packageName + fileName }, directoryPath);
	}
}
