import java.text.MessageFormat;
import java.util.Optional;

import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v114.network.Network;

import com.google.common.collect.ImmutableList;

public class BlockCssAndImagesNetworkRequestsTest {

	public static void main(String[] args) {
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

		// Request is paused listener for request and modify status
		devTools.send(Network.setBlockedURLs(ImmutableList.of("*.jpg", "*.png", "*.css")));

		System.out.println(MessageFormat.format("After Network.setBlockedURLs: {0}", devTools.hashCode()));

		// Get execution start time
		long startTime = System.currentTimeMillis();

		// Access web page
		chromeDriver.get("https://rahulshettyacademy.com/angularAppdemo/");

		// Click on Browse Products button
		chromeDriver.findElement(By.linkText("Browse Products")).click();

		// Click on Selenium link
		chromeDriver.findElement(By.linkText("Selenium")).click();

		// Click on Add to Cart button
		chromeDriver.findElement(By.cssSelector(".add-to-cart")).click();

		String paragraphText = chromeDriver.findElement(By.cssSelector("p")).getText();
		System.out.println(MessageFormat.format("Paragraph text: {0}", paragraphText));

		// Get execution end time
		long endTime = System.currentTimeMillis();
		System.out.println(MessageFormat.format("Total execution time: {0}ms", endTime - startTime));

		// Close session
		chromeDriver.close();
	}

}
