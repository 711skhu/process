package com.shouwn.oj.run;

import java.util.ArrayList;
import java.util.List;

import com.shouwn.oj.command.OSCommand;
import com.shouwn.oj.command.WindowCommand;
import com.shouwn.oj.exception.IllegalStateException;
import com.shouwn.oj.model.entity.member.Admin;
import com.shouwn.oj.model.entity.problem.Course;
import com.shouwn.oj.model.entity.problem.Problem;
import com.shouwn.oj.model.entity.problem.ProblemDetail;
import com.shouwn.oj.model.entity.problem.TestCase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.shouwn.oj.model.enums.ProblemType.HOMEWORK;

public class JavaRunnerTest {

	private Admin professor;
	private Course newCourse;
	private Problem problem;
	private ProblemDetail problemDetail;
	private TestCase testCase;
	private List<TestCase> testCases;
	private String directoryPath;

	private OSCommand osCommand;

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

		this.directoryPath = "C:\\test";

		this.osCommand = new WindowCommand();
	}

	@Test
	public void runnerCommandTest() {
		Runner runnerWithPackageName = new JavaRunner("test.", "Test", "T:\\test", this.osCommand);
		Runner runnerWithoutPackageName = new JavaRunner("", "Test", "T:\\test", this.osCommand);

		StringBuilder sb = new StringBuilder();

		for (String s : runnerWithPackageName.getCommands()) {
			sb.append(s);
			sb.append(" ");
		}

		Assertions.assertEquals("cmd.exe /c java test.Test ", sb.toString());

		sb = new StringBuilder();

		for (String s : runnerWithoutPackageName.getCommands()) {
			sb.append(s);
			sb.append(" ");
		}

		Assertions.assertEquals("cmd.exe /c java Test ", sb.toString());
	}

	@Test
	public void runnerRunSuccess() {
		Runner runner = new JavaRunner("", "Test", directoryPath, this.osCommand);

		List<String> results = runner.run(testCases);

		Assertions.assertEquals("1", results.get(0));
	}

	@Test
	public void runnerRunFailure() {
		Runner runnerWrongFileName = new JavaRunner("", "TestFailure", directoryPath, this.osCommand);

		Assertions.assertThrows(IllegalStateException.class, () -> runnerWrongFileName.run(testCases));

		Runner runnerWrongPath = new JavaRunner("", "Test", directoryPath + "\\wrongPath", this.osCommand);

		Assertions.assertThrows(IllegalStateException.class, () -> runnerWrongPath.run(testCases));
	}

	@Test
	public void runnerRunExceptionResult() {
		Runner runner = new JavaRunner("", "Test", directoryPath, this.osCommand);
		testCase.setParams("s");

		List<String> results = runner.run(testCases);
		String exceptionMessage = results.get(results.size() - 1);

		Assertions.assertEquals(true, exceptionMessage.contains("Exception in thread"));
	}
}
