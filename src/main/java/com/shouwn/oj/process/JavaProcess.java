package com.shouwn.oj.process;

import com.shouwn.oj.compile.JavaCompiler;
import com.shouwn.oj.model.request.process.ProcessRequest;
import com.shouwn.oj.run.JavaRunner;
import com.shouwn.oj.sourceFile.JavaSourceFile;

public class JavaProcess extends Process {

	public JavaProcess(ProcessRequest processRequest) {
		super(processRequest);
		sourceFile = new JavaSourceFile(pk, sourceCode);
		complier = new JavaCompiler(sourceFile.getClassName(), sourceFile.getDirectoryPath());
		runner = new JavaRunner(sourceFile.getPackageName(), sourceFile.getClassName(), sourceFile.getDirectoryPath());
	}
}
