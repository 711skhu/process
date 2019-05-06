package com.shouwn.oj.factory;

import java.io.File;

public class ProcessFactory{
	public static Process processRun(String[] command, String directoryPath) throws Exception{
		ProcessBuilder processBuilder = new ProcessBuilder(command);
		processBuilder.directory(new File(directoryPath));

		try {
			Process process = processBuilder.start();
			process.waitFor();
			return process;
		}catch (Exception e) {
			throw new Exception();
		}
	}
}