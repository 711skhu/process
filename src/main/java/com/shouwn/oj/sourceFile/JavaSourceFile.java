package com.shouwn.oj.sourceFile;

import com.shouwn.oj.command.OSCommand;

public class JavaSourceFile extends SourceFile {
	public JavaSourceFile(long problemDetailId, String sourceCode, OSCommand osCommand) {
		super(problemDetailId, sourceCode, ".java", osCommand);
	}
}
