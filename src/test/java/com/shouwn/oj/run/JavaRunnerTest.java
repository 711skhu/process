package com.shouwn.oj.run;

import java.util.ArrayList;
import java.util.List;

import com.shouwn.oj.model.entity.member.Admin;
import com.shouwn.oj.model.entity.problem.Course;
import com.shouwn.oj.model.entity.problem.Problem;
import com.shouwn.oj.model.entity.problem.ProblemDetail;
import com.shouwn.oj.model.entity.problem.TestCase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static com.shouwn.oj.model.enums.ProblemType.HOMEWORK;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class JavaRunnerTest {

	private Admin professor;
	private Course newCourse;
	private Problem problem;
	private ProblemDetail problemDetail;
	private TestCase testCase;
	private List<TestCase> testCases;
	private String directoryPath;

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

		this.directoryPath = "C:\\Users\\yeji\\Desktop\\711SKHUTESTFOLDER\\";
	}

	@Test
	public void runnerCommandTest() {
		Runner runnerWithPackageName = new JavaRunner("test.", "Test", "T:\\test");
		Runner runnerWithoutPackageName = new JavaRunner("", "Test", "T:\\test");

		StringBuilder sb = new StringBuilder();

		for (String s : runnerWithPackageName.getCommand()) {
			sb.append(s);
			sb.append(" ");
		}

		Assertions.assertEquals("cmd.exe /c java test.Test ", sb.toString());

		sb = new StringBuilder();

		for (String s : runnerWithoutPackageName.getCommand()) {
			sb.append(s);
			sb.append(" ");
		}

		Assertions.assertEquals("cmd.exe /c java Test ", sb.toString());
	}

	@Test
	public void runnerRunSuccess() {
		Runner runner = new JavaRunner("", "Test", directoryPath);

		List<String> results = runner.run(testCases);

		Assertions.assertEquals("1", results.get(0));
	}

	@Test
	public void runnerRunFailure() {
		Runner runner = new JavaRunner("", "TestFailure", directoryPath);

		List<String> results = runner.run(testCases);

		Assertions.assertEquals("오류: 기본 클래스 TestFailure을(를) 찾거나 로드할 수 없습니다.", results.get(0));
	}
}
