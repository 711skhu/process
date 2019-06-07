package com.shouwn.oj.compile;

import com.shouwn.oj.command.OSCommand;

public class JavaCompiler extends Compiler {

	public JavaCompiler(String fileName, String directoryPath, OSCommand osCommand) {
		super(osCommand.createCommandList("javac -d .", fileName + ".java"), directoryPath);
	}
}
