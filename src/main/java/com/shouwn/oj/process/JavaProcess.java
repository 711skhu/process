package com.shouwn.oj.process;

import com.shouwn.oj.compile.JavaCompiler;
import com.shouwn.oj.model.request.process.ProcessRequest;
import com.shouwn.oj.run.JavaRunner;
import com.shouwn.oj.sourceFile.JavaSourceFile;

public class JavaProcess extends Process {

	public JavaProcess(ProcessRequest processRequest) {
		super(processRequest);
		complier = new JavaCompiler();
		runner = new JavaRunner();
		sourceFile = new JavaSourceFile(pk, sourceCode);
	}
}
