import java.text.MessageFormat;
import java.util.HashMap;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;

public class SetGeoLocationTest {

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
		HashMap<String, Object> geoPointsMap = new HashMap<String, Object>();
		geoPointsMap.put("latitude", 40);
		geoPointsMap.put("longitude", -3);
		geoPointsMap.put("accuracy", 1);

		// See documentation https://chromedevtools.github.io/devtools-protocol/
		// Send the commands to Chrome DevTools Protocol (CDP) by sending directly to
		// CDP not via
		// existing wrapper method
		chromeDriver.executeCdpCommand("Emulation.setGeolocationOverride", geoPointsMap);

		// Access web page
		chromeDriver.get("https://www.google.com");

		// Search for netflix results
		chromeDriver.findElement(By.name("q")).sendKeys("netflix", Keys.ENTER);
		Thread.sleep(3000);

		// Click on first result link
		chromeDriver.findElements(By.cssSelector(".LC20lb")).get(0).click();

		System.out.println(MessageFormat.format("Netflix main header text: {0}",
				chromeDriver.findElement(By.cssSelector(".default-ltr-cache-19f4kxn")).getText()));

		// Close session
		chromeDriver.close();

	}

}
