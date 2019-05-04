package com.shouwn.oj.run;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.shouwn.oj.model.entity.problem.TestCase;
import com.shouwn.oj.util.StringUtils;

public abstract class Runner {

	protected String[] command;
	protected String directoryPath;

	public Runner(String[] command, String directoryPath) {
		this.command = command;
		this.directoryPath = directoryPath;
	}

	public List<String> run(List<TestCase> testCases) {
		List<String> results = new ArrayList<>();

		ProcessBuilder processBuilder = new ProcessBuilder(command);
		processBuilder.directory(new File(directoryPath));

		Process process;

		BufferedWriter stdIn;
		BufferedReader stdOut;
		BufferedReader stdErr;

		try {
			for (int i = 0; i < testCases.size(); ++i) {
				process = processBuilder.start();

				stdIn = new BufferedWriter(new OutputStreamWriter(process.getOutputStream()));
				stdOut = new BufferedReader(new InputStreamReader(process.getInputStream()));
				stdErr = new BufferedReader(new InputStreamReader(process.getErrorStream()));

				stdIn.write(testCases.get(i).getParams());
				stdIn.newLine();
				stdIn.flush();

				results.add(Optional.of(StringUtils.inputStreamToString(stdOut)).orElse(StringUtils.inputStreamToString(stdErr)));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}


		return results;
	}
}
