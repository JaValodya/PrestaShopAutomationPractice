package com.prestashop.tests;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import io.github.bonigarcia.wdm.WebDriverManager;

public class LogInPositiveScenarios {

	static {
		WebDriverManager.chromedriver().setup();
	}
	static WebDriver driver = new ChromeDriver();

	// this method generates the credentials: email
	public static String generateEmail() {
		Faker faker = new Faker();
		return faker.internet().emailAddress();

	}

	// this method generates the credentials: first name
	public static String generateFirstName() {
		Faker faker = new Faker();
		return faker.name().firstName();

	}

	// this method generates the credentials: last name
	public static String generateLastName() {
		Faker faker = new Faker();
		return faker.name().lastName();

	}

	// this method generates the credentials: password
	public static String generatePssword() {
		Faker faker = new Faker();
		return faker.internet().password(5, 7);

	}

	// this method generates the credentials: Address
	public static String generateAddress() {
		Faker faker = new Faker();
		return faker.address().streetAddress();

	}

	// this method generates the credentials: City
	public static String generateCity() {
		Faker faker = new Faker();
		return faker.address().city();

	}

	// this method generates the credentials: ZipCode
	public static String generateZipCode() {
		Faker faker = new Faker();
		return faker.address().zipCode().substring(0, 5);

	}

	// this method generates the credentials: Mobile phone
	public static String generateMobilePhoneNumber() {
		Faker faker = new Faker();
		return faker.phoneNumber().cellPhone();

	}

	public static List<String> generateNewUser() {

		List<String> credentials = new ArrayList<String>();
		String email = generateEmail();
		credentials.add(email);

		String firstName = generateFirstName();
		credentials.add(firstName);

		String lastName = generateLastName();
		credentials.add(lastName);

		String password = generatePssword();
		credentials.add(password);

		Random random = new Random();
		int randomGender = random.nextInt(2 - 1) + 1; // 1-Mr. 2-Mrs.

		String address = generateAddress();
		String city = generateCity();
		String zipCode = generateZipCode();
		String cellPhoneNumber = generateMobilePhoneNumber();
		int randomState = random.nextInt(50 - 1) + 1;

		driver.findElement(By.id("email_create")).sendKeys(email);
		driver.findElement(By.id("SubmitCreate")).click();
		driver.findElement(By.id("uniform-id_gender" + randomGender)).click();
		driver.findElement(By.id("customer_firstname")).sendKeys(firstName);
		driver.findElement(By.id("customer_lastname")).sendKeys(lastName);
		// email is transfered after entered on the previous page

		driver.findElement(By.id("passwd")).sendKeys(password);

		driver.findElement(By.id("address1")).sendKeys(address);
		driver.findElement(By.id("city")).sendKeys(city);

		WebElement states = driver.findElement(By.id("id_state"));
		Select list = new Select(states);
		list.selectByIndex(randomState);

		driver.findElement(By.id("postcode")).sendKeys(zipCode);
		driver.findElement(By.id("phone_mobile")).sendKeys(cellPhoneNumber);

		driver.findElement(By.id("submitAccount")).click();

		return credentials;
	}

	// logout method
	public static void logOut() {
		driver.findElement(By.className("logout")).click();
	}

	public static void submitCredentialsEmailAndPassword(String Email, String Password) {

		driver.findElement(By.id("email")).sendKeys(Email);
		driver.findElement(By.id("passwd")).sendKeys(Password);
		driver.findElement(By.id("SubmitLogin")).click();

	}

	// Get user name from account page
	public static String getUserName() {

		return driver.findElement(By.xpath("//a[@class = 'account']/span")).getText();
	}

	@BeforeMethod
	public void goTo() {

		driver.get("http://automationpractice.com");
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		driver.findElement(By.className("login")).click();
	}

	@Test
	public void loginTest() {

		List<String> userCredentials = generateNewUser();
		String userEmail = userCredentials.get(0);
		String userFullName = userCredentials.get(1) + " " + userCredentials.get(2);
		String userPassword = userCredentials.get(3);

		logOut();

		submitCredentialsEmailAndPassword(userEmail, userPassword);

		System.out.println("getUserName()" + getUserName());
		System.out.println("userFullName" + userFullName);

		Assert.assertTrue(getUserName().equals(userFullName));

	}

	@AfterClass
	public void tearDovnClass() {
		driver.quit();
	}

}
