package com.shouwn.oj.factory;

import java.util.ArrayList;
import java.util.List;

import com.shouwn.oj.machine.JavaProcessMachine;
import com.shouwn.oj.machine.ProcessMachine;
import com.shouwn.oj.model.entity.member.Admin;
import com.shouwn.oj.model.entity.problem.Course;
import com.shouwn.oj.model.entity.problem.Problem;
import com.shouwn.oj.model.entity.problem.ProblemDetail;
import com.shouwn.oj.model.entity.problem.TestCase;
import com.shouwn.oj.model.request.process.ProcessRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static com.shouwn.oj.model.enums.ProblemType.HOMEWORK;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ProcessFactoryTest {

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
				.params("1")
				.result("1")
				.problemDetail(problemDetail)
				.build();

		this.testCases = new ArrayList<>();
		testCases.add(testCase);
	}

	@Test
	public void constructorTestSuccess() {
		ProcessRequest processRequest = ProcessRequest.builder()
				.sourceCode("public class Main{}")
				.language("java")
				.pk(1)
				.testCases(testCases)
				.build();

		ProcessMachine processMachine = new ProcessMachineFactory(processRequest).getProcessMachine();

		Assertions.assertEquals(true, processMachine instanceof JavaProcessMachine);
	}

	@Test
	public void constructorTestFailure() {
		ProcessRequest processRequest = ProcessRequest.builder()
				.sourceCode("public class Main{}")
				.language("jaa")
				.pk(1)
				.testCases(testCases)
				.build();

		ProcessMachine processMachine = new ProcessMachineFactory(processRequest).getProcessMachine();

		Assertions.assertEquals(null, processMachine);
	}
}
