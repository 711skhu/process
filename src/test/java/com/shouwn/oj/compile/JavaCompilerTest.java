package com.shouwn.oj.compile;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import com.shouwn.oj.exception.process.processMachine.CompileFailedException;
import com.shouwn.oj.factory.ProcessFactory;
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
		this.directoryPath = "C:\\test";
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

		Process p = ProcessFactory.processRun(command, directoryPath);

		BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream(), "EUC-KR"));
		String result = br.readLine();

		Assertions.assertEquals(directoryPath + "\\Test.class", result);
	}

	@Test
	public void compilerCompileTestFailure(){
		Compiler compilerWrongFileName = new JavaCompiler("TestFailure", directoryPath);

		Assertions.assertThrows(CompileFailedException.class, () -> compilerWrongFileName.compile());

		Compiler compilerWrongPath = new JavaCompiler("Test", directoryPath + "\\wrongPath");

		Assertions.assertThrows(CompileFailedException.class, () -> compilerWrongPath.compile());
	}

}
