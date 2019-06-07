package com.shouwn.oj.factory;

import java.util.List;

import com.shouwn.oj.machine.JavaProcessMachine;
import com.shouwn.oj.machine.ProcessMachine;
import com.shouwn.oj.model.entity.problem.TestCase;
import com.shouwn.oj.model.request.process.ProcessRequest;
import com.shouwn.oj.util.OSUtils;
import lombok.Getter;

@Getter
public class ProcessMachineFactory {

	// TODO 생성자 예외 발생 시 처리
	public static ProcessMachine createProcessMachine(final ProcessRequest processRequest, List<TestCase> testCases) {
		switch (processRequest.getLanguage()) {
			case "java":
				return new JavaProcessMachine(processRequest, testCases, OSUtils.osType());
			default:
				return null;
		}
	}
}