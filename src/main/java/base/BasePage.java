package base;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BasePage {
	private String url;
	private Properties prop;
	public static String scrnShtDestPath;
	
	public BasePage() throws IOException {
		prop = new Properties();
		FileInputStream data = new FileInputStream(
				System.getProperty("user.dir") + "\\src\\main\\java\\Resources\\config.properties");
		prop.load(data);
	}
	
	public static WebDriver getDriver() throws InterruptedException, IOException {
		return WebDriverInstance.getDriver();
	}

	public String getUrl() throws IOException {
		url = prop.getProperty("url");
		return url;
	}
	
	public static String takeSnapshot(String name) throws IOException, WebDriverException, InterruptedException {
		File srcFile = ((TakesScreenshot)getDriver()).getScreenshotAs(OutputType.FILE);
		String destFile = System.getProperty("user.dir")+ "\\target\\screenshots"+timestamp()+".png";
		scrnShtDestPath = destFile;
		
		try {
			FileUtils.copyFile(srcFile, new File(destFile));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return name;
		
	}
	
	public static String timestamp() {
		return new SimpleDateFormat("dd-MM-yyyy HH-mm-ss").format(new Date());
	}
	
	public static String getScrnShtDestPath() {
		return scrnShtDestPath;
	}
	
	
	public static void waitForElement(WebElement element, Duration timer) throws InterruptedException, IOException {
		WebDriverWait wait = new WebDriverWait(getDriver(), timer);
		wait.until(ExpectedConditions.visibilityOf(element));
	}
	
	public static void waitForClick(WebElement element, Duration timer) throws InterruptedException, IOException {
		WebDriverWait wait = new WebDriverWait(getDriver(), timer);
		wait.until(ExpectedConditions.elementToBeClickable(element));
	}
	
	public static void waitForInvisibility(WebElement element, Duration timer) throws InterruptedException, IOException {
		WebDriverWait wait = new WebDriverWait(getDriver(), timer);
		wait.until(ExpectedConditions.invisibilityOf(element));
	}
	
}
