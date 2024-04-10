package datadrivenTesting;

import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

import org.apache.poi.EncryptedDocumentException;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import Pages.RedBusHome;
import io.github.bonigarcia.wdm.WebDriverManager;

public class PassData {
	WebDriver driver;
	DataExtract get;

	@BeforeTest(alwaysRun = true)
	public void setup() {
		WebDriverManager.chromedriver().setup();
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--disable-notifications");
		driver = new ChromeDriver(options);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.manage().window().maximize();
	}

	@DataProvider(name = "getdata")
	public Object[][] getdata() throws EncryptedDocumentException, IOException {
		DataExtract get = new DataExtract();
		Object[][] data = get.getdatafromexcel();
		return data;
	}

	@Test(dataProvider = "getdata")
	public void bustest(String from, String to, String date) throws InterruptedException, java.text.ParseException {
		driver.get("https://www.redbus.in/");

		RedBusHome hp = new RedBusHome(driver);

		if (from != null && !from.isEmpty()) {
			hp.getFrom().sendKeys(from);
		}

		if (to != null && !to.isEmpty()) {
			hp.getTo().sendKeys(to);
		}
		
		 

		if (date != null && !date.isEmpty()) {
			WebElement calendarInput = driver.findElement(By.id("onwardCal"));
			new WebDriverWait(driver, Duration.ofSeconds(10))
					.until(ExpectedConditions.elementToBeClickable(calendarInput));
			calendarInput.click();

			// Correctly parse the date and format it
			SimpleDateFormat inputFormat = new SimpleDateFormat("dd-MM-yyyy");
			SimpleDateFormat outputFormat = new SimpleDateFormat("dd MMM yyyy");
			java.util.Date parsedDate = inputFormat.parse(date);
			java.sql.Date sqlDate = new java.sql.Date(parsedDate.getTime());
			String formattedDate = outputFormat.format(sqlDate);

			// Use the formatted date in the JavaScript execution
			String script = String.format("document.querySelector('.labelCalendarContainer').innerText = '%s';",
					formattedDate);
			((JavascriptExecutor) driver).executeScript(script);
		}

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		WebElement searchButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("search_button")));
		searchButton.click();
		 
		 
	}
	 
	@AfterTest(alwaysRun = true)
	public void teardown() {
		driver.close();
	}
}
