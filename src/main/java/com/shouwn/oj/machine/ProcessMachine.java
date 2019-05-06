package com.shouwn.oj.machine;

import java.util.List;

import com.shouwn.oj.compile.Compiler;
import com.shouwn.oj.model.entity.problem.TestCase;
import com.shouwn.oj.model.request.process.ProcessRequest;
import com.shouwn.oj.run.Runner;
import com.shouwn.oj.sourceFile.SourceFile;

public abstract class ProcessMachine {
	protected String sourceCode;
	protected List<TestCase> testCases;
	protected long pk;

	protected Compiler compiler;
	protected Runner runner;
	protected SourceFile sourceFile;

	public ProcessMachine(ProcessRequest processRequest) {
		this.sourceCode = processRequest.getSourceCode();
		this.testCases = processRequest.getTestCases();
		this.pk = processRequest.getPk();
	}

	public List<String> run() throws Exception {
		sourceFile.createSourceFile();

		compiler.compile();

		List<String> result = runner.run(testCases);

		sourceFile.deleteFolder();

		return result;
	}
}
