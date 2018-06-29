package helper;

import java.io.File;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

public class Utility {
	public static String getCurrentDateTime() 
	{
		DateFormat dformat = new SimpleDateFormat("MM-dd-yyyy-HH-mm-ss");
		Date d= new Date();
		String date = dformat.format(d);
		return date;
	}
	
	public static String captureScreenShot(WebDriver driver) 
	{
		String screenshotpath = System.getProperty
				("user.dir")+"/Screenshots/"+Utility.getCurrentDateTime()+".png";
		File dest = new File(screenshotpath);
		TakesScreenshot ts=(TakesScreenshot)driver;
		File src = ts.getScreenshotAs(OutputType.FILE);
		
		try {
			FileHandler.copy(src, dest);
			//FileUtils.copyFile(src, dest);
		} catch (Exception e) {
			System.err.println("ERROR: Unable to capture screenshots "+e.getMessage());
		}
		
		return screenshotpath;
	}
	
	public static void highLightElement(WebDriver driver, WebElement element) {
		JavascriptExecutor js = (JavascriptExecutor) driver;

		js.executeScript("arguments[0].setAttribute('style', 'background: yellow; "
				+ "border: 2px solid red;');", element);
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
  
			System.out.println(e.getMessage());
		}

		js.executeScript("arguments[0].setAttribute('style','border: solid 2px white');",
				element);

	}
	public static void syncWebElement(WebDriver driver, WebElement element, int time) {

		WebDriverWait wait = new WebDriverWait(driver, time);
		wait.until(ExpectedConditions.visibilityOf(element));
		wait.until(ExpectedConditions.elementToBeClickable(element));

	}
	public static WebElement waitForWebElement(WebDriver driver, WebElement element, int time) {

		syncWebElement(driver, element, time);

		highLightElement(driver, element);

		return element;
	}
	
	public static void verifyBrokenLink(String linkurl) {
		URL url = null;
		HttpURLConnection connection = null;

		try {
			url = new URL(linkurl);
		} catch (Exception e) {
			System.out.println("URL is not configured");
		}

		try {
			connection = (HttpURLConnection) url.openConnection();

			connection.setConnectTimeout(5000);

			connection.connect();

		} catch (Exception e) {

		}

		int code = 0;
		try {
			code = connection.getResponseCode();
		} catch (Exception e) {

		}

		System.out.println("Response Code from Server is " + code);

		Assert.assertEquals(code, 200);
	}

	public static void verifyMultipleBrokenLink(WebDriver ldriver, String xpath) {

		SoftAssert soft = new SoftAssert();
		URL url = null;
		HttpURLConnection connection = null;

		List<WebElement> allLinks = ldriver.findElements(By.xpath(xpath));

		System.out.println("Total link to verify " + allLinks.size());

		for (WebElement ele : allLinks) {

			String hrefurl = ele.getAttribute("href");

			System.out.println("Testing link with this url " + hrefurl);

			try {
				url = new URL(hrefurl);
			} catch (Exception e) {
				System.out.println("URL is not configured");
			}

			try {
				connection = (HttpURLConnection) url.openConnection();

				connection.setConnectTimeout(5000);

				connection.connect();

			} catch (Exception e) {

			}

			int code = 0;
			try {
				code = connection.getResponseCode();
			} catch (Exception e) {

			}

			System.out.println("Response Code from Server is " + code);
			if (code!=200)
			{
				System.out.println("Broken link is: "+ hrefurl);
			}
			soft.assertEquals(code, 200);
			
		}

		soft.assertAll();

	}

	public static void verifyMultipleBrokenImages(WebDriver ldriver, String xpath) {

		SoftAssert soft = new SoftAssert();
		URL url = null;
		HttpURLConnection connection = null;

		List<WebElement> allLinks = ldriver.findElements(By.xpath(xpath));

		System.out.println("Total Images to verify " + allLinks.size());

		for (WebElement ele : allLinks) {

			String hrefurl = ele.getAttribute("src");

			System.out.println("Testing Images with this url " + hrefurl);

			try {
				url = new URL(hrefurl);
			} catch (Exception e) {
				System.out.println("URL is not configured");
			}

			try {
				connection = (HttpURLConnection) url.openConnection();

				connection.setConnectTimeout(5000);

				connection.connect();

			} catch (Exception e) {

			}

			int code = 0;
			try {
				code = connection.getResponseCode();

			} catch (Exception e) {

			}

			System.out.println("Response Code from Server is " + code);

			soft.assertEquals(code, 200);

		}

		soft.assertAll();

	}
}