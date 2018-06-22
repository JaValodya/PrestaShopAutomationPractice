package com.prestashop.tests;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.Assertion;

import com.github.javafaker.Faker;

import io.github.bonigarcia.wdm.WebDriverManager;

public class LogInNegativeScenarios {

	static {
		WebDriverManager.chromedriver().setup();
	}
	static WebDriver driver = new ChromeDriver();

	// this method generates the credentials: email
	public static String generateEmail() {
		Faker faker = new Faker();
		return faker.internet().emailAddress();

	}

	// this method generates the credentials: invalid format email
	public static String generateInvalidFormatEmail() {
		return "InvalidFormatEmail";

	}

	// this method generates the credentials: password
	public static String generatePassword() {
		Faker faker = new Faker();
		return faker.internet().password(0, 10);

	}

	// this methods will fill the fields end submit them
	public static void submitCredentialsEmail(String Email) {

		driver.findElement(By.id("email")).sendKeys(Email);
		driver.findElement(By.id("SubmitLogin")).click();

	}

	public static void submitCredentialsPassword(String Password) {

		driver.findElement(By.id("passwd")).sendKeys(Password);
		driver.findElement(By.id("SubmitLogin")).click();

	}

	public static void submitCredentialsEmailAndPassword(String Email, String Password) {

		driver.findElement(By.id("email")).sendKeys(Email);
		driver.findElement(By.id("passwd")).sendKeys(Password);
		driver.findElement(By.id("SubmitLogin")).click();

	}

	// this method will get the actual text from the error field
	public static String getActual() {

		return driver.findElement(By.xpath("//div[@class='alert alert-danger']/ol/li")).getText();

	}

	@BeforeMethod
	public void goTo() {
		// WebDriverManager.chromedriver().setup();
		// driver = new ChromeDriver();
		driver.get("http://automationpractice.com");
		driver.findElement(By.className("login")).click();
	}

	@Test
	public void wrongCredentialsTest() {
		/*
		 * (login by entering a any wrong email (email must have a correct format) any
		 * password)
		 */
		// after:
		String expected = "Authentication failed.";
		submitCredentialsEmailAndPassword(generateEmail(), generatePassword());
		Assert.assertTrue(getActual().equals(expected));

	}

	@Test
	public void invalidEmailTest() {
		/* (try to login by entering a email with invalid format and any password) */

		String expected = "Invalid email address.";
		submitCredentialsEmailAndPassword(generateInvalidFormatEmail(), generatePassword());
		Assert.assertTrue(getActual().equals(expected));

	}

	@Test
	public void blankEmailTest() {
		/* try to login by leaving the email field empty and entering any password */
		String expected = "An email address required.";
		submitCredentialsPassword(generatePassword());
		Assert.assertTrue(getActual().equals(expected));
	}

	@Test
	public void blankPasswordTest() {
		/* try to login by entering an email and leaving the password field blank */
		String expected = "Password is required.";
		submitCredentialsEmail(generateEmail());
		Assert.assertTrue(getActual().equals(expected));
	}

	@AfterMethod
	public void backToLogIn() {
		driver.navigate().back();
		
	}

	@AfterClass
	public void tearDovnClass() {
		driver.quit();
	}

}

// this method generates the credentials email or password
// public static String generateCredentials(String type) {
// Faker faker = new Faker();
// if (type.equals("email")) {
// String email = faker.internet().emailAddress();
// return email;
// } else if (type.equals("password")) {
// String password = faker.internet().password();
// return password;
// } else {
// return "Uncnown Credational Type";
// }
//
// }

/*
 * @Test public void wrongCredentialsTest()before:
 */
// Faker faker = new Faker();
// String email = faker.internet().emailAddress();
// String password = faker.internet().password();
// String expected = "Authentication failed.";
// driver.findElement(By.id("email")).sendKeys(email);
// driver.findElement(By.id("passwd")).sendKeys(password);
// driver.findElement(By.id("SubmitLogin")).click();
// String actual = driver.findElement(By.xpath("//div[@class='alert
// alert-danger']/ol/li")).getText();
// Assert.assertTrue(getActual().equals(expected));