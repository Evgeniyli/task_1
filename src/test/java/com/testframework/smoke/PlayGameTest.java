package com.testframework.smoke;

import com.codeborne.selenide.Selenide;
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
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static com.testframework.model.constants.Environments.*;
import static com.testframework.utils.test_constants.TestConstants.*;
import static org.testng.Assert.assertTrue;

public class PlayGameTest extends BaseTest {

    List<String> winnerList;
    List<String> winnerList2;
    List<String> winnerList3;

    @BeforeClass(alwaysRun = true)
    public void setUp() {
        winnerList = Arrays.asList("cell-0", "cell-1", "cell-2");
        winnerList2 = Arrays.asList("cell-3", "cell-4", "cell-5");
        winnerList3 = Arrays.asList("cell-6", "cell-7", "cell-8");
    }

    @Test(groups = "test_ui")
    @BrowserSession
    @TmsLink("TC-001" + "TC-021" + "TC-003")
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
    @TmsLink("TC-017" + "TC-018" + "TC-019" + "TC-023")
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
        Selenide.refresh();
        Assert.assertTrue(MainPage.HEADER.getText()
                .equalsIgnoreCase("Hello, " + lastName2));

        mainPage.openProfileTab()
                .waitPageLoading(Duration.ofSeconds(2))
                .deleteAccount()
                .waitPageLoading(Duration.ofSeconds(2));
    }

    @Test(groups = "test_ui")
    @BrowserSession
    @TmsLink("TC-013" + "TC-014" + "TC-015" + "TC-022" + "TC-002" + "TC-004")
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
        assertTrue(playGame.getGameResultWithoutStatus(winnerList, winnerList2, winnerList3));

        playGame.clickResetButton();
        assertTrue(playGame.getGameResultWithoutStatus(winnerList3, winnerList2, winnerList));

        playGame.clickResetButton();
        playGame.selectDifficulty(MEDIUM);
        assertTrue(playGame.getGameResultWithoutStatus(winnerList, winnerList2, winnerList3));

        playGame.clickResetButton();
        playGame.selectDifficulty(HARD);
        assertTrue(playGame.getGameResultWithoutStatus(winnerList, winnerList2, winnerList3));

    }


    @Test(groups = "test_ui")
    @BrowserSession
    @TmsLink("TC-008")
    @Description("Player Wins by Row")
    public void testCase_verifyThatPlayerWinsByRow() {
        String lastName = PersonGenerator.createPerson()
                .getLastName() + StringUtils.getRandomNumber();

        var mainPage = new WelcomePage().openWindow(GAME_ENV)
                .waitPageLoading(Duration.ofSeconds(5))
                .setPersonName(lastName)
                .clickCreateAccountButton();

        mainPage.selectLanguage(ENGLISH);
        var playGame = mainPage.openPlayTabPage();

        playGame.selectDifficulty(EASY);
        assertTrue(playGame.getGameResult(winnerList, winnerList2, winnerList3, YOU_WIN));
    }

    @Test(groups = "test_ui")
    @BrowserSession
    @TmsLink("TC-020" + "TC-024" + "TC-006" + "TC-007")
    @Description("Check History")
    public void testCase_verifyThatHistoryDisplayedCorrectlyData() {
        String lastName = PersonGenerator.createPerson()
                .getLastName() + StringUtils.getRandomNumber();

        var mainPage = new WelcomePage().openWindow(GAME_ENV)
                .waitPageLoading(Duration.ofSeconds(5))
                .setPersonName(lastName)
                .clickCreateAccountButton();

        var historyTab = mainPage.openHistoryTabPage()
                .waitPageLoading(Duration.ofSeconds(2));

        Assert.assertTrue(historyTab.isNoGamesYetMessageAppeared());
        var playGame = mainPage.openPlayTabPage();
        playGame.selectDifficulty(EASY);
        assertTrue(playGame.getGameResult(winnerList, winnerList2, winnerList3, YOU_WIN));

        LocalDateTime now = LocalDateTime.now();
        mainPage.openHistoryTabPage();
        Assert.assertTrue(historyTab.isHistoryDifficultyAppeared(EASY1));
        Assert.assertTrue(historyTab.isHistoryResultAppeared(WIN));
        Assert.assertTrue(historyTab.isHistoryDateAppeared(now));
    }

    @Test(groups = "test_ui")
    @BrowserSession
    @TmsLink("TC-005")
    @Description("Logout")
    public void testCase_verifyThatLogoutIsWorking() {
        String lastName = PersonGenerator.createPerson()
                .getLastName() + StringUtils.getRandomNumber();

        var mainPage = new WelcomePage().openWindow(GAME_ENV)
                .waitPageLoading(Duration.ofSeconds(5))
                .setPersonName(lastName)
                .clickCreateAccountButton();

        var historyTab = mainPage.openHistoryTabPage()
                .waitPageLoading(Duration.ofSeconds(2));

        Assert.assertTrue(historyTab.isNoGamesYetMessageAppeared());
        mainPage.logout()
                .waitPageLoading(Duration.ofSeconds(3));
    }
}
