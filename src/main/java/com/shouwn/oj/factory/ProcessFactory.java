package com.shouwn.oj.factory;

import com.shouwn.oj.model.request.process.ProcessRequest;
import com.shouwn.oj.process.JavaProcess;
import com.shouwn.oj.process.Process;
import lombok.Getter;

@Getter
public class ProcessFactory {

	private Process process;

	// TODO 생성자 예외 발생 시 처리.
	public ProcessFactory(ProcessRequest processRequest) {
		switch(processRequest.getLanguage()) {
			case "java":
				process = new JavaProcess(processRequest);
				break;
			default:
				process = null;
		}
	}

}
