package com.shouwn.oj.sourceFile;

import java.io.*;
import java.util.List;

import com.shouwn.oj.command.OSCommand;
import com.shouwn.oj.factory.ProcessFactory;
import com.shouwn.oj.util.FileNameUtils;
import lombok.Getter;

@Getter
public abstract class SourceFile {

	private long problemDetailId;
	private String packageName;
	private String className;
	private String directoryPath;
	private String sourceCode;
	private String sourceFileExtension;
	private String defaultDirectoryPath = "C:\\Users\\yeji\\Desktop\\711SKHUTESTFOLDER\\"; //현재 cmd에서 테스트를 위한 경로. 삭제 예정.

	private OSCommand osCommand;

	public SourceFile(long problemDetailId, String sourceCode, String sourceFileExtension, OSCommand osCommand) {
		this.problemDetailId = problemDetailId;
		this.sourceCode = sourceCode.trim();
		this.packageName = FileNameUtils.getSourceFilePackageName(sourceCode);
		this.className = FileNameUtils.getSourceFileClassName(sourceCode);
		this.directoryPath = createDirectoryPath();
		this.sourceFileExtension = sourceFileExtension;
		this.osCommand = osCommand;
	}

	public String createDirectoryPath() {
		String directoryPath = defaultDirectoryPath + problemDetailId;
		String command = osCommand.createDirectoryPathCommand();
		List<String> commands = osCommand.createCommandList(command, String.valueOf(this.problemDetailId));

		try {
			Process process = ProcessFactory.processRun(commands, defaultDirectoryPath);
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
		String command = osCommand.deleteFolderCommand();
		List<String> commands = osCommand.createCommandList(command, String.valueOf(this.problemDetailId));

		try {
			Process process = ProcessFactory.processRun(commands, defaultDirectoryPath);
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
