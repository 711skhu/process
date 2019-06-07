package com.shouwn.oj.util;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class FileNameUtilsTest {
	@Test
	public void getSourceFilePackageNameTestWithPackageNameSuccess() {
		String sourceCode = "package com.Shouwn.oj.util.정규식테스트_$09;\n" +
				"\n" +
				"import java.util.regex.Matcher;\n" +
				"import java.util.regex.Pattern;\n" +
				"\n" +
				"public class FileNameUtils {}";
		Assert.assertEquals("com.Shouwn.oj.util.정규식테스트_$09.", FileNameUtils.getSourceFilePackageName(sourceCode));
	}

	//특문
	@Test
	public void getSourceFilePackageNameTestWithPackageNameWrongText() {
		String sourceCode = "package com.shouwn.oj.util.정규식테스트_!!*★$09;\n" +
				"\n" +
				"import java.util.regex.Matcher;\n" +
				"import java.util.regex.Pattern;\n" +
				"\n" +
				"public class FileNameUtils {}";
		Assertions.assertThrows(IllegalStateException.class, () -> FileNameUtils.getSourceFilePackageName(sourceCode));
	}

	//개행
	@Test
	public void getSourceFilePackageNameTestWithPackageNameWrongTextNewLine() {
		String sourceCode = "package com.\nshouwn.oj.\n" +
				"util.정규식테스트_$09;\n" +
				"\n" +
				"import java.util.regex.Matcher;\n" +
				"import java.util.regex.Pattern;\n" +
				"\n" +
				"public class FileNameUtils {}";
		Assertions.assertThrows(IllegalStateException.class, () -> FileNameUtils.getSourceFilePackageName(sourceCode));
	}

	//공백
	@Test
	public void getSourceFilePackageNameTestWithPackageNameWrongTextSpace() {
		String sourceCode = "package com. shouwn.oj.   util.정규식테스트_$09;\n" +
				"\n" +
				"import java.util.regex.Matcher;\n" +
				"import java.util.regex.Pattern;\n" +
				"\n" +
				"public class FileNameUtils {}";
		Assertions.assertThrows(IllegalStateException.class, () -> FileNameUtils.getSourceFilePackageName(sourceCode));
	}

	@Test
	public void getSourceFilePackageNameTestWithoutPackageNameSuccess() {
		String sourceCode = "import java.util.regex.Matcher;\n" +
				"import java.util.regex.Pattern;\n" +
				"\n" +
				"public class FileNameUtils {}";
		Assert.assertEquals("", FileNameUtils.getSourceFilePackageName(sourceCode));
	}

	@Test
	public void getSourceFilePackageNameTestWithoutPackageNameWrongText() {
		String sourceCode = "com.shouwn.oj.util.정규식테스트_$09;\n" +
				"\n" +
				"import java.util.regex.Matcher;\n" +
				"import java.util.regex.Pattern;\n" +
				"\n" +
				"public class FileNameUtils {}";
		Assert.assertEquals("", FileNameUtils.getSourceFilePackageName(sourceCode));
	}

	@Test
	public void getSourceFileClassNameSuccess() {
		String sourceCode = "package com.Shouwn.oj.util.정규식테스트_$09;\n" +
				"\n" +
				"import java.util.regex.Matcher;\n" +
				"import java.util.regex.Pattern;\n" +
				"\n" +
				"protected class $한글이름09FileName_Utils {}";
		Assert.assertEquals("$한글이름09FileName_Utils", FileNameUtils.getSourceFileClassName(sourceCode));
	}

	//특문
	@Test
	public void getSourceFileClassNameSuccessWrongText() {
		String sourceCode = "package com.Shouwn.oj.util.정규식테스트_$09;\n" +
				"\n" +
				"import java.util.regex.Matcher;\n" +
				"import java.util.regex.Pattern;\n" +
				"\n" +
				"protected class !FileNameUtils! {}";
		Assertions.assertThrows(IllegalStateException.class, () -> FileNameUtils.getSourceFileClassName(sourceCode));
	}

	//개행
	@Test
	public void getSourceFileClassNameSuccessWrongTextNewLine() {
		String sourceCode = "package com.Shouwn.oj.util.정규식테스트_$09;\n" +
				"\n" +
				"import java.util.regex.Matcher;\n" +
				"import java.util.regex.Pattern;\n" +
				"\n" +
				"protected class FileNa\nmeUtils\n {}";
		Assertions.assertThrows(IllegalStateException.class, () -> FileNameUtils.getSourceFileClassName(sourceCode));
	}

	//공백
	@Test
	public void getSourceFileClassNameSuccessWrongTextSpace() {
		String sourceCode = "package com.Shouwn.oj.util.정규식테스트_$09;\n" +
				"\n" +
				"import java.util.regex.Matcher;\n" +
				"import java.util.regex.Pattern;\n" +
				"\n" +
				"protected class FileNa   meUtils {}";
		Assertions.assertThrows(IllegalStateException.class, () -> FileNameUtils.getSourceFileClassName(sourceCode));
	}
}
