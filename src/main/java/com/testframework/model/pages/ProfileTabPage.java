package com.testframework.model.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.SelenideElement;
import com.testframework.core.report.TestReporter;
import com.testframework.core.utils.WaitingUtils;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$;
import static com.testframework.core.driver.DriverManager.getDriver;
import static com.testframework.model.pages.MainPage.HEADER;
import static com.testframework.model.providers.DataProviders.getValue;

public class ProfileTabPage extends BasePage<ProfileTabPage> {
    private static final String PAGE_NAME = "profile_tab_page";
    private final SelenideElement inputDisplayName = $(Selectors.byXpath(getValue(PAGE_NAME, "input-profile-name")));
    private final SelenideElement saveChanges = $(Selectors.byXpath(getValue(PAGE_NAME, "btn-save-profile")));
    private final SelenideElement deleteAccount = $(Selectors.byXpath(getValue(PAGE_NAME, "btn-delete-account")));
    private final SelenideElement profileNessage = $(Selectors.byXpath(getValue(PAGE_NAME, "profile-message")));

    protected ProfileTabPage() {
        super(HEADER);
    }

    private String getStatus(SelenideElement rowCellElementFormat) {
        return rowCellElementFormat.getText();
    }

    public ProfileTabPage setName(String name) {
        inputDisplayName.shouldBe(Condition.visible).clear();
        inputDisplayName.shouldBe(Condition.visible).val(name);
        TestReporter.reportDebugStep("%s name was set", name);
        return this;
    }

    public ProfileTabPage saveChanges() {
        saveChanges.shouldBe(Condition.visible).click();
        TestReporter.reportDebugStep("Save changes tab was clicked");
        return new ProfileTabPage();
    }

    public boolean isSaveMessageAppeared() {
        boolean isSaveMessage = WaitingUtils.isSelenideElement(Condition.visible, profileNessage, Duration.ofSeconds(4));
        TestReporter.reportDebugStep("%s Save changes message is displayed", isSaveMessage);
        return isSaveMessage;
    }

    public WelcomePage deleteAccount() {
        deleteAccount.shouldBe(Condition.visible).click();
        TestReporter.reportDebugStep("Delete account clicked");
        new WebDriverWait(getDriver(), Duration.ofSeconds(10))
                .until(ExpectedConditions.alertIsPresent())
                .accept();

        return new WelcomePage();
    }
}
