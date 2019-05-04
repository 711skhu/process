package com.shouwn.oj.process;

import java.util.ArrayList;
import java.util.List;

import com.shouwn.oj.compile.Complier;
import com.shouwn.oj.model.entity.problem.TestCase;
import com.shouwn.oj.model.request.process.ProcessRequest;
import com.shouwn.oj.run.Runner;
import com.shouwn.oj.sourceFile.SourceFile;

public abstract class Process {
	protected String sourceCode;
	protected String language;
	protected List<TestCase> testCases;
	protected long pk;
	protected Complier complier;
	protected Runner runner;
	protected SourceFile sourceFile;

	public Process(ProcessRequest processRequest) {
		this.sourceCode = processRequest.getSourceCode();
		this.language = processRequest.getLanguage();
		this.testCases = processRequest.getTestcases();
		this.pk = processRequest.getPk();
	}

	public List<String> run() {
		List<String> result = new ArrayList<>();
		return result;
	}
}
