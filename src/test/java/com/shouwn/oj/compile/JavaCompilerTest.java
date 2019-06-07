package com.shouwn.oj.compile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

import com.shouwn.oj.command.OSCommand;
import com.shouwn.oj.command.WindowCommand;
import com.shouwn.oj.exception.IllegalStateException;
import com.shouwn.oj.factory.ProcessFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class JavaCompilerTest {

	private String directoryPath;

	private OSCommand osCommand;

	@BeforeEach
	void init() {
		this.directoryPath = "C:\\test";
		this.osCommand = new WindowCommand();
	}

	@Test
	public void compilerCommandTestSuccess() {
		Compiler compiler = new JavaCompiler("Test", directoryPath, osCommand);

		StringBuilder sb = new StringBuilder();

		for (String s : compiler.getCommands()) {
			sb.append(s);
			sb.append(" ");
		}

		Assertions.assertEquals("cmd.exe /c javac -d . Test.java ", sb.toString());
	}

	@Test
	public void compilerCompileTestSuccess() throws Exception {
		Compiler compiler = new JavaCompiler("Test", directoryPath, osCommand);
		compiler.compile();

		List<String> commands = Arrays.asList("cmd.exe", "/c", "dir", "/s", "/b", "Test.class");

		Process p = ProcessFactory.processRun(commands, directoryPath);

		BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream(), "EUC-KR"));
		String result = br.readLine();

		Assertions.assertEquals(directoryPath + "\\Test.class", result);
	}

	@Test
	public void compilerCompileTestFailure() {
		Compiler compilerWrongFileName = new JavaCompiler("TestFailure", directoryPath, osCommand);

		Assertions.assertThrows(IllegalStateException.class, () -> compilerWrongFileName.compile());

		Compiler compilerWrongPath = new JavaCompiler("Test", directoryPath + "\\wrongPath", osCommand);

		Assertions.assertThrows(IllegalStateException.class, () -> compilerWrongPath.compile());
	}

}
