package com.testframework.model.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.SelenideElement;
import com.testframework.core.report.TestReporter;
import org.openqa.selenium.support.ui.Select;

import static com.codeborne.selenide.Selenide.$;
import static com.testframework.model.providers.DataProviders.getValue;

public class MainPage extends BasePage<HistoryTabPage> {
    private static final String PAGE_NAME = "main_page";
    private final SelenideElement navigateProfile = $(Selectors.byXpath(getValue(PAGE_NAME, "nav-profile")));
    private final SelenideElement navHistory = $(Selectors.byXpath(getValue(PAGE_NAME, "nav-history")));
    private final SelenideElement navPlay = $(Selectors.byXpath(getValue(PAGE_NAME, "nav-play")));
    public static final SelenideElement HEADER = $(Selectors.byXpath(getValue(PAGE_NAME, "hello-user_tittle")));
    private final SelenideElement logoutButton = $(Selectors.byXpath(getValue(PAGE_NAME, "btn-logout")));
    private final SelenideElement selectLanguage = $(Selectors.byXpath(getValue(PAGE_NAME, "select_language")));

    protected MainPage() {
        super(HEADER);
    }

    public PlayPage openPlayTabPage() {
        navPlay.shouldBe(Condition.visible).click();
        TestReporter.reportDebugStep("History tab was clicked");
        return new PlayPage();
    }

    public HistoryTabPage openHistoryTabPage() {
        navHistory.shouldBe(Condition.visible).click();
        TestReporter.reportDebugStep("History tab was clicked");
        return new HistoryTabPage();
    }
    public ProfileTabPage openProfileTab() {
        navigateProfile.shouldBe(Condition.visible).click();
        TestReporter.reportDebugStep("Profile tab was clicked");
        return new ProfileTabPage();
    }

    public WelcomePage logout() {
        logoutButton.shouldBe(Condition.visible).click();
        TestReporter.reportDebugStep("Log out from system");
        return new WelcomePage();
    }

    public void selectLanguage(String value) {
        Select select = new Select(selectLanguage);
        select.selectByValue(value);
        TestReporter.reportDebugStep("%s - was selected", value);
    }
}
