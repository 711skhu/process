package com.shouwn.oj.compile;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import lombok.Getter;

@Getter
public abstract class Compiler {

	private String[] command;
	private String directoryPath;

	public Compiler(String[] command, String directoryPath) {
		this.command = command;
		this.directoryPath = directoryPath;
	}

	public void compile() {
		ProcessBuilder processBuilder = new ProcessBuilder(command);
		processBuilder.directory(new File(directoryPath));

		try {
			Process process = processBuilder.start();

			BufferedReader stdErr = new BufferedReader(new InputStreamReader(process.getErrorStream(), "EUC-KR"));

			if (stdErr.readLine() != null) {
				//throw new Exception(); //파일이 존재하지 않을 때 발생하는 에러
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
