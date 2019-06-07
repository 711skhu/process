package com.shouwn.oj.sourceFile;

import java.io.*;
import java.util.Arrays;
import java.util.List;

import com.shouwn.oj.command.OSCommand;
import com.shouwn.oj.command.WindowCommand;
import com.shouwn.oj.factory.ProcessFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class sourceFileTest {
	private long pk;
	private String sourceCode;
	private OSCommand osCommand;

	@BeforeEach
	void initEach() {
		this.pk = 123456;
		this.sourceCode = "package com.shouwn.oj.sourceFile;\n" +
				"\n" +
				"import java.util.regex.Matcher;\n" +
				"import java.util.regex.Pattern;\n" +
				"\n" +
				"public class FileNameUtils {}";
		this.osCommand = new WindowCommand();
	}

	@Test
	public void createDirectoryPathSuccess() {
		String directoryPath = "C:\\Users\\yeji\\Desktop";
		List<String> command = Arrays.asList("cmd.exe", "/c", "dir /s/b ", String.valueOf(pk));
		BufferedReader bufferedReader;
		BufferedReader bufferedReaderError;
		Process process;

		//현재 폴더를 저장하려는 위치에 폴더가 없는 것을 확인.
		try {
			process = ProcessFactory.processRun(command, directoryPath);
			bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream(), "EUC-KR"));
			bufferedReaderError = new BufferedReader(new InputStreamReader(process.getErrorStream(), "EUC-KR"));
			Assertions.assertEquals(null, bufferedReader.readLine());
			Assertions.assertEquals("파일을 찾을 수 없습니다.", bufferedReaderError.readLine());
		} catch (Exception e) {
			e.printStackTrace();
		}

		new JavaSourceFile(pk, sourceCode, osCommand); //폴더 생성

		//폴더 생성 후 폴더가 존재하는지 확인.
		try {
			process = ProcessFactory.processRun(command, directoryPath);
			bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream(), "EUC-KR"));
			bufferedReaderError = new BufferedReader(new InputStreamReader(process.getErrorStream(), "EUC-KR"));
			Assertions.assertEquals("C:\\Users\\yeji\\Desktop\\711SKHUTESTFOLDER\\" + pk, bufferedReader.readLine());
			Assertions.assertEquals(null, bufferedReaderError.readLine());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void createDirectoryPathExistSourceFolderExceptionSuccess() {
		String directoryPath = "C:\\Users\\yeji\\Desktop";
		BufferedReader bufferedReader;
		BufferedReader bufferedReaderError;
		Process process;
		List<String> command = Arrays.asList("cmd.exe", "/c", "dir /s/b ", String.valueOf(pk));

		try {
			process = ProcessFactory.processRun(command, directoryPath);
			bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream(), "EUC-KR"));
			bufferedReaderError = new BufferedReader(new InputStreamReader(process.getErrorStream(), "EUC-KR"));
			Assertions.assertEquals("C:\\Users\\yeji\\Desktop\\711SKHUTESTFOLDER\\" + pk, bufferedReader.readLine());
			Assertions.assertEquals(null, bufferedReaderError.readLine());
		} catch (Exception e) {
			e.printStackTrace();
		}

		Assertions.assertThrows(IllegalStateException.class, () -> new JavaSourceFile(pk, sourceCode, new WindowCommand()));
	}

	@Test
	public void createSourceFileSuccess() {
		String directoryPath = "C:\\Users\\yeji\\Desktop\\711SKHUTESTFOLDER";
		SourceFile sourceFile = new JavaSourceFile(pk, sourceCode, osCommand);
		List<String> command = Arrays.asList("cmd.exe", "/c", "dir /s/b ", sourceFile.getClassName() + ".java");
		BufferedReader bufferedReader;
		BufferedReader bufferedReaderError;
		Process process;

		//현재 폴더에 해당 소스파일이 없는 것을 확인.
		try {
			process = ProcessFactory.processRun(command, directoryPath);
			bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			bufferedReaderError = new BufferedReader(new InputStreamReader(process.getErrorStream(), "EUC-KR"));
			Assertions.assertEquals(null, bufferedReader.readLine());
			Assertions.assertEquals("파일을 찾을 수 없습니다.", bufferedReaderError.readLine());
		} catch (Exception e) {
			e.printStackTrace();
		}

		sourceFile.createSourceFile();

		try {
			process = ProcessFactory.processRun(command, directoryPath);
			bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream(), "EUC-KR"));
			bufferedReaderError = new BufferedReader(new InputStreamReader(process.getErrorStream(), "EUC-KR"));
			Assertions.assertEquals("C:\\Users\\yeji\\Desktop\\711SKHUTESTFOLDER\\" + pk + "\\" + sourceFile.getClassName() + ".java", bufferedReader.readLine());
			Assertions.assertEquals(null, bufferedReaderError.readLine());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void createSourceFileNotFoundSourceFolderExceptionSuccess() {
		String directoryPath = "C:\\Users\\yeji\\Desktop";
		List<String> command = Arrays.asList("cmd.exe", "/c", "dir /s/b ", String.valueOf(pk));
		BufferedReader bufferedReader;
		BufferedReader bufferedReaderError;
		Process process;
		SourceFile sourceFile = new JavaSourceFile(pk, sourceCode, osCommand);
		sourceFile.deleteFolder();

		//현재 파일을 저장하려는 위치에 폴더가 없는 것을 확인.
		try {
			process = ProcessFactory.processRun(command, directoryPath);
			bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream(), "EUC-KR"));
			bufferedReaderError = new BufferedReader(new InputStreamReader(process.getErrorStream(), "EUC-KR"));
			Assertions.assertEquals(null, bufferedReader.readLine());
			Assertions.assertEquals("파일을 찾을 수 없습니다.", bufferedReaderError.readLine());
		} catch (Exception e) {
			e.printStackTrace();
		}

		Assertions.assertThrows(IllegalStateException.class, () -> sourceFile.createSourceFile());
	}

	@Test
	public void deleteFolderSuccess() {
		String directoryPath = "C:\\Users\\yeji\\Desktop";
		List<String> command = Arrays.asList("cmd.exe", "/c", "dir /s/b ", String.valueOf(pk));
		BufferedReader bufferedReader;
		BufferedReader bufferedReaderError;
		Process process;

		try {
			process = ProcessFactory.processRun(command, directoryPath);
			bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			bufferedReaderError = new BufferedReader(new InputStreamReader(process.getErrorStream(), "EUC-KR"));
			Assertions.assertEquals(null, bufferedReader.readLine());
			Assertions.assertEquals("파일을 찾을 수 없습니다.", bufferedReaderError.readLine());
		} catch (Exception e) {
			e.printStackTrace();
		}

		SourceFile sourceFile = new JavaSourceFile(pk, sourceCode, osCommand);

		try {
			process = ProcessFactory.processRun(command, directoryPath);
			bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream(), "EUC-KR"));
			bufferedReaderError = new BufferedReader(new InputStreamReader(process.getErrorStream(), "EUC-KR"));
			Assertions.assertEquals("C:\\Users\\yeji\\Desktop\\711SKHUTESTFOLDER\\" + pk, bufferedReader.readLine());
			Assertions.assertEquals(null, bufferedReaderError.readLine());
		} catch (Exception e) {
			e.printStackTrace();
		}

		//delete Test
		sourceFile.deleteFolder();

		try {
			process = ProcessFactory.processRun(command, directoryPath);
			bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream(), "EUC-KR"));
			bufferedReaderError = new BufferedReader(new InputStreamReader(process.getErrorStream(), "EUC-KR"));
			Assertions.assertEquals(null, bufferedReader.readLine());
			Assertions.assertEquals("파일을 찾을 수 없습니다.", bufferedReaderError.readLine());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void deleteFolderNotFoundExceptionSuccess() {
		String directoryPath = "C:\\Users\\yeji\\Desktop";
		List<String> command = Arrays.asList("cmd.exe", "/c", "dir /s/b ", String.valueOf(pk));
		BufferedReader bufferedReader;
		BufferedReader bufferedReaderError;
		Process process;

		SourceFile sourceFile = new JavaSourceFile(pk, sourceCode, osCommand);
		sourceFile.deleteFolder();

		try {
			process = ProcessFactory.processRun(command, directoryPath);
			bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			bufferedReaderError = new BufferedReader(new InputStreamReader(process.getErrorStream(), "EUC-KR"));
			Assertions.assertEquals(null, bufferedReader.readLine());
			Assertions.assertEquals("파일을 찾을 수 없습니다.", bufferedReaderError.readLine());
		} catch (Exception e) {
			e.printStackTrace();
		}

		Assertions.assertThrows(IllegalStateException.class, () -> sourceFile.deleteFolder());
	}
}
