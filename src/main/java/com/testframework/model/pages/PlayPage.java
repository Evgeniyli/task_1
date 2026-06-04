package com.testframework.model.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.SelenideElement;
import com.testframework.core.report.TestReporter;
import com.testframework.core.utils.WaitingUtils;
import org.openqa.selenium.support.ui.Select;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.codeborne.selenide.Selenide.$;
import static com.testframework.model.pages.MainPage.HEADER;
import static com.testframework.model.providers.DataProviders.getValue;;

public class PlayPage extends BasePage<PlayPage> {
    private static final String PAGE_NAME = "play_tab_page";
    private final String rowCell = getValue(PAGE_NAME, "rowCell");
    private final SelenideElement status = $(Selectors.byXpath(getValue(PAGE_NAME, "status")));
    private final SelenideElement btnNew = $(Selectors.byXpath(getValue(PAGE_NAME, "btn-new")));
    private final SelenideElement btnHint = $(Selectors.byXpath(getValue(PAGE_NAME, "btn-hint")));
    private final SelenideElement btnReset = $(Selectors.byXpath(getValue(PAGE_NAME, "btn-reset")));
    private final SelenideElement selectDifficulty = $(Selectors.byXpath(getValue(PAGE_NAME, "select-difficulty")));

    protected PlayPage() {
        super(HEADER);
    }

    public boolean getGameResult(List<String> list,
                                 List<String> list2,
                                 List<String> list3, String initiateStatus) {

        var firstResult = playGame(list, initiateStatus);
        var secondResult = playGame(list2, initiateStatus);
        var thirdResult = playGame(list3, initiateStatus);
        return firstResult || secondResult || thirdResult;
    }

    public boolean getGameResultWithoutStatus(List<String> list,
                                              List<String> list2,
                                              List<String> list3) {

        var firstResult = playGameWithoutStatus(list);
        var secondResult = playGameWithoutStatus(list2);
        var thirdResult = playGameWithoutStatus(list3);
        return firstResult || secondResult || thirdResult;
    }

    public boolean playGame(List<String> list, String initiateStatus) {
        for (String item : list) {
            var rowCellElementFormat = $(Selectors.byXpath(String.format(rowCell, item)));
            if (rowCellElementFormat.getAttribute("disabled") == null) {
                rowCellElementFormat.click();
                WaitingUtils.delay(3, TimeUnit.SECONDS);
            }
            String status = getStatus();
            if (status.equalsIgnoreCase(initiateStatus)) {
                return true;
            }
        }
        return false;
    }

    public boolean playGameWithoutStatus(List<String> list) {
        for (String item : list) {
            var rowCellElementFormat = $(Selectors.byXpath(String.format(rowCell, item)));
            if (rowCellElementFormat.getAttribute("disabled") == null) {
                rowCellElementFormat.click();
                WaitingUtils.delay(3, TimeUnit.SECONDS);
            }
            String status = getStatus();
            if (status.equalsIgnoreCase("You win!") ||
                    status.equalsIgnoreCase("Computer wins.") ||
                    status.equalsIgnoreCase("Draw.")) {
                return true;
            }
        }
        return false;
    }


    public String getStatus() {
        String statusText = status.getText();
        TestReporter.reportDebugStep("%s - is displayed", statusText);
        return statusText;
    }

    public String getNewGameButtonText() {
        String newText = btnNew.getText();
        TestReporter.reportDebugStep("%s - is displayed", newText);
        return newText;
    }

    public String getHintButtonText() {
        String newText = btnHint.getText();
        TestReporter.reportDebugStep("%s - is displayed", newText);
        return newText;
    }

    public String getResetButtonText() {
        String newText = btnReset.getText();
        TestReporter.reportDebugStep("%s - is displayed", newText);
        return newText;
    }

    public void clickResetButton() {
        btnReset.shouldBe(Condition.visible).click();
        TestReporter.reportDebugStep("Reset button was clicked");
    }

    public void selectDifficulty(String criteria) {
        Select select = new Select(selectDifficulty);
        select.selectByValue(criteria);
        TestReporter.reportDebugStep("%s - was selected", criteria);
    }
}
