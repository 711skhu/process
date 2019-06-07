package com.shouwn.oj.run;

import com.shouwn.oj.command.OSCommand;

public class JavaRunner extends Runner {

	public JavaRunner(String packageName, String fileName, String directoryPath, OSCommand osCommand) {
		super(osCommand.createCommandList("java", packageName + fileName), directoryPath);
	}
}
