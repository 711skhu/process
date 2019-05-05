package com.shouwn.oj.factory;

import com.shouwn.oj.model.request.process.ProcessRequest;
import com.shouwn.oj.process.JavaProcess;
import com.shouwn.oj.process.Process;
import lombok.Getter;

@Getter
public class ProcessFactory {

	private Process process;

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
