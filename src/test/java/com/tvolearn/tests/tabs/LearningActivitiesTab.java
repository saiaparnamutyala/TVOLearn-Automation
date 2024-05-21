package com.tvolearn.tests.tabs;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.asserts.SoftAssert;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LearningActivitiesTab {
    private static final Logger logger = LogManager.getLogger(LearningActivitiesTab.class);

    private WebDriver driver;
    private WebDriverWait wait;
    private ExtentTest extentTest;
    private SoftAssert softAssert;

    public LearningActivitiesTab(WebDriver driver, WebDriverWait wait, ExtentTest extentTest) {
        this.driver = driver;
        this.wait = wait;
        this.extentTest = extentTest;
        this.softAssert = new SoftAssert();
    }

    public void navigateAndValidate() {
        validateTabNavigation("Learning Activities", "https://tvolearn.com/pages/grade-2-mathematics");
        validateLearningActivities();
        try {
            softAssert.assertAll(); // Assert all soft assertions at the end
        } catch (AssertionError e) {
            logger.error("Validation failed", e);
            extentTest.fail("Validation failed - " + e.getMessage());
        }
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
            logger.info("Navigated to Learning Activities Content");
            extentTest.info("Navigated to Learning Activities Content");
        } catch (Exception e) {
            logger.error("Error during validation of tab navigation for: " + tabText, e);
            extentTest.fail("Error during validation of tab navigation for: " + tabText + " - " + e.getMessage());
            softAssert.fail("Error during validation of tab navigation for: " + tabText + " - " + e.getMessage());
        }
    }

    private void validateLearningActivities() {
        logger.info("Validating Learning Activities sub-tab: Number");
        extentTest.info("Validating Learning Activities sub-tab: Number");
        validateSubTabContent("Number");

        // Click and validate remaining subtabs
        String[] subTabs = {"Algebra", "Data", "Spatial Sense", "Financial Literacy"};
        for (String subTab : subTabs) {
            logger.info("Validating Learning Activities sub-tab: " + subTab);
            extentTest.info("Validating Learning Activities sub-tab: " + subTab);
            validateSubTabActivities(subTab);
        }

        // After validating all subtabs, navigate back to "On this page" section
        logger.info("Navigating back to 'On this page' section");
        extentTest.info("Navigating back to 'On this page' section");
        WebElement onThisPageSection = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[contains(text(), 'On this page:')]")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", onThisPageSection);
    }

    private void validateSubTabContent(String subTab) {
        // Ensure the sub-tab content is visible
        try {
            WebElement content = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[contains(text(), '" + subTab + "')]")));
            softAssert.assertTrue(content.isDisplayed(), subTab + " content is not visible");
            logger.info(subTab + " content is visible");
            extentTest.info(subTab + " content is visible");
        } catch (Exception e) {
            logger.error(subTab + " content is not visible", e);
            extentTest.fail(subTab + " content is not visible - " + e.getMessage());
            softAssert.fail(subTab + " content is not visible - " + e.getMessage());
        }
    }

    private void validateSubTabActivities(String subTab) {
        logger.info("Entered into function validateSubTabActivities with subTab: " + subTab);
        extentTest.info("Entered into function validateSubTabActivities with subTab: " + subTab);

        try {
            // Wait for the sub-tab to be visible and clickable
            WebElement tab = wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText(subTab)));
            logger.info("Located subTab: " + subTab);
            extentTest.info("Located subTab: " + subTab);
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", tab);
            wait.until(ExpectedConditions.elementToBeClickable(tab)).click();
            logger.info("Clicked subTab: " + subTab);
            extentTest.info("Clicked subTab: " + subTab);

            // Validate the content under the clicked sub-tab
            validateSubTabContent(subTab);

            // Navigate back to the main "Learning Activities" section
            driver.navigate().back();
            wait.until(ExpectedConditions.presenceOfElementLocated(By.linkText("Learning Activities")));
            logger.info("Navigated back to Learning Activities section");
            extentTest.info("Navigated back to Learning Activities section");
        } catch (Exception e) {
            logger.error("Error during validation of subTab: " + subTab, e);
            extentTest.fail("Error during validation of subTab: " + subTab + " - " + e.getMessage());
            softAssert.fail("Error during validation of subTab: " + subTab + " - " + e.getMessage());
        }
    }
}
