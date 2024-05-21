package com.tvolearn.tests;

import com.tvolearn.tests.tabs.*;
import com.tvolearn.utils.DriverFactory;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.Duration;

public class TVOLearnTest {
    private static final Logger logger = LogManager.getLogger(TVOLearnTest.class);

    private WebDriver driver;
    private ExtentReports extentReports;
    private ExtentTest extentTest;
    private WebDriverWait wait;

    @BeforeSuite
    public void setUp() {
        logger.info("Setting up ExtentReports");
        ExtentSparkReporter sparkReporter = new ExtentSparkReporter("extentReport.html");
        extentReports = new ExtentReports();
        extentReports.attachReporter(sparkReporter);
    }

    @BeforeMethod
    public void beforeMethod() {
        logger.info("Initializing WebDriver");
        driver = DriverFactory.getDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        driver.get("https://tvolearn.com/");
    }

    @Test
    public void testLearningResources() {
        extentTest = extentReports.createTest("testLearningResources");

        try {
            logger.info("Navigating to 'Learning Resources (K-12)' dropdown");
            extentTest.info("Navigating to 'Learning Resources (K-12)' dropdown");
            WebElement learningResourcesDropdown = wait.until(ExpectedConditions.elementToBeClickable(By.className("site-nav__label")));
            learningResourcesDropdown.click();

            logger.info("Choosing Grade 2");
            extentTest.info("Choosing Grade 2");
            WebElement gradeLevel = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Grade 2")));
            gradeLevel.click();

            logger.info("Scrolling to 'Learn Forward in the Curriculum'");
            extentTest.info("Scrolling to 'Learn Forward in the Curriculum'");
            WebElement targetElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[contains(text(), 'Learn Forward in the Curriculum')]")));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", targetElement);

            logger.info("Clicking on the 'Mathematics' tab");
            extentTest.info("Clicking on the 'Mathematics' tab");
            WebElement mathematicsTab = wait.until(ExpectedConditions.elementToBeClickable(By.className("button-subject-name")));
            mathematicsTab.click();

            logger.info("Clicking the 'helpful tips' link");
            extentTest.info("Clicking the 'helpful tips' link");
            WebElement helpfulTipsLink = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("helpful tips")));
            helpfulTipsLink.click();

            logger.info("Waiting until the 'helpful tips' page is loaded");
            extentTest.info("Waiting until the 'helpful tips' page is loaded");
            wait.until(ExpectedConditions.urlToBe("https://tvolearn.com/pages/helpful-tips"));
            driver.navigate().back();
            wait.until(ExpectedConditions.urlToBe("https://tvolearn.com/pages/grade-2-mathematics"));

            logger.info("Clicking the 'View Curriculum' link");
            extentTest.info("Clicking the 'View Curriculum' link");
            WebElement viewCurriculumLink = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("View Curriculum")));
            viewCurriculumLink.click();

            logger.info("Switching to the new tab for 'View Curriculum'");
            extentTest.info("Switching to the new tab for 'View Curriculum'");
            String originalHandle = driver.getWindowHandle();
            for (String handle : driver.getWindowHandles()) {
                if (!handle.equals(originalHandle)) {
                    driver.switchTo().window(handle);
                    break;
                }
            }

            logger.info("Waiting for the 'View Curriculum' tab to load");
            extentTest.info("Waiting for the 'View Curriculum' tab to load");
            wait.until(ExpectedConditions.urlToBe("https://www.dcp.edu.gov.on.ca/en/curriculum/elementary-mathematics"));

            logger.info("Closing the 'View Curriculum' tab and switching back to the original tab");
            extentTest.info("Closing the 'View Curriculum' tab and switching back to the original tab");
            driver.close();
            driver.switchTo().window(originalHandle);

            wait.until(ExpectedConditions.urlToBe("https://tvolearn.com/pages/grade-2-mathematics"));

            logger.info("Clicking the 'idello.org' link");
            extentTest.info("Clicking the 'idello.org' link");
            WebElement idelloLink = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("idello.org")));
            idelloLink.click();

            logger.info("Switching to the new tab for 'idello.org'");
            extentTest.info("Switching to the new tab for 'idello.org'");
            for (String handle : driver.getWindowHandles()) {
                if (!handle.equals(originalHandle)) {
                    driver.switchTo().window(handle);
                    break;
                }
            }

            logger.info("Waiting for the 'idello.org' tab to load");
            extentTest.info("Waiting for the 'idello.org' tab to load");
            wait.until(ExpectedConditions.urlToBe("https://apprendre.tfo.org/"));

            logger.info("Closing the 'idello.org' tab and switching back to the original tab");
            extentTest.info("Closing the 'idello.org' tab and switching back to the original tab");
            driver.close();
            driver.switchTo().window(originalHandle);

            wait.until(ExpectedConditions.urlToBe("https://tvolearn.com/pages/grade-2-mathematics"));

            logger.info("Scrolling to 'On this page:'");
            extentTest.info("Scrolling to 'On this page:'");
            WebElement onThisPageSection = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[contains(text(), 'On this page:')]")));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", onThisPageSection);

            // Validate the tabs
            LearningActivitiesTab learningActivitiesTab = new LearningActivitiesTab(driver, wait, extentTest);
            learningActivitiesTab.navigateAndValidate();

            ResourcesForLearningTab resourcesForLearningTab = new ResourcesForLearningTab(driver, wait, extentTest);
            resourcesForLearningTab.navigateAndValidate();

            ApplyTheLearningTab applyTheLearningTab = new ApplyTheLearningTab(driver, wait, extentTest);
            applyTheLearningTab.navigateAndValidate();

            VocabularyTab vocabularyTab = new VocabularyTab(driver, wait, extentTest);
            vocabularyTab.navigateAndValidate();

            DifferentSubjectSection differentSubjectSection = new DifferentSubjectSection(driver, wait, extentTest);
            differentSubjectSection.navigateAndValidate();

            // Validate the "Stay Connected" section
            StayConnectedSection stayConnectedSection = new StayConnectedSection(driver, wait, extentTest);
            stayConnectedSection.navigateAndValidate();

            extentTest.pass("Test Passed");
        } catch (Exception e) {
            extentTest.fail("Test Failed - " + e.getMessage());
            throw e;
        }
    }

    @AfterMethod
    public void afterMethod() {
        logger.info("Quitting WebDriver");
        DriverFactory.quitDriver();
    }

    @AfterSuite
    public void tearDown() {
        logger.info("Flushing ExtentReports");
        extentReports.flush();
    }
}
