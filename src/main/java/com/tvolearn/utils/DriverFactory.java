package com.tvolearn.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import io.github.bonigarcia.wdm.WebDriverManager;

public class DriverFactory {
	
	private static final Logger logger = LogManager.getLogger(DriverFactory.class);

    private static WebDriver driver;

    public static WebDriver getDriver() {
        if (driver == null) {
        	
        	 logger.info("Setting up ChromeDriver");
             System.setProperty("webdriver.chrome.driver", "drivers/chromedriver-mac-arm64/chromedriver");
             String driverVersion = WebDriverManager.chromedriver().getDownloadedDriverVersion();
             logger.info("Using ChromeDriver version: " + driverVersion);
             ChromeOptions options = new ChromeOptions();
             options.addArguments("--remote-allow-origins=*");
             options.addArguments("--headless");
             options.addArguments("--disable-gpu"); // Disable GPU hardware acceleration
             options.addArguments("--window-size=1920,1080"); // Set a specific window size
             driver = new ChromeDriver(options);
     
        }
        return driver;
    }

    public static void quitDriver() {
        if (driver != null) {
        	logger.info("Quitting ChromeDriver");
            driver.quit();
            driver = null;
        }
    }
}
