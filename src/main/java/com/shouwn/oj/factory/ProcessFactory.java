package com.shouwn.oj.factory;

import com.shouwn.oj.machine.JavaProcessMachine;
import com.shouwn.oj.machine.ProcessMachine;
import com.shouwn.oj.model.request.process.ProcessRequest;
import lombok.Getter;

@Getter
public class ProcessFactory {

	private ProcessMachine process;

	// TODO 생성자 예외 발생 시 처리.
	public ProcessFactory(ProcessRequest processRequest) {
		switch(processRequest.getLanguage()) {
			case "java":
				process = new JavaProcessMachine(processRequest);
				break;
			default:
				process = null;
		}
	}

}
