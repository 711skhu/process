package com.shouwn.oj.factory;

import java.io.File;

public class ProcessFactory{

	public static Process processRun(String[] command, String directoryPath) throws Exception{
		ProcessBuilder processBuilder = new ProcessBuilder(command);
		processBuilder.directory(new File(directoryPath));

		Process process = processBuilder.start();

		System.out.println("팩토리");

		process.waitFor();

		System.out.println("팩토리");


		return process;
	}
}