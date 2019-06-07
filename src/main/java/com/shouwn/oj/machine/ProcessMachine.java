package com.shouwn.oj.machine;

import java.util.List;

import com.shouwn.oj.command.OSCommand;
import com.shouwn.oj.compile.Compiler;
import com.shouwn.oj.exception.OJException;
import com.shouwn.oj.factory.OSCommandFactory;
import com.shouwn.oj.model.entity.problem.TestCase;
import com.shouwn.oj.model.request.process.ProcessRequest;
import com.shouwn.oj.run.Runner;
import com.shouwn.oj.sourceFile.SourceFile;
import com.shouwn.oj.util.OSUtils;

public abstract class ProcessMachine {

	protected long problemDetailId;
	protected String sourceCode;
	protected List<TestCase> testCases;

	protected Compiler compiler;
	protected Runner runner;
	protected SourceFile sourceFile;
	protected OSCommand osCommand;

	public ProcessMachine(ProcessRequest processRequest, List<TestCase> testCases, String os) {
		this.sourceCode = processRequest.getSourceCode();
		this.testCases = testCases;
		this.problemDetailId = processRequest.getProblemDetailId();
		this.osCommand = OSCommandFactory.createCommand(os);
	}

	public List<String> run() throws OJException {
		sourceFile.createSourceFile();

		compiler.compile();

		List<String> result = runner.run(testCases);

		sourceFile.deleteFolder();

		return result;
	}
}
