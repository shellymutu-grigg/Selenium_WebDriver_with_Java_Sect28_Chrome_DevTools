import java.net.URI;

import org.openqa.selenium.HasAuthentication;
import org.openqa.selenium.UsernameAndPassword;
import org.openqa.selenium.chrome.ChromeDriver;

import com.google.common.base.Predicate;

public class BasicWindowAuthentication {

	public static void main(String[] args) {
		// Setup chrome driver
		ChromeDriver chromeDriver = new ChromeDriver();

		// Predicate condition
		Predicate<URI> uriPredicate = uri -> uri.getHost().contains("httpbin.org");

		// (HasAuthentication)chromeDriver).register(predicate, UsernameAndPassword)
		((HasAuthentication) chromeDriver).register(uriPredicate, UsernameAndPassword.of("foo", "bar"));
		chromeDriver.get("http://httpbin.org/basic-auth/foo/bar");

		// Close session
		chromeDriver.close();
	}

}
