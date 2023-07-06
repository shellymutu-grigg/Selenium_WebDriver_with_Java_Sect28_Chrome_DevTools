import java.util.HashMap;

import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;

public class CDPCommandsTest {

	public static void main(String[] args) throws InterruptedException {
		// Setup chrome driver
		System.setProperty("webdriver.chromedriver",
				System.getProperty("user.home") + "//eclipse//drivers//chromedriver");
		ChromeDriver chromeDriver = new ChromeDriver();

		// Create an object to to access DevTools
		DevTools devTools = chromeDriver.getDevTools();

		// Create a session
		devTools.createSession();

		// Create a HashMap for the parameters
		HashMap<String, Object> deviceMetricsMap = new HashMap<String, Object>();
		deviceMetricsMap.put("width", 600);
		deviceMetricsMap.put("height", 1000);
		deviceMetricsMap.put("deviceScaleFactor", 50);
		deviceMetricsMap.put("mobile", true);

		// See documentation https://chromedevtools.github.io/devtools-protocol/
		// Send the commands to Chrome DevTools Protocol (CDP) by sending directly to
		// CDP not via
		// existing wrapper method
		chromeDriver.executeCdpCommand("Emulation.setDeviceMetricsOverride", deviceMetricsMap);

		// Access webpage
		chromeDriver.get("https://rahulshettyacademy.com/angularAppdemo/");

		// Click on hamburger menu
		chromeDriver.findElement(By.cssSelector(".navbar-toggler")).click();
		Thread.sleep(3000);

		// Click on menu link
		chromeDriver.findElement(By.linkText("Library")).click();

		// Close session
		chromeDriver.close();

	}

}
