package com.shouwn.oj.sourceFile;

import java.io.*;
import com.shouwn.oj.factory.ProcessFactory;
import com.shouwn.oj.util.FileNameUtils;
import lombok.Getter;

@Getter
public abstract class SourceFile {
	private long pk;
	private String packageName;
	private String className;
	private String directoryPath;
	private String sourceCode;
	private String sourceFileExtension;
	private String defaultDirectoryPath = "C:\\Users\\yeji\\Desktop\\711SKHUTESTFOLDER\\"; //현재 cmd에서 테스트를 위한 경로. 삭제 예정.

	public SourceFile(long pk, String sourceCode, String sourceFileExtension) {
		this.pk = pk;
		this.sourceCode = sourceCode.trim();
		this.packageName = FileNameUtils.getSourceFilePackageName(sourceCode);
		this.className = FileNameUtils.getSourceFileClassName(sourceCode);
		this.directoryPath = createDirectoryPath();
		this.sourceFileExtension = sourceFileExtension;
	}

	public String createDirectoryPath() {
		String directoryPath = defaultDirectoryPath + pk;
		String[] command = {"cmd.exe", "/c", "md " + pk};

		try {
			Process process = ProcessFactory.processRun(command, defaultDirectoryPath);
			BufferedReader bufferedReaderError = new BufferedReader(new InputStreamReader(process.getErrorStream(), "EUC-KR"));
			String errorMessage = bufferedReaderError.readLine();

			if (errorMessage != null && errorMessage.contains("이미 있습니다.")) {
				throw new IllegalStateException("IllegalStateException : 소스파일 폴더 생성 실패.");
			}
		} catch (Exception e) {
			throw new IllegalStateException("IllegalStateException : 소스파일 폴더 생성 실패.");
		}
		return directoryPath;
	}

	public void createSourceFile() {
		try {
			OutputStream outputStream = new FileOutputStream(directoryPath + "//" + className + sourceFileExtension);
			byte[] by = sourceCode.getBytes();
			outputStream.write(by);
		} catch (Exception e) {
			throw new IllegalStateException("IllegalStateException : 소스파일 생성 실패.");
		}
	}

	public void deleteFolder() {
		String[] command = {"cmd.exe", "/c", "rd /s/q " + pk};
		ProcessBuilder processBuilder = new ProcessBuilder(command);
		processBuilder.directory(new File(defaultDirectoryPath));

		try {
			Process process = ProcessFactory.processRun(command, defaultDirectoryPath);
			BufferedReader bufferedReaderError = new BufferedReader(new InputStreamReader(process.getErrorStream(), "EUC-KR"));
			String errorMessage = bufferedReaderError.readLine();

			if (errorMessage != null && errorMessage.contains("찾을 수 없습니다.")) {
				throw new IllegalStateException("IllegalStateException : 소스파일 폴더 삭제 실패.");
			}
		} catch (Exception e) {
			throw new IllegalStateException("IllegalStateException : 소스파일 폴더 삭제 실패.");
		}
	}
}
