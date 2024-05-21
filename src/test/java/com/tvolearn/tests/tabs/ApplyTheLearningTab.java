package com.tvolearn.tests.tabs;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.asserts.SoftAssert;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ApplyTheLearningTab {
    private static final Logger logger = LogManager.getLogger(ApplyTheLearningTab.class);

    private WebDriver driver;
    private WebDriverWait wait;
    private ExtentTest extentTest;
    private SoftAssert softAssert;

    public ApplyTheLearningTab(WebDriver driver, WebDriverWait wait, ExtentTest extentTest) {
        this.driver = driver;
        this.wait = wait;
        this.extentTest = extentTest;
        this.softAssert = new SoftAssert();
    }

    public void navigateAndValidate() {
        validateTabNavigation("Apply the Learning", "https://tvolearn.com/pages/grade-2-mathematics");
        
        // Assert all soft assertions at the end
        softAssert.assertAll();
    }

    private void validateTabNavigation(String tabText, String expectedUrl) {
        logger.info("Validating tab navigation for: " + tabText);
        extentTest.info("Validating tab navigation for: " + tabText);

        try {
            WebElement tab = wait.until(ExpectedConditions.elementToBeClickable(By.linkText(tabText)));
            logger.info("Located the tab: " + tabText);
            extentTest.info("Located the tab: " + tabText);
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", tab);

            try {
                tab.click();
                logger.info("Clicked tab: " + tabText);
                extentTest.info("Clicked tab: " + tabText);
            } catch (ElementClickInterceptedException e) {
                logger.warn("ElementClickInterceptedException caught. Using JavaScript to click the tab: " + tabText);
                extentTest.log(Status.WARNING, "ElementClickInterceptedException caught. Using JavaScript to click the tab: " + tabText);
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", tab);
            }

            wait.until(ExpectedConditions.urlToBe(expectedUrl));
            softAssert.assertEquals(driver.getCurrentUrl(), expectedUrl, tabText + " tab did not navigate to the expected content");
            logger.info("Navigated to Apply the Learning Content");
            extentTest.info("Navigated to Apply the Learning Content");
        } catch (Exception e) {
            logger.error("Error during validation of tab navigation for: " + tabText, e);
            extentTest.fail("Error during validation of tab navigation for: " + tabText + " - " + e.getMessage());
            softAssert.fail("Error during validation of tab navigation for: " + tabText + " - " + e.getMessage());
        }
    }
}
