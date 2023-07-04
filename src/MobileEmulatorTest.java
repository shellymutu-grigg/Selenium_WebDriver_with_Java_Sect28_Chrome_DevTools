import org.openqa.selenium.chrome.ChromeDriver;

public class MobileEmulatorTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		System.setProperty("webdriver.chromedriver",
				System.getProperty("user.home") + "//eclipse//drivers//chromedriver");
		ChromeDriver chromeDriver = new ChromeDriver();

	}

}
