package com.shouwn.oj.sourceFile;

import java.io.*;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class sourceFileTest {
	private long pk;
	private String sourceCode;

	@BeforeEach
	void initEach() {
		this.pk = 123456;
		this.sourceCode = "package com.shouwn.oj.sourceFile;\n" +
				"\n" +
				"import java.util.regex.Matcher;\n" +
				"import java.util.regex.Pattern;\n" +
				"\n" +
				"public class FileNameUtils {}";
	}

	@Test
	public void createDirectoryPath정상작동() {
		String[] command = {"cmd.exe", "/c", "cd C:\\Users\\yeji\\Desktop & dir /s/b ", String.valueOf(pk)};
		ProcessBuilder processBuilder = new ProcessBuilder(command);
		BufferedReader bufferedReader;
		BufferedReader bufferedReaderError;
		Process process;

		//현재 폴더를 저장하려는 위치에 해당 폴더가 존재하는지 확인.
		try {
			process = processBuilder.start();
			bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			bufferedReaderError = new BufferedReader(new InputStreamReader(process.getErrorStream(), "EUC-KR"));
			Assert.assertEquals(null, bufferedReader.readLine());
			Assert.assertEquals("파일을 찾을 수 없습니다.", bufferedReaderError.readLine());
		} catch (IOException e) {
			e.printStackTrace();
		}

		SourceFile sourceFile = new JavaSourceFile(pk, sourceCode); //폴더 생성
		sourceFile.createDirectoryPath(pk);
		processBuilder = new ProcessBuilder(command);

		//폴더 생성 후 폴더가 존재하는지 확인.
		try {
			process = processBuilder.start();
			bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream(), "EUC-KR"));
			bufferedReaderError = new BufferedReader(new InputStreamReader(process.getErrorStream(), "EUC-KR"));
			Assert.assertEquals("C:\\Users\\yeji\\Desktop\\711SKHUTESTFOLDER\\" + pk, bufferedReader.readLine());
			Assert.assertEquals(null, bufferedReaderError.readLine());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void saveSourceFile정상작동() {
		SourceFile sourceFile = new JavaSourceFile(pk, sourceCode);
		sourceFile.createDirectoryPath(pk);
		String[] command = {"cmd.exe", "/c", "cd C:\\Users\\yeji\\Desktop\\711SKHUTESTFOLDER & dir /s/b ", sourceFile.getClassName() + ".java"};
		ProcessBuilder processBuilder = new ProcessBuilder(command);
		BufferedReader bufferedReader;
		BufferedReader bufferedReaderError;
		Process process;

		try {
			process = processBuilder.start();
			bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			bufferedReaderError = new BufferedReader(new InputStreamReader(process.getErrorStream(), "EUC-KR"));
			Assert.assertEquals(null, bufferedReader.readLine());
			Assert.assertEquals("파일을 찾을 수 없습니다.", bufferedReaderError.readLine());
		} catch (IOException e) {
			e.printStackTrace();
		}

		sourceFile.saveSourceFile();

		processBuilder = new ProcessBuilder(command);

		try {
			process = processBuilder.start();
			bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream(), "EUC-KR"));
			bufferedReaderError = new BufferedReader(new InputStreamReader(process.getErrorStream(), "EUC-KR"));
			Assert.assertEquals("C:\\Users\\yeji\\Desktop\\711SKHUTESTFOLDER\\" + pk + "\\" + sourceFile.getClassName() + ".java", bufferedReader.readLine());
			Assert.assertEquals(null, bufferedReaderError.readLine());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void deleteFolder정상작동() {
		String[] command = {"cmd.exe", "/c", "cd C:\\Users\\yeji\\Desktop & dir /s/b ", String.valueOf(pk)};
		ProcessBuilder processBuilder = new ProcessBuilder(command);
		BufferedReader bufferedReader;
		BufferedReader bufferedReaderError;
		Process process;

		try {
			process = processBuilder.start();
			bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			bufferedReaderError = new BufferedReader(new InputStreamReader(process.getErrorStream(), "EUC-KR"));
			Assert.assertEquals(null, bufferedReader.readLine());
			Assert.assertEquals("파일을 찾을 수 없습니다.", bufferedReaderError.readLine());
		} catch (IOException e) {
			e.printStackTrace();
		}

		SourceFile sourceFile = new JavaSourceFile(pk, sourceCode);
		sourceFile.createDirectoryPath(pk);
		processBuilder = new ProcessBuilder(command);

		try {
			process = processBuilder.start();
			bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream(), "EUC-KR"));
			bufferedReaderError = new BufferedReader(new InputStreamReader(process.getErrorStream(), "EUC-KR"));
			Assert.assertEquals("C:\\Users\\yeji\\Desktop\\711SKHUTESTFOLDER\\" + pk, bufferedReader.readLine());
			Assert.assertEquals(null, bufferedReaderError.readLine());
		} catch (IOException e) {
			e.printStackTrace();
		}

		//delete Test
		sourceFile.deleteFolder();

		processBuilder = new ProcessBuilder(command);

		try {
			process = processBuilder.start();
			process.waitFor();
			bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream(), "EUC-KR"));
			bufferedReaderError = new BufferedReader(new InputStreamReader(process.getErrorStream(), "EUC-KR"));
			Assert.assertEquals(null, bufferedReader.readLine());
			Assert.assertEquals("파일을 찾을 수 없습니다.", bufferedReaderError.readLine());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
