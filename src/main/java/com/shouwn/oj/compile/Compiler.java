package com.shouwn.oj.compile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

import com.shouwn.oj.command.OSCommand;
import com.shouwn.oj.exception.IllegalStateException;
import com.shouwn.oj.factory.ProcessFactory;
import lombok.Getter;

@Getter
public abstract class Compiler {

	private List<String> commands;
	private String directoryPath;

	protected OSCommand osCommand;

	public Compiler(List<String> commands, String directoryPath) {
		this.directoryPath = directoryPath;
		this.commands = commands;
	}

	public void compile() {
		try {
			Process process = ProcessFactory.processRun(this.commands, this.directoryPath);

			BufferedReader stdErr = new BufferedReader(new InputStreamReader(process.getErrorStream(), "EUC-KR"));

			if (stdErr.readLine() != null) {
				throw new IllegalStateException("컴파일 실행중 오류가 발생했습니다.");
			}

		} catch (Exception e) {
			throw new IllegalStateException("컴파일 실행중 오류가 발생했습니다.");
		}
	}
}
