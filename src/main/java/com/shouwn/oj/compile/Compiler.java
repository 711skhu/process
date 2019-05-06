package com.shouwn.oj.compile;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import com.shouwn.oj.exception.process.processMachine.CompileFailedException;
import com.shouwn.oj.factory.ProcessFactory;
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
		try {
			Process process = ProcessFactory.processRun(command, directoryPath);

			BufferedReader stdErr = new BufferedReader(new InputStreamReader(process.getErrorStream(), "EUC-KR"));

			if (stdErr.readLine() != null) {
				throw new CompileFailedException("컴파일 실행중 오류가 발생했습니다.");
			}

		} catch (Exception e) {
			throw new CompileFailedException("컴파일 실행중 오류가 발생했습니다.");
		}
	}

}
