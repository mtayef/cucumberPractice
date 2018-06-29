package steps;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import helper.Utility;

public class Steps {
	
	WebDriver driver;
	
	@Given("^Application and browser up and running$")
	public void application_and_browser_up_and_running() throws Throwable {
	    System.setProperty("webdriver.chrome.driver", "./browsers/chromedriver.exe");
	    driver = new ChromeDriver();
	    driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
	    driver.get("http://opensource.demo.orangehrmlive.com/");
	}

	@And("^user enters the username and password$")
	public void user_enters_the_username_and_password() throws Throwable {
	    WebElement username = driver.findElement(By.name("txtUsername"));
	    WebElement password = driver.findElement(By.name("txtPassword"));
	    Utility.waitForWebElement(driver, username, 15).sendKeys("admin");
	    Utility.waitForWebElement(driver, password, 15).sendKeys("admin");
	}

	@When("^clicked on login button user should be logged in successfully$")
	public void clicked_on_login_button_user_should_be_logged_in_successfully() throws Throwable {
	    WebElement login = driver.findElement(By.name("Submit"));
	    Utility.waitForWebElement(driver, login, 15).click();
	}

	@Then("^close the browser and application$")
	public void close_the_browser_and_application() throws Throwable {
	    driver.quit();
	}

}
