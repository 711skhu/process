package com.shouwn.oj.command;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public interface OSCommand {

	String commandLine();

	default List<String> createCommandList(String command, String fileName){
		StringTokenizer commandLineToken = new StringTokenizer(this.commandLine());
		StringTokenizer commandToken = new StringTokenizer(command);

		List<String> commands = new ArrayList<>();

		while (commandLineToken.hasMoreTokens()) {
			commands.add(commandLineToken.nextToken());
		}

		while (commandToken.hasMoreTokens()) {
			commands.add(commandToken.nextToken());
		}

		commands.add(fileName);

		return commands;
	}

	String createDirectoryPathCommand();

	String deleteFolderCommand();
}
