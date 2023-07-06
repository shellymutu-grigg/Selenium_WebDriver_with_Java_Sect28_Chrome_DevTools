import java.text.MessageFormat;
import java.util.Optional;

import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v114.network.Network;
import org.openqa.selenium.devtools.v114.network.model.Request;
import org.openqa.selenium.devtools.v114.network.model.Response;

public class LogNetworkActivityTest {

	public static void main(String[] args) throws InterruptedException {
		// Setup webdriver
		ChromeDriver chromeDriver = new ChromeDriver();

		// Create access to devTools
		DevTools devTools = chromeDriver.getDevTools();

		// Create a devTool;s session
		devTools.createSession();

		// Enable ability to listen to network calls
		devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));

		// Listen for event trigger to fire
		devTools.addListener(Network.requestWillBeSent(), request -> {
			Request networkRequest = request.getRequest();
			System.out.println(MessageFormat.format("Network request url: {0}", networkRequest.getUrl()));
			System.out.println(MessageFormat.format("Network request headers: {0}", networkRequest.getHeaders()));
		});

		// Listen for event response to fire
		devTools.addListener(Network.responseReceived(), response -> {
			Response networkResponse = response.getResponse();
			if (networkResponse.getStatus().toString().startsWith("4")) {
				System.out.println(MessageFormat.format("Network failure response url: {0}", networkResponse.getUrl()));
				System.out.println(
						MessageFormat.format("Network failure response status: {0}", networkResponse.getStatus()));
			}
		});

		// Access webpage
		chromeDriver.get("https://rahulshettyacademy.com/angularAppdemo/");

		// Click on hamburger menu
		chromeDriver.findElement(By.cssSelector("button[routerlink*='library']")).click();
		Thread.sleep(3000);

		// Close session
		chromeDriver.close();

	}

}
