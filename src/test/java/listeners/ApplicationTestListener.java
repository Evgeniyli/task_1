package listeners;

import com.testframework.core.driver.DriverManager;
import com.testframework.model.annotation.BrowserSession;
import com.testframework.core.report.TestReporter;
import io.qameta.allure.Description;
import io.qameta.allure.testng.AllureTestNg;
import lombok.SneakyThrows;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.*;
import org.testng.internal.IResultListener2;

import static com.testframework.core.driver.DriverManager.getDriver;
import static com.testframework.model.invoker.InvokerApplicationTestResultInfoService.getAnnotationInfo;
import static com.testframework.model.invoker.InvokerApplicationTestResultInfoService.getAnnotationValue;


public class ApplicationTestListener extends TestListenerAdapter implements
        IReporter,
        IExecutionListener,
        ISuiteListener,
        IResultListener2 {

    private final AllureTestNg allureTestNg = new AllureTestNg();

    @Override
    public void onStart(ISuite suite) {
        allureTestNg.onStart(suite);
    }

    @Override
    public void onStart(ITestContext testContext) {
        allureTestNg.onStart(testContext);
    }

    @Override
    public void onTestStart(ITestResult testResult) {
        if (createBrowserSession(testResult)) {
            DriverManager.initDriver(testResult, System.getProperty("browser"));
        }
        allureTestNg.onTestStart(testResult);
        TestReporter.logger.info("Test has been started : " + testResult.getName() + " in thread " + Thread.currentThread().getName());
    }

    @Override
    public void onTestSuccess(ITestResult testResult) {
        markTestStatus("passed", "Test was successfully completed: " + testResult.getName() + " in thread " + Thread.currentThread().getName());
        TestReporter.logger.info("Test was successfully completed: {} in thread {}", testResult.getName(), Thread.currentThread().getName());
        TestReporter.logger.info("Test description: {}", getAnnotationValue(testResult, Description.class));
        allureTestNg.onTestSuccess(testResult);
        closeBrowserSessionIfNeeded(testResult);
    }

    @Override
    public void onTestSkipped(ITestResult testResult) {
        TestReporter.logger.info("Test was skipped: {} in thread {}", testResult.getName(), Thread.currentThread().getName());
        allureTestNg.onTestSkipped(testResult);
        closeBrowserSessionIfNeeded(testResult);
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult testResult) {
        TestReporter.logger.info("Test was completed with errors: {} in thread {}", testResult.getName(), Thread.currentThread().getName());
        allureTestNg.onTestFailedButWithinSuccessPercentage(testResult);
        closeBrowserSessionIfNeeded(testResult);
    }

    @Override
    public void onTestFailure(ITestResult testResult) {
        TestReporter.logger.info("Test was failed: {} in thread {}", testResult.getName(), Thread.currentThread().getName());
        TestReporter.logger.info("Test description: {}", getAnnotationValue(testResult, Description.class));
        if (testResult.getThrowable() != null) {
            TestReporter.logger.error("Failure cause for {}:", testResult.getName(), testResult.getThrowable());
        }
        markTestStatus("failed", "Test was failed: " +
                testResult.getName() + " in thread " + Thread.currentThread().getName());
        allureTestNg.onTestFailure(testResult);
    }

    @Override
    public void onConfigurationFailure(ITestResult testResult) {

    }

    @Override
    public void onFinish(ITestContext testContext) {
        allureTestNg.onFinish(testContext);
    }

    @SneakyThrows
    @Override
    public void onFinish(ISuite suite) {
        allureTestNg.onFinish(suite);
    }

    private boolean createBrowserSession(ITestResult testResult) {
        BrowserSession annotationInfo = getAnnotationInfo(testResult, BrowserSession.class);
        return annotationInfo != null && annotationInfo.createBrowserSession();
    }

    private void markTestStatus(String status, String reason) {
        if (System.getProperty("browser").equalsIgnoreCase("ios")) {
            if (getDriver() == null) {
                TestReporter.logger.warn("Driver is NULL in markTestStatus() - Skipping status update!");
                return;
            }
            JavascriptExecutor js = (JavascriptExecutor) getDriver();
            String script = String.format(
                    "browserstack_executor: {\"action\": \"setSessionStatus\", \"arguments\": {\"status\": \"%s\", \"reason\": \"%s\"}}",
                    status, reason
            );
            js.executeScript(script);
        }
    }

    private void closeBrowserSessionIfNeeded(ITestResult testResult) {
        if (createBrowserSession(testResult)) {
            DriverManager.closeDriver();
        }
    }
}