package com.shouwn.oj.sourceFile;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import com.shouwn.oj.util.FileNameUtils;
import lombok.Getter;

@Getter
public abstract class SourceFile {
	private String packageName;
	private String className;
	private String directoryPath;
	private String sourceCode;
	private String sourceFileExtension;

	public SourceFile(long pk, String sourceCode, String sourceFileExtension) {
		this.sourceCode = sourceCode;
		this.packageName = FileNameUtils.getSourceFilePackageName(sourceCode);
		this.className = FileNameUtils.getSourceFileClassName(sourceCode);
		this.directoryPath = createDirectoryPath(pk);
		this.sourceFileExtension = sourceFileExtension;
	}

	public String createDirectoryPath(long pk) {
		String defaultDirectoryPath = "C:\\Users";
		String directoryPath = defaultDirectoryPath + pk;
		String[] command = {"cmd.exe", "/c", "md " + directoryPath};
		ProcessBuilder processBuilder = new ProcessBuilder(command);
		try {
			processBuilder.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return directoryPath;
	}

	public void saveSourceFile() {
		try {
			OutputStream outputStream = new FileOutputStream(directoryPath + className + sourceFileExtension);
			byte[] by = sourceCode.getBytes();
			outputStream.write(by);
		} catch (Exception e) {
			e.getStackTrace();
		}
	}

	public void deleteFolder() {
		String[] command = {"cmd.exe", "/c", "rd /s/q " + directoryPath};
		ProcessBuilder processBuilder = new ProcessBuilder(command);
		try {
			processBuilder.start();
		} catch (IOException e) {
			e.getStackTrace();
		}
	}
}
