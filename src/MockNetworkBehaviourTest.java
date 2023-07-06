import java.text.MessageFormat;
import java.util.Optional;

import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v114.fetch.Fetch;

public class MockNetworkBehaviourTest {

	public static void main(String[] args) {
		// Setup driver
		System.setProperty("webdriver.chromedriver",
				System.getProperty("user.home") + "//eclipse//drivers//chromedriver");

		ChromeDriver chromeDriver = new ChromeDriver();

		// Access DevTools
		DevTools devTools = chromeDriver.getDevTools();

		// Create session
		devTools.createSession();

		// Enable the fetch object
		devTools.send(Fetch.enable(Optional.empty(), Optional.empty()));

		System.out.println(MessageFormat.format("After Fetch.enable: {0}", devTools.hashCode()));

		// Request is paused listen for request and modify request url
		devTools.addListener(Fetch.requestPaused(), request -> {

			// --> CODE HANGS BEFORE THIS POINT
			System.out.println(
					MessageFormat.format("Inside Fetch.requestPaused(): {0}", request.getRequest().getMethod()));

			if (request.getRequest().getUrl().contains("=shetty")) {

				// Create mocked URL
				String mockedUrl = request.getRequest().getUrl().replace("=shetty", "=BadGuy");
				System.out.println(MessageFormat.format("Mocked URL: {0}", mockedUrl));

				// Inject mocked URL into network call
				devTools.send(Fetch.continueRequest(request.getRequestId(), Optional.of(mockedUrl), Optional.empty(),
						Optional.empty(), Optional.empty(), Optional.empty()));
			} else {

				// Use original URL into network call
				devTools.send(Fetch.continueRequest(request.getRequestId(), Optional.of(request.getRequest().getUrl()),
						Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty()));
			}

			// Access webpage
			chromeDriver.get("https://rahulshettyacademy.com/angularAppdemo/");

			// Click on library button
			chromeDriver.findElement(By.cssSelector("button[routerlink*='library']")).click();
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			System.out.println(MessageFormat.format("Paragraph text in libarary page: {0}",
					chromeDriver.findElement(By.cssSelector("p")).getText()));

			// Close session
			chromeDriver.close();
		});

	}

}
