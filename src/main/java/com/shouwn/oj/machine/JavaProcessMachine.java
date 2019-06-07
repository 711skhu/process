package com.shouwn.oj.machine;

import java.util.List;

import com.shouwn.oj.compile.JavaCompiler;
import com.shouwn.oj.factory.OSCommandFactory;
import com.shouwn.oj.model.entity.problem.TestCase;
import com.shouwn.oj.model.request.process.ProcessRequest;
import com.shouwn.oj.run.JavaRunner;
import com.shouwn.oj.sourceFile.JavaSourceFile;

public class JavaProcessMachine extends ProcessMachine {

	public JavaProcessMachine(ProcessRequest processRequest, List<TestCase> testCases, String os) {
		super(processRequest, testCases, os);
		this.sourceFile = new JavaSourceFile(this.problemDetailId, sourceCode, this.osCommand);
		this.compiler = new JavaCompiler(sourceFile.getClassName(), sourceFile.getDirectoryPath(), this.osCommand);
		this.runner = new JavaRunner(sourceFile.getPackageName(), sourceFile.getClassName(), sourceFile.getDirectoryPath(), this.osCommand);
	}
}
