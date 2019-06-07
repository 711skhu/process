package com.shouwn.oj.command;

public class LinuxCommand implements OSCommand {

	@Override
	public String commandLine() {
		return "";
	}

	@Override
	public String createDirectoryPathCommand() {
		return null;
	}

	@Override
	public String deleteFolderCommand() {
		return null;
	}
}
