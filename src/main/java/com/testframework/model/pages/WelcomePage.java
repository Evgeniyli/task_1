package com.testframework.model.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.SelenideElement;
import com.testframework.core.report.TestReporter;
import com.testframework.core.utils.WaitingUtils;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$;
import static com.testframework.model.providers.DataProviders.getValue;

public class WelcomePage extends BasePage<WelcomePage> {
    private static final String PAGE_NAME = "welcome_page";
    private final SelenideElement authError = $(Selectors.byXpath(getValue(PAGE_NAME, "auth-error")));
    private final SelenideElement playerNameText = $(Selectors.byText("Player name"));
    private final SelenideElement createAccountButton = $(Selectors.byXpath(getValue(PAGE_NAME, "create_account_button")));
    private final SelenideElement alreadyHaveAnAccountLink = $(Selectors.byXpath(getValue(PAGE_NAME, "already_have_an_account_link")));
    private final SelenideElement authSubtitle = $(Selectors.byXpath(getValue(PAGE_NAME, "auth-subtitle")));
    private final String btnThemeButtonFormat = getValue(PAGE_NAME, "btn_theme_format");
    private final SelenideElement btnTheme = $(Selectors.byXpath(getValue(PAGE_NAME, "btn_theme")));
    private final SelenideElement inputField = $(Selectors.byXpath(getValue(PAGE_NAME, "input-name")));
    private final SelenideElement subTitle = $(Selectors.byXpath(getValue(PAGE_NAME, "sub_title")));
    public static final SelenideElement HEADER = $(Selectors.byXpath(getValue(PAGE_NAME, "welcome_tittle")));

    public WelcomePage() {
        super(HEADER);
    }

    public boolean isASmallGameForTestAutomationTittleExist() {
        String titleMenuString = subTitle.getText();
        TestReporter.reportDebugStep("%s - tittle is displayed", titleMenuString);
        return subTitle.getText().equalsIgnoreCase("A small game for test automation");
    }

    public boolean isPlayerNameAppeared() {
        String titleMenuString = playerNameText.getText();
        TestReporter.reportDebugStep("%s - is displayed", titleMenuString);
        return titleMenuString.equalsIgnoreCase("Player name");
    }

    public boolean isCreateAccountButtonAppeared() {
        String titleMenuString = createAccountButton.getText();
        TestReporter.reportDebugStep("%s - is displayed", titleMenuString);
        return titleMenuString.equalsIgnoreCase("Create Account");
    }

    public boolean isEnterYourNameToStartPlayingAppeared() {
        String titleMenuString = authSubtitle.getText();
        TestReporter.reportDebugStep("%s - is displayed", titleMenuString);
        return titleMenuString.equalsIgnoreCase("Enter your name to start playing.");
    }

    public boolean isAlreadyHaveAnAccountLinkAppeared() {
        String titleMenuString = alreadyHaveAnAccountLink.getText();
        TestReporter.reportDebugStep("%s - is displayed", titleMenuString);
        return titleMenuString.equalsIgnoreCase("Already have an account? Log in");
    }

    public boolean isThemeAppeared(String value) {
        var btnThemeButtonElementFormat = $(Selectors.byXpath(String.format(btnThemeButtonFormat, value)));
        TestReporter.reportDebugStep("%s - was appeared", value);
        return WaitingUtils.isSelenideElement(Condition.visible, btnThemeButtonElementFormat, Duration.ofSeconds(3));
    }

    public WelcomePage changeTheme() {
        btnTheme.shouldBe(Condition.visible).click();
        TestReporter.reportDebugStep("theme was changed");
        return this;
    }

    public WelcomePage setPersonName(String personName) {
        inputField.shouldBe(Condition.visible).clear();
        inputField.shouldBe(Condition.visible).val(personName);
        TestReporter.reportDebugStep("%s name was set", personName);
        return this;
    }

    public MainPage clickCreateAccountButton() {
        createAccountButton.shouldBe(Condition.visible).click();
        TestReporter.reportDebugStep("The creation button was clicked");
        return new MainPage();
    }

    public boolean isPleaseEnterANameValidationErrorMessageAppeared() {
        String titleMenuString = authError.getText();
        TestReporter.reportDebugStep("%s - is displayed", titleMenuString);
        return titleMenuString.equalsIgnoreCase("Please enter a name.");
    }

}
