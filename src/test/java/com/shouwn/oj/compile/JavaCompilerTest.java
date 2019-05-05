package com.shouwn.oj.compile;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class JavaCompilerTest {

	@Test
	public void compilerCommandTestSuccess() {
		Compiler compiler = new JavaCompiler("Test", "T:\\test");

		StringBuilder sb = new StringBuilder();

		for (String s : compiler.getCommand()) {
			sb.append(s);
			sb.append(" ");
		}

		Assertions.assertEquals("cmd.exe /c javac -d . Test.java ", sb.toString());
	}

	@Test
	public void compilerCompileTestSuccess() throws Exception{
		Compiler compiler = new JavaCompiler("Test", "C:\\test");

		compiler.compile();

		String[] command = { "cmd.exe", "/c", "dir", "/s", "/b", "Test.class" };

		ProcessBuilder processBuilder = new ProcessBuilder(command);
		processBuilder.directory(new File("C:\\test"));

		Process p = processBuilder.start();

		BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream(), "EUC-KR"));

		String result = br.readLine();

		Assertions.assertEquals("C:\\test\\Test.class", result);
	}

	@Test
	public void compilerCompileTestFailure() throws Exception{
		Compiler compiler = new JavaCompiler("TestFailure", "C:\\test");

		compiler.compile();

		String[] command = { "cmd.exe", "/c", "dir", "/s", "/b", "TestFailure.class" };

		ProcessBuilder processBuilder = new ProcessBuilder(command);
		processBuilder.directory(new File("C:\\test"));

		Process p = processBuilder.start();

		BufferedReader br = new BufferedReader(new InputStreamReader(p.getErrorStream(), "EUC-KR"));

		String result = br.readLine();

		Assertions.assertEquals("파일을 찾을 수 없습니다.", result);
	}

}
