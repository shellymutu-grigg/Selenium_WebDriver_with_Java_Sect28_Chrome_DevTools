import java.text.MessageFormat;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;

public class CaptureConsoleLogsTest {

	public static void main(String[] args) {
		// Setup chrome driver
		ChromeDriver chromeDriver = new ChromeDriver();

		// Access web page
		chromeDriver.get("https://rahulshettyacademy.com/angularAppdemo/");

		// Click on browse Products button, selenium product and Add to cart
		chromeDriver.findElement(By.linkText("Browse Products")).click();
		chromeDriver.findElement(By.partialLinkText("Selenium")).click();
		chromeDriver.findElement(By.cssSelector(".add-to-cart")).click();

		// Navigate to cart
		chromeDriver.findElement(By.linkText("Cart")).click();

		// Clear the selenium quantity field and add 2
		chromeDriver.findElement(By.id("exampleInputEmail1")).clear();
		chromeDriver.findElement(By.id("exampleInputEmail1")).sendKeys("2");

		// Retrieve log entries
		LogEntries logEntries = chromeDriver.manage().logs().get(LogType.BROWSER);

		List<LogEntry> logs = logEntries.getAll();

		// Iterate through log entries
		for (LogEntry entry : logs) {
			System.out.println(MessageFormat.format("Log entry: {0}", entry.getMessage()));
		}

		// Close session
		chromeDriver.close();

	}

}
