package com.shouwn.oj.factory;

import com.shouwn.oj.command.LinuxCommand;
import com.shouwn.oj.command.OSCommand;
import com.shouwn.oj.command.WindowCommand;

public class OSCommandFactory {

	public static OSCommand createCommand(String os) {
		if (os.contains("window")) {
			return new WindowCommand();
		} else if (os.contains("linux")) {
			return new LinuxCommand();
		}
		return null;
	}
}
