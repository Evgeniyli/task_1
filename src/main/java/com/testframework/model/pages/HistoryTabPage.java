package com.testframework.model.pages;

import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.SelenideElement;
import com.testframework.core.report.TestReporter;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import static com.codeborne.selenide.Selenide.$;
import static com.testframework.model.pages.MainPage.HEADER;
import static com.testframework.model.providers.DataProviders.getValue;

public class HistoryTabPage extends BasePage<HistoryTabPage> {
    private static final String PAGE_NAME = "history_tab_page";
    private final SelenideElement historyEmpty = $(Selectors.byXpath(getValue(PAGE_NAME, "history-empty")));
    private final SelenideElement historyResult = $(Selectors.byXpath(getValue(PAGE_NAME, "history-result")));
    private final SelenideElement historyDifficulty = $(Selectors.byXpath(getValue(PAGE_NAME, "history-difficulty")));
    private final SelenideElement historyDate = $(Selectors.byXpath(getValue(PAGE_NAME, "history-date")));

    protected HistoryTabPage() {
        super(HEADER);
    }

    public boolean isNoGamesYetMessageAppeared() {
        String titleMenuString = historyEmpty.getText();
        TestReporter.reportDebugStep("%s - error validation is displayed", titleMenuString);
        return titleMenuString.equalsIgnoreCase("No games yet. Play one!");
    }

    public boolean isHistoryResultAppeared(String status) {
        String statusText = historyResult.getText();
        TestReporter.reportDebugStep("%s - is displayed", statusText);
        return statusText.equalsIgnoreCase(status);
    }

    public boolean isHistoryDifficultyAppeared(String criteria) {
        String statusText = historyDifficulty.getText();
        TestReporter.reportDebugStep("%s - is displayed", statusText);
        return statusText.equalsIgnoreCase(criteria);
    }

    public boolean isHistoryDateAppeared(LocalDateTime actualDateTime) {
        String input = historyDate.getText();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy, h:mm:ss a", Locale.ENGLISH);
        LocalDateTime expectedDateTime = LocalDateTime.parse(input, formatter);
        long diff = Math.abs(Duration.between(expectedDateTime, actualDateTime).getSeconds());
        TestReporter.reportDebugStep("%s - different between seconds", diff);
        return diff <= 5;
    }
}
