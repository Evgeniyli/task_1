package com.testframework.smoke;

import com.testframework.BaseTest;
import com.testframework.core.generator.PersonGenerator;
import com.testframework.core.utils.StringUtils;
import com.testframework.model.annotation.BrowserSession;
import com.testframework.model.pages.MainPage;
import com.testframework.model.pages.WelcomePage;
import io.qameta.allure.Description;
import io.qameta.allure.TmsLink;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

import static com.testframework.model.constants.Environments.*;
import static com.testframework.utils.test_constants.TestConstants.*;
import static org.testng.Assert.assertTrue;

public class PlayGameTest extends BaseTest {
    List<String> list;
    List<String> list2;
    List<String> list3;

    @BeforeClass(alwaysRun = true)
    public void setUp() {
        list = Arrays.asList("cell-0", "cell-1", "cell-2");
        list2 = Arrays.asList("cell-3", "cell-4", "cell-5");
        list3 = Arrays.asList("cell-6", "cell-7", "cell-8");
    }

    @Test(groups = "test_ui")
    @BrowserSession
    @TmsLink("https://keabank.atlassian.net/browse/KSP-111111")
    @Description("Verify welcome functionality is working")
    public void testCase_verifyWelcomeFunctionalityIsWorking() {
        WelcomePage welcomePage = new WelcomePage().openWindow(GAME_ENV)
                .waitPageLoading(Duration.ofSeconds(15));

        assertTrue(welcomePage.isASmallGameForTestAutomationTittleExist());
        assertTrue(welcomePage.isPlayerNameAppeared());
        assertTrue(welcomePage.isCreateAccountButtonAppeared());
        assertTrue(welcomePage.isEnterYourNameToStartPlayingAppeared());
        assertTrue(welcomePage.isAlreadyHaveAnAccountLinkAppeared());

        assertTrue(welcomePage.isThemeAppeared(DARK));
        welcomePage.changeTheme();
        assertTrue(welcomePage.isThemeAppeared(LIGHT));
        welcomePage.changeTheme();
        assertTrue(welcomePage.isThemeAppeared(DARK));

        welcomePage.clickCreateAccountButton();
        assertTrue(welcomePage.isPleaseEnterANameValidationErrorMessageAppeared());
    }

    @Test(groups = "test_ui")
    @BrowserSession
    @TmsLink("https://keabank.atlassian.net/browse/KSP-111112")
    @Description("Verify that created account is deleted successfully")
    public void testCase_verifyCreatedAccountIsDeletedSuccessfully() {
        String lastName = PersonGenerator.createPerson()
                .getLastName() + StringUtils.getRandomNumber();

        var mainPage = new WelcomePage().openWindow(GAME_ENV)
                .waitPageLoading(Duration.ofSeconds(5))
                .setPersonName(lastName)
                .clickCreateAccountButton();

        String lastName2 = PersonGenerator.createPerson()
                .getLastName() + StringUtils.getRandomNumber();

        var profile = mainPage.openProfileTab()
                .waitPageLoading(Duration.ofSeconds(4))
                .setName(lastName2)
                .saveChanges();

        Assert.assertTrue(profile.isSaveMessageAppeared());
        System.out.println(MainPage.HEADER.getText());
        Assert.assertTrue(MainPage.HEADER.getText()
                .equalsIgnoreCase("Hello, " + lastName2));

        var historyTab = mainPage.openHistoryTabPage()
                .waitPageLoading(Duration.ofSeconds(2));

        Assert.assertTrue(historyTab.isNoGamesYetMessageAppeared());
        mainPage.openProfileTab()
                .waitPageLoading(Duration.ofSeconds(2))
                .deleteAccount()
                .waitPageLoading(Duration.ofSeconds(2));
    }

    @Test(groups = "test_ui")
    @BrowserSession
    @TmsLink("https://keabank.atlassian.net/browse/KSP-111112")
    @Description("Verify basic logic is working successfully")
    public void testCase_verifyBasicLogicIsWorkingSuccessfully() {
        String lastName = PersonGenerator.createPerson()
                .getLastName() + StringUtils.getRandomNumber();

        var mainPage = new WelcomePage().openWindow(GAME_ENV)
                .waitPageLoading(Duration.ofSeconds(5))
                .setPersonName(lastName)
                .clickCreateAccountButton();

        var playGame = mainPage.openPlayTabPage();
        mainPage.selectLanguage(ENGLISH);
        mainPage.selectLanguage(PERSIAN);
        Assert.assertTrue(playGame.getNewGameButtonText().equalsIgnoreCase("بازی جدید"));
        Assert.assertTrue(playGame.getHintButtonText().equalsIgnoreCase("راهنمایی"));
        Assert.assertTrue(playGame.getResetButtonText().equalsIgnoreCase("بازنشانی"));

        mainPage.selectLanguage(ENGLISH);
        mainPage.openPlayTabPage();

        playGame.selectDifficulty(EASY);
        assertTrue(playGame.getGameResult(list, list2, list3));

        playGame.clickResetButton();
        assertTrue(playGame.getGameResult(list3, list2, list));

        playGame.clickResetButton();
        playGame.selectDifficulty(MEDIUM);
        assertTrue(playGame.getGameResult(list, list2, list3));

        playGame.clickResetButton();
        playGame.selectDifficulty(HARD);
        assertTrue(playGame.getGameResult(list, list2, list3));
    }
}
