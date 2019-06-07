package com.shouwn.oj.factory;

import java.io.File;
import java.util.List;

public class ProcessFactory{

	public static Process processRun(List<String> commands, String directoryPath) throws Exception{
		ProcessBuilder processBuilder = new ProcessBuilder(commands);
		processBuilder.directory(new File(directoryPath));

		Process process = processBuilder.start();
		process.waitFor();

		return process;
	}
}