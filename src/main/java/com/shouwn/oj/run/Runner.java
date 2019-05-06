package com.shouwn.oj.run;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import com.shouwn.oj.exception.process.processMachine.RunFailedException;

import com.shouwn.oj.model.entity.problem.TestCase;
import com.shouwn.oj.util.StringUtils;
import lombok.Getter;

@Getter
public abstract class Runner {

	private String[] command;
	private String directoryPath;

	public Runner(String[] command, String directoryPath) {
		this.command = command;
		this.directoryPath = directoryPath;
	}

	public List<String> run(List<TestCase> testCases) {
		List<String> results = new ArrayList<>();

		ProcessBuilder processBuilder = new ProcessBuilder(command);
		processBuilder.directory(new File(directoryPath));

		try {
			for (int i = 0; i < testCases.size(); ++i) {
				Process process = processBuilder.start();

				BufferedWriter stdIn = new BufferedWriter(new OutputStreamWriter(process.getOutputStream()));
				BufferedReader stdOut = new BufferedReader(new InputStreamReader(process.getInputStream(), "EUC-KR"));
				BufferedReader stdErr = new BufferedReader(new InputStreamReader(process.getErrorStream(), "EUC-KR"));

				stdIn.write(testCases.get(i).getParams());
				stdIn.newLine();
				stdIn.flush();

				process.waitFor();

				String result = StringUtils.inputStreamToString(stdOut);
				String error = StringUtils.inputStreamToString(stdErr);
				if(result == null) {
					if(error.contains("오류")) {
						throw new RunFailedException("파일 실행중 오류가 발생했습니다.");
					}
					else {
						process.destroy();
						results.add(error);
						return results;
					}
				}

				results.add(result);
			}
		} catch (Exception e) {
			throw new RunFailedException("파일 실행중 오류가 발생했습니다.");
		}

		return results;
	}
}
