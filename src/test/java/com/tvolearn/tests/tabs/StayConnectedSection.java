package com.tvolearn.tests.tabs;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.asserts.SoftAssert;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class StayConnectedSection {
    private static final Logger logger = LogManager.getLogger(StayConnectedSection.class);

    private WebDriver driver;
    private WebDriverWait wait;
    private ExtentTest extentTest;
    private SoftAssert softAssert;

    public StayConnectedSection(WebDriver driver, WebDriverWait wait, ExtentTest extentTest) {
        this.driver = driver;
        this.wait = wait;
        this.extentTest = extentTest;
        this.softAssert = new SoftAssert();
    }

    public void navigateAndValidate() {
        scrollToStayConnectedSection();
        validateStayConnectedFunctionality();
        softAssert.assertAll(); // Assert all soft assertions at the end
    }

    private void scrollToStayConnectedSection() {
        logger.info("Scrolling to 'Stay Connected' section");
        extentTest.info("Scrolling to 'Stay Connected' section");

        try {
            WebElement stayConnectedHeading = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[contains(text(), 'Stay Connected')]")));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", stayConnectedHeading);
            logger.info("Scrolled to 'Stay Connected' section");
            extentTest.info("Scrolled to 'Stay Connected' section");
        } catch (Exception e) {
            logger.error("Error during scrolling to 'Stay Connected' section", e);
            extentTest.log(Status.FAIL, "Error during scrolling to 'Stay Connected' section - " + e.getMessage());
            softAssert.fail("Error during scrolling to 'Stay Connected' section - " + e.getMessage());
        }
    }

    private void validateStayConnectedFunctionality() {
        logger.info("Validating 'Stay Connected' section functionality");
        extentTest.info("Validating 'Stay Connected' section functionality");

        try {
            WebElement emailTextBox = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@type='email']")));
            WebElement subscribeButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("mc-embedded-subscribe")));

            // Enter an email address
            emailTextBox.sendKeys("sai@gmail.com");
            logger.info("Entered email address");
            extentTest.info("Entered email address");

            // Click the Subscribe button
            subscribeButton.click();
            logger.info("Clicked 'Subscribe' button");
            extentTest.info("Clicked 'Subscribe' button");

            // Validate the confirmation message
            WebElement confirmationMessage = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[contains(text(), 'Almost finished... We need to confirm your email address. To complete the subscription process, please click the link in the email we just sent you.')]")));
            softAssert.assertTrue(confirmationMessage.isDisplayed(), "Subscription confirmation message is not visible");
            logger.info("Subscription confirmation message is visible");
            extentTest.log(Status.PASS, "Subscription confirmation message is visible");
        } catch (Exception e) {
            logger.error("Error during validation of 'Stay Connected' section functionality", e);
            extentTest.log(Status.FAIL, "Error during validation of 'Stay Connected' section functionality - " + e.getMessage());
            softAssert.fail("Error during validation of 'Stay Connected' section functionality - " + e.getMessage());
        }
    }
}
