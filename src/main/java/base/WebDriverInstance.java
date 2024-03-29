package base;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
// import org.openqa.selenium.edge.EdgeDriver;
// import org.openqa.selenium.firefox.FirefoxDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class WebDriverInstance {
	public static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
	
	public static WebDriver getDriver() throws InterruptedException {
		if(driver.get() == null) {
			try {
				driver.set(createDriver());
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
		return driver.get();
		
	}
	
	public static WebDriver createDriver() throws IOException, InterruptedException {
		WebDriver driver = null;
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		
		Properties prop = new Properties();
		FileInputStream data = new FileInputStream(
			System.getProperty("user.dir") + "\\src\\main\\java\\Resources\\config.properties");
		prop.load(data);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(6));
		driver.get(prop.getProperty("url")); // 2- also on BasePage-Hooks
			
			/* 
			if(prop.getProperty("browser").equals("chrome")) {
				System.setProperty("webdriver.chrome.driver",
				System.getProperty("user.dir")+"\\src\\main\\java\\drivers\\chromedriver.exe");
				driver = new ChromeDriver();
			} 
			
			else if (prop.getProperty("browser").equals("firefox")) {
				System.setProperty("webdriver.gecko.driver", 
				System.getProperty("user.dir")+"\\src\\main\\java\\drivers\\geckodriver.exe");
				driver = new FirefoxDriver();
		}
		
		else {
			System.setProperty("webdriver.edge.driver", 
			System.getProperty("user.dir")+"\\src\\main\\java\\drivers\\msedgedriver.exe");
			driver = new EdgeDriver();
		}
		
		*/
		
		return driver;
	}
	
	public static void cleanupDriver() throws InterruptedException {
		driver.get().quit();
		driver.remove();
	}
}
