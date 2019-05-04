package com.shouwn.oj.compile;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public abstract class Compiler {

	private String[] command;
	private String directoryPath;

	public Compiler(String[] command, String directoryPath) {
		this.command = command;
		this.directoryPath = directoryPath;
	}

	public void compile() throws IOException {
		ProcessBuilder processBuilder = new ProcessBuilder(command);
		processBuilder.directory(new File(directoryPath));

		Process process = processBuilder.start();

		BufferedReader stdErr = new BufferedReader(new InputStreamReader(process.getErrorStream()));

		if(stdErr.readLine() != null) {
			//throw new Exception(); //파일이 존재하지 않을 때 발생하는 에러
		}
	}
}
