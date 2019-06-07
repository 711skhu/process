package com.shouwn.oj.command;

public class WindowCommand implements OSCommand {

	@Override
	public String commandLine() {
		return "cmd.exe /c";
	}

	@Override
	public String createDirectoryPathCommand() {
		return "md";
	}

	@Override
	public String deleteFolderCommand() {
		return "rd /s/q";
	}
}
