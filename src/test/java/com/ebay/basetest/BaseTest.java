package com.ebay.basetest;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import java.io.File;
import java.io.FileInputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

public class BaseTest {

	public Properties prop;
	WebDriver driver;
	public SoftAssert softAssert = new SoftAssert();

	public void launchApp() {
		// init the prop file
		if (prop == null) {
			prop = new Properties();
			System.out.println(System.getProperty("user.dir"));
			String path = System.getProperty("user.dir") + "//src//test//resources//project.properties";
			System.out.println(path);
			try {
				FileInputStream fs = new FileInputStream(path);
				prop.load(fs);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		File app = new File(prop.getProperty("apkFilePath"));
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability("deviceName", prop.getProperty("deviceName"));
		capabilities.setCapability("platformName", prop.getProperty("platformName"));
		capabilities.setCapability("platformVersion", prop.getProperty("platformVersion"));
		capabilities.setCapability("app", app.getAbsolutePath());
		capabilities.setCapability("udid", prop.getProperty("appudid"));
		capabilities.setCapability("noReset", true);
		capabilities.setCapability("fullReset", false);

		try {
			driver = new AndroidDriver<AndroidElement>(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

	public void quitApp() {
		driver.quit();
	}

	public void click(String locatorKey) {
		getAndroidElement(locatorKey).click();
	}

	public void type(String locatorKey, String data) {
		getAndroidElement(locatorKey).sendKeys(data);
	}
	
	public void setOrientationLandScape() {
		((AppiumDriver<AndroidElement>) driver).rotate(ScreenOrientation.LANDSCAPE);
	}
	
	public void getOrientationLandScape() {
		 System.out.println("Current screen orientation Is : " + ((AppiumDriver<AndroidElement>) driver).getOrientation());
	}

	public void swipe() {
		Dimension size = driver.manage().window().getSize();
		System.out.println(size);
		int startx = (int) (size.width * 0.70);
		int endx = (int) (size.width * 0.30);
		int starty = size.height / 2;
		System.out.println("startx = " + startx + " ,endx = " + endx + " , starty = " + starty);
		// Swipe from Right to Left.
		((AndroidDriver) driver).swipe(startx, starty, endx, starty, 3000);
		// Swipe from Left to Right.
		((AndroidDriver) driver).swipe(endx, starty, startx, starty, 3000);

	}

	public void scroll(String text) {
		MobileElement element = (MobileElement) driver.findElement(MobileBy.AndroidUIAutomator(
				"new UiScrollable(new UiSelector().resourceId(\"com.android.vending:id/tab_recycler_view\")).getChildByText("
						+ "new UiSelector().className(\"android.widget.TextView\"), \"Games We Are Playing\")"));
		element.click();

	}

	public AndroidElement getAndroidElement(String locatorKey) {
		// extract AndroidElement
		AndroidDriver<AndroidElement> aDriver = (AndroidDriver<AndroidElement>) driver;

		AndroidElement e = null;
		try {
			if (locatorKey.endsWith("_id"))
				e = aDriver.findElement(By.id(prop.getProperty(locatorKey)));
			else if (locatorKey.endsWith("_xpath"))
				e = aDriver.findElement(By.xpath(prop.getProperty(locatorKey)));
			else if (locatorKey.endsWith("_name"))
				e = aDriver.findElement(By.name(prop.getProperty(locatorKey)));
		} catch (Exception ex) {
			// screenshot
			Assert.fail(ex.getMessage());
		}

		return e;
	}

	/*********************** validation functions *****************************/

	public boolean isElementPresent(String locatorKey) {
		return false;
	}

	public boolean verifyText(String locatorKey, String expectedText) {
		return false;
	}

}
