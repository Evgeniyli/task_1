package com.testframework.model.pages;

import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.SelenideElement;
import com.testframework.core.report.TestReporter;

import static com.codeborne.selenide.Selenide.$;
import static com.testframework.model.pages.MainPage.HEADER;
import static com.testframework.model.providers.DataProviders.getValue;

public class HistoryTabPage extends BasePage<HistoryTabPage> {
    private static final String PAGE_NAME = "history_tab_page";
    private final SelenideElement historyEmpty = $(Selectors.byXpath(getValue(PAGE_NAME, "history-empty")));

    protected HistoryTabPage() {
        super(HEADER);
    }

    public boolean isNoGamesYetMessageAppeared() {
        String titleMenuString = historyEmpty.getText();
        TestReporter.reportDebugStep("%s - error validation is displayed", titleMenuString);
        return titleMenuString.equalsIgnoreCase("No games yet. Play one!");
    }
}
