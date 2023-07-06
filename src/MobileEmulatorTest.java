import java.util.Optional;

import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v112.emulation.Emulation;

public class MobileEmulatorTest {

	public static void main(String[] args) throws InterruptedException {
		// Setup chrome driver
		System.setProperty("webdriver.chromedriver",
				System.getProperty("user.home") + "//eclipse//drivers//chromedriver");
		ChromeDriver chromeDriver = new ChromeDriver();

		// Setup firefox driver
		// System.setProperty("webdriver.gecko.driver",
		// System.getProperty("user.home") + "//eclipse//drivers//chromedriver");
		// FirefoxDriver firefoxDriver = new FirefoxDriver();

		// Create an object to to access DevTools
		DevTools devTools = chromeDriver.getDevTools();

		// Create a session
		devTools.createSession();

		// See documentation https://chromedevtools.github.io/devtools-protocol/
		// Send the commands to Chrome DevTools Protocol (CDP)
		devTools.send(Emulation.setDeviceMetricsOverride(600, 1000, 50, true, Optional.empty(), Optional.empty(),
				Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(),
				Optional.empty(), Optional.empty()));

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
