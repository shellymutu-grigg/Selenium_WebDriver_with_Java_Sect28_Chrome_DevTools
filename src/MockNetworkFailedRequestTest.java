import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v114.fetch.Fetch;
import org.openqa.selenium.devtools.v114.fetch.model.RequestPattern;
import org.openqa.selenium.devtools.v114.network.model.ErrorReason;

public class MockNetworkFailedRequestTest {

	public static void main(String[] args) throws InterruptedException {
		// Setup driver
		ChromeOptions chromeOptions = new ChromeOptions();
		ChromeDriver chromeDriver = new ChromeDriver(chromeOptions);

		// Access DevTools
		DevTools devTools = ((ChromeDriver) chromeDriver).getDevTools();

		// Create session
		devTools.createSession();

		Optional<List<RequestPattern>> patterns = Optional
				.of(Arrays.asList(new RequestPattern(Optional.of("*GetBook*"), Optional.empty(), Optional.empty())));

		// Enable the fetch object
		devTools.send(Fetch.enable(patterns, Optional.empty()));

		System.out.println(MessageFormat.format("After Fetch.enable: {0}", devTools.hashCode()));

		// Request is paused listener for request and modify status
		devTools.addListener(Fetch.requestPaused(), request -> {
			System.out.println(MessageFormat.format("Inside Fetch.requestPaused(): {0}", devTools.hashCode()));

			devTools.send(Fetch.failRequest(request.getRequestId(), ErrorReason.FAILED));
		});

		// Access web page
		chromeDriver.get("https://rahulshettyacademy.com/angularAppdemo/");

		// Click on library button
		chromeDriver.findElement(By.cssSelector("button[routerlink*='library']")).click();

		Thread.sleep(3000);

		// Determine if any text is displayed on page
		String paragraphMessageString = "";
		List<WebElement> size = chromeDriver.findElements(By.cssSelector("p"));
		if (size.isEmpty()) {
			paragraphMessageString = "Houston we have success! No text displayed!";
		}
		System.out.println(MessageFormat.format("Paragraph text in libarary page: {0}", paragraphMessageString));

		// Close session
		chromeDriver.close();
	}
}
