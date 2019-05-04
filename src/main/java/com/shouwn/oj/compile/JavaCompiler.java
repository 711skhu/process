package com.shouwn.oj.compile;

public class JavaCompiler extends Compiler {

	public JavaCompiler(String fileName, String directoryPath) {
		super(new String[] { "javac", fileName, ".java" }, directoryPath);
	}

}
