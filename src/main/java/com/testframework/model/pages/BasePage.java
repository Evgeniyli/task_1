package com.testframework.model.pages;

import com.codeborne.selenide.SelenideElement;
import com.testframework.core.exceptions.ExceptionError;
import com.testframework.core.report.TestReporter;
import org.apache.commons.lang3.StringUtils;

import java.time.Duration;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public abstract class BasePage<PAGE extends BasePage<PAGE>> {
    private final SelenideElement pageLoadingElement;

    protected BasePage(SelenideElement pageLoadingElement) {
        this.pageLoadingElement = $(pageLoadingElement);
    }

    public PAGE openWindow(String pageURL) {
        if (StringUtils.isNotEmpty(pageURL)) {
            open(pageURL);
            TestReporter.reportDebugStep("%s was opened", this.getClass().getSimpleName());
            TestReporter.reportDebugStep("%s page was opened", pageURL);
        } else {
            throw new ExceptionError("Page URL not establish");
        }
        return (PAGE) this;
    }

    /**
     * Wait page loading.
     * Loading locator use from constructor <b>pageLoadingLocator</b> argument.
     *
     * @return current page instance
     */
    public PAGE waitPageLoading(Duration duration) {
        TestReporter.reportDebugStep("Wait %s loading", this.getClass().getSimpleName());
        pageLoadingElement.shouldBe(visible, duration);
        return (PAGE) this;
    }
}
