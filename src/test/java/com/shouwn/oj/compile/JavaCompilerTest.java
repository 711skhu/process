package com.shouwn.oj.compile;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class JavaCompilerTest {
	private String directoryPath;

	@BeforeEach
	void init() {
		this.directoryPath = "C:\\Users\\yeji\\Desktop\\711SKHUTESTFOLDER\\";
	}

	@Test
	public void compilerCommandTestSuccess() {
		Compiler compiler = new JavaCompiler("Test", directoryPath);

		StringBuilder sb = new StringBuilder();

		for (String s : compiler.getCommand()) {
			sb.append(s);
			sb.append(" ");
		}

		Assertions.assertEquals("cmd.exe /c javac -d . Test.java ", sb.toString());
	}

	@Test
	public void compilerCompileTestSuccess() throws Exception{
		Compiler compiler = new JavaCompiler("Test", directoryPath);

		compiler.compile();

		String[] command = { "cmd.exe", "/c", "dir", "/s", "/b", "Test.class" };

		ProcessBuilder processBuilder = new ProcessBuilder(command);
		processBuilder.directory(new File(directoryPath));

		Process p = processBuilder.start();

		BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream(), "EUC-KR"));

		String result = br.readLine();

		Assertions.assertEquals(directoryPath + "Test.class", result);
	}

	@Test
	public void compilerCompileTestFailure() throws Exception{
		Compiler compiler = new JavaCompiler("TestFailure", directoryPath);

		compiler.compile();

		String[] command = { "cmd.exe", "/c", "dir", "/s", "/b", "TestFailure.class" };

		ProcessBuilder processBuilder = new ProcessBuilder(command);
		processBuilder.directory(new File(directoryPath));

		Process p = processBuilder.start();

		BufferedReader br = new BufferedReader(new InputStreamReader(p.getErrorStream(), "EUC-KR"));

		String result = br.readLine();

		Assertions.assertEquals("파일을 찾을 수 없습니다.", result);
	}

}
