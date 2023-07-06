import java.text.MessageFormat;
import java.util.Optional;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v114.network.Network;
import org.openqa.selenium.devtools.v114.network.model.ConnectionType;

public class ReduceNetworkSpeedTest {

	public static void main(String[] args) throws InterruptedException {

		// Setup driver
		ChromeOptions chromeOptions = new ChromeOptions();
		ChromeDriver chromeDriver = new ChromeDriver(chromeOptions);

		// Access DevTools
		DevTools devTools = ((ChromeDriver) chromeDriver).getDevTools();

		// Create session
		devTools.createSession();

		// Enable the fetch object
		devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));

		System.out.println(MessageFormat.format("After Network.enable: {0}", devTools.hashCode()));

		// emulateNetworkConditions(offline, latency(ms), downloadThroughput(bytes),
		// uploadThroughput(bytes), connectionType));
		devTools.send(
				Network.emulateNetworkConditions(false, 3000, 20000, 100000, Optional.of(ConnectionType.ETHERNET)));

		System.out.println(MessageFormat.format("After Network.emulateNetworkConditions: {0}", devTools.hashCode()));

		// Get execution start time
		long startTime = System.currentTimeMillis();

		// Access web page
		// chromeDriver.get("https://rahulshettyacademy.com/angularAppdemo/");

		// Click on library button
		// chromeDriver.findElement(By.cssSelector("button[routerlink*='library']")).click();

		// Took 15,000ms WITH latency

		// Access webpage
		chromeDriver.get("https://www.google.com");

		// Click on hamburger menu
		chromeDriver.findElement(By.name("q")).sendKeys("netflix", Keys.ENTER);
		Thread.sleep(3000);

		// Click on first result link
		chromeDriver.findElements(By.cssSelector(".LC20lb")).get(0).click();

		System.out.println(MessageFormat.format("Netflix main header text: {0}",
				chromeDriver.findElement(By.cssSelector(".default-ltr-cache-19f4kxn")).getText()));

		// Get execution end time
		long endTime = System.currentTimeMillis();
		System.out.println(MessageFormat.format("Total execution time: {0}ms", endTime - startTime));

		// Took 293,524ms WITH latency
		// Took WITHOUT latency

		// Close session
		chromeDriver.close();

	}

}
