package com.tvolearn.tests.tabs;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.asserts.SoftAssert;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DifferentSubjectSection {
    private static final Logger logger = LogManager.getLogger(DifferentSubjectSection.class);

    private WebDriver driver;
    private WebDriverWait wait;
    private ExtentTest extentTest;
    private SoftAssert softAssert;

    public DifferentSubjectSection(WebDriver driver, WebDriverWait wait, ExtentTest extentTest) {
        this.driver = driver;
        this.wait = wait;
        this.extentTest = extentTest;
        this.softAssert = new SoftAssert();
    }

    public void navigateAndValidate() {
        String[] subjects = {"Science & Technology", "Language", "Social Studies", "The Arts"};
        String[] urls = {
            "https://tvolearn.com/pages/grade-2-science-and-technology",
            "https://tvolearn.com/pages/grade-2-language",
            "https://tvolearn.com/pages/grade-2-social-studies",
            "https://tvolearn.com/pages/grade-2-the-arts"
        };

        try {
            // Ensure "Looking for a Different Subject?" heading is visible
            WebElement differentSubjectHeading = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[contains(text(), 'Looking for a Different Subject?')]")));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", differentSubjectHeading);

            for (int i = 0; i < subjects.length; i++) {
                validateSubjectLink(subjects[i], urls[i]);
                validateViewResourcesButton(subjects[i], urls[i]);
            }
        } catch (Exception e) {
            logger.error("Error during navigation and validation", e);
            extentTest.log(Status.FAIL, "Error during navigation and validation - " + e.getMessage());
        } finally {
            try {
                softAssert.assertAll();
            } catch (AssertionError e) {
                logger.error("Assertion errors during validation", e);
                extentTest.log(Status.FAIL, "Assertion errors during validation - " + e.getMessage());
            }
        }
    }

    private void validateSubjectLink(String subject, String expectedUrl) {
        try {
            WebElement subjectLink = wait.until(ExpectedConditions.elementToBeClickable(By.linkText(subject)));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", subjectLink);
            subjectLink = wait.until(ExpectedConditions.elementToBeClickable(By.linkText(subject)));

            logger.info("Located subject link: " + subject);
            extentTest.info("Located subject link: " + subject);
            subjectLink.click();
            wait.until(ExpectedConditions.urlToBe(expectedUrl));
            softAssert.assertEquals(driver.getCurrentUrl(), expectedUrl, subject + " link did not navigate to the expected content");
            logger.info(subject + " link navigated to expected URL: " + expectedUrl);
            extentTest.log(Status.PASS, subject + " link navigated to expected URL: " + expectedUrl);

            driver.navigate().back();
            wait.until(ExpectedConditions.urlToBe("https://tvolearn.com/pages/grade-2-mathematics"));

            // Scroll to "Looking for a Different Subject?" heading again
            WebElement differentSubjectHeading = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[contains(text(), 'Looking for a Different Subject?')]")));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", differentSubjectHeading);

        } catch (Exception e) {
            logger.error("Error during validation of subject link: " + subject, e);
            extentTest.log(Status.FAIL, "Error during validation of subject link: " + subject + " - " + e.getMessage());
            softAssert.fail("Error during validation of subject link: " + subject + " - " + e.getMessage());
        }
    }

    private void validateViewResourcesButton(String subject, String expectedUrl) {
        try {
            WebElement viewResourcesButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[text()='" + subject + "']/following-sibling::a[text()='View Resources']")));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", viewResourcesButton);
            viewResourcesButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[text()='" + subject + "']/following-sibling::a[text()='View Resources']")));

            logger.info("Located 'View Resources' button for: " + subject);
            extentTest.info("Located 'View Resources' button for: " + subject);
            viewResourcesButton.click();
            wait.until(ExpectedConditions.urlToBe(expectedUrl));
            softAssert.assertEquals(driver.getCurrentUrl(), expectedUrl, "'View Resources' button for " + subject + " did not navigate to the expected content");
            logger.info("'View Resources' button for " + subject + " navigated to expected URL: " + expectedUrl);
            extentTest.log(Status.PASS, "'View Resources' button for " + subject + " navigated to expected URL: " + expectedUrl);

            driver.navigate().back();
            wait.until(ExpectedConditions.urlToBe("https://tvolearn.com/pages/grade-2-mathematics"));

            // Scroll to "Looking for a Different Subject?" heading again
            WebElement differentSectionHeading = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[contains(text(), 'Looking for a Different Subject?')]")));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", differentSectionHeading);

        } catch (Exception e) {
            logger.error("Error during validation of 'View Resources' button for: " + subject, e);
            extentTest.log(Status.FAIL, "Error during validation of 'View Resources' button for: " + subject + " - " + e.getMessage());
            softAssert.fail("Error during validation of 'View Resources' button for: " + subject + " - " + e.getMessage());
        }
    }
}
