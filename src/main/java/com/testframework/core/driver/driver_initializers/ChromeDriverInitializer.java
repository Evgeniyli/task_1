package com.testframework.core.driver.driver_initializers;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.ITestResult;

public class ChromeDriverInitializer extends BaseDriverInitializer {

    public ChromeDriverInitializer(ITestResult testResult) {
        super(testResult);
    }

    @Override
    public WebDriver initDriver() {
        ChromeOptions chromeOptions = new ChromeOptions();
        return new ChromeDriver(chromeOptions);
    }
}
