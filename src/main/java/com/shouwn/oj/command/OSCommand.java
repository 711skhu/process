package com.shouwn.oj.command;

import java.util.ArrayList;
import java.util.List;

public interface OSCommand {

	String commandLine();

	default List<String> createCommandList(String command, String fileName){
		List<String> commands = new ArrayList<>();
		commands.add(this.commandLine());
		commands.add(command);
		commands.add(fileName);
		return commands;
	}

	String createDirectoryPathCommand();

	String deleteFolderCommand();
}
