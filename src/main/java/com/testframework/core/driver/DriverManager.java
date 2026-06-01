package com.testframework.core.driver;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import com.testframework.core.driver.driver_initializers.ChromeDriverInitializer;
import com.testframework.core.report.TestReporter;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestResult;

import java.util.Objects;

public class DriverManager {
    private static final ThreadLocal<WebDriver> DRIVER_HOLDER = new ThreadLocal<>();

    private DriverManager() {
    }

    public static void initDriver(ITestResult testResult, String browser) {
        Objects.requireNonNull(browser, "browser is null");
        TestReporter.logger.info("Initializing driver for browser: " + browser);
        WebDriver webDriver = chooseDriver(testResult, browser);
        DRIVER_HOLDER.set(webDriver);
        WebDriverRunner.setWebDriver(webDriver);
        if ("chrome".equalsIgnoreCase(browser)) {
            webDriver.manage().window().maximize();
        }
    }

    private static WebDriver chooseDriver(ITestResult testResult, String browser) {
        WebDriver webDriver = null;

        if (browser.equalsIgnoreCase("chrome")) {
            webDriver = new ChromeDriverInitializer(testResult)
                    .initDriver();
        } else {
            throw new UnsupportedOperationException("Unsupported browser type: " + browser);
        }

        return webDriver;
    }

    public static WebDriver getDriver() {
        return DRIVER_HOLDER.get();
    }

    public static void closeDriver() {
        try {
            if (WebDriverRunner.hasWebDriverStarted()) {
                Selenide.closeWebDriver();
            }
        } catch (Exception e) {
            TestReporter.logger.warn("Failed to close Selenide WebDriver", e);
        } finally {
            DRIVER_HOLDER.remove();
        }
    }

    public static void clearCookies() {
        WebDriver driver = getDriver();
        if (driver != null)
            driver.manage().deleteAllCookies();
    }

    public static void clearStorages() {
        JavascriptExecutor jsExecutor = (JavascriptExecutor) getDriver();
        jsExecutor.executeScript("window.localStorage.clear(); window.sessionStorage.clear();");
    }

    public static void clearBrowserData() {
        clearCookies();
        clearStorages();
        getDriver().navigate().refresh();
    }
}
