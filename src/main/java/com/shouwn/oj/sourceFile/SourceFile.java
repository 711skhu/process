package com.shouwn.oj.sourceFile;

import java.io.FileOutputStream;
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
	private String defaultDirectoryPath = "C:\\Users\\yeji\\Desktop\\711SKHUTESTFOLDER\\"; //현재 cmd에서 테스트를 위한 경로. 삭제 예정.

	public SourceFile(long pk, String sourceCode, String sourceFileExtension) {
		this.sourceCode = sourceCode.trim();
		this.packageName = FileNameUtils.getSourceFilePackageName(sourceCode);
		this.className = FileNameUtils.getSourceFileClassName(sourceCode);
		this.directoryPath = createDirectoryPath(pk);
		this.sourceFileExtension = sourceFileExtension;
	}

	public String createDirectoryPath(long pk) {
		String directoryPath = defaultDirectoryPath + pk;
		String[] command = {"cmd.exe", "/c", "md " + directoryPath};
		ProcessBuilder processBuilder = new ProcessBuilder(command);
		try {
			Process process = processBuilder.start();
			process.waitFor();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return directoryPath;
	}

	public void saveSourceFile() {
		try {
			OutputStream outputStream = new FileOutputStream(directoryPath+"//" + className + sourceFileExtension);
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
			Process process = processBuilder.start();
			process.waitFor();
		} catch (Exception e) {
			e.getStackTrace();
		}
	}
}
