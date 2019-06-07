package com.shouwn.oj.service;

import java.util.LinkedList;
import java.util.List;

import com.shouwn.oj.factory.ProcessMachineFactory;
import com.shouwn.oj.model.entity.problem.TestCase;
import com.shouwn.oj.model.request.process.ProcessRequest;
import com.shouwn.oj.service.problem.TestCaseService;

import org.springframework.stereotype.Service;

@Service
public class ProcessServiceForProcess {
	private final TestCaseService testCaseService;

	public ProcessServiceForProcess(TestCaseService testCaseService) {
		this.testCaseService = testCaseService;
	}

	public int getSolutionResult(ProcessRequest processRequest) {
		List<TestCase> testCases = new LinkedList<>(testCaseService.findByProblemDetailIdOrderById(processRequest.getProblemDetailId()));
		List<String> result = ProcessMachineFactory.createProcessMachine(processRequest, testCases).run();
		return getCountOfMatchResult(testCases, result);
	}

	private int getCountOfMatchResult(List<TestCase> testCases, List<String> result) {
		List<String> userAnswers = new LinkedList<>(result);
		List<String> problemAnswers = new LinkedList<>();
		testCases.stream().forEach(testCase -> problemAnswers.add(testCase.getResult()));
		return compareAnswers(userAnswers, problemAnswers);
	}

	private int compareAnswers(List<String> userAnswers, List<String> problemAnswers) {
		int matchCount = 0;
		for(String problemAnswer : problemAnswers) {
			for(String userAnswer : userAnswers) {
				if(problemAnswer.equals(userAnswer)) {
					matchCount++;
				}
			}
		}
		return matchCount;
	}
}
