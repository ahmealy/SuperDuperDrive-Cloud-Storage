package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

	@LocalServerPort
	protected int port;

	protected WebDriver driver;

	protected static String lUserName;
	protected static String lPassword;
	protected static String lFirstName;
	protected static String lLastName;

	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
		lUserName = "ahmedaly";
		lPassword = "20212022";
		lFirstName = "Ahmed";
		lLastName = "Aly";
	}

	@BeforeEach
	public void beforeEach() {
		this.driver = new ChromeDriver();
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			driver.quit();
		}
	}

	@Test
	public void getLoginPage() {
		driver.get("http://localhost:" + this.port + "/login");
		Assertions.assertEquals("Login", driver.getTitle());
	}

	protected HomePage signUpAndLogin() {
		driver.get("http://localhost:" + this.port + "/signup");
		SignupPage signupPage = new SignupPage(driver);
		signupPage.setFirstName(lFirstName);
		signupPage.setLastName(lLastName);
		signupPage.setUserName(lUserName);
		signupPage.setPassword(lPassword);
		signupPage.signUp();
		driver.get("http://localhost:" + this.port + "/login");
		LoginPage loginPage = new LoginPage(driver);
		loginPage.setUserName(lUserName);
		loginPage.setPassword(lPassword);
		loginPage.login();

		return new HomePage(driver);
	}
}
