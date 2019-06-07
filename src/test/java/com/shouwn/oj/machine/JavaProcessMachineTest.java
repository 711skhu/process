package com.shouwn.oj.machine;

import java.util.ArrayList;
import java.util.List;

import com.shouwn.oj.factory.ProcessMachineFactory;
import com.shouwn.oj.model.entity.member.Admin;
import com.shouwn.oj.model.entity.problem.Course;
import com.shouwn.oj.model.entity.problem.Problem;
import com.shouwn.oj.model.entity.problem.ProblemDetail;
import com.shouwn.oj.model.entity.problem.TestCase;
import com.shouwn.oj.model.request.process.ProcessRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.shouwn.oj.model.enums.ProblemType.HOMEWORK;

public class JavaProcessMachineTest {

	private ProcessRequest processRequest;
	private Admin professor;
	private Course newCourse;
	private Problem problem;
	private ProblemDetail problemDetail;
	private TestCase testCase;
	private List<TestCase> testCases;

	@BeforeEach
	void init() {
		this.professor = Admin.builder()
				.username("junit_tester")
				.password("test123")
				.name("tester")
				.email("test@gmail.com")
				.build();

		this.newCourse = Course.builder()
				.name("junit_test")
				.description("test")
				.enabled(true)
				.professor(professor)
				.build();

		this.problem = Problem.builder()
				.type(HOMEWORK)
				.title("junit_test")
				.course(newCourse)
				.build();


		this.problemDetail = ProblemDetail.builder()
				.title("junit_test")
				.content("junit-test")
				.sequence(1)
				.problem(problem)
				.build();

		this.testCase = TestCase.builder()
				.params("test")
				.result("test")
				.problemDetail(problemDetail)
				.build();

		this.testCases = new ArrayList<>();
		testCases.add(testCase);

		this.processRequest = ProcessRequest.builder()
				.language("java")
				.problemDetailId(1)
				.sourceCode("public class Main {}")
				.build();
	}

	@Test
	public void javaProcessMachineRunTestSuccess() {
		ProcessMachine processMachine = ProcessMachineFactory.createProcessMachine(this.processRequest, this.testCases);

		Assertions.assertEquals(true, processMachine.run() instanceof List);
	}
}
