package com.testframework.core.utils;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebElementCondition;
import com.codeborne.selenide.ex.UIAssertionError;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class WaitingUtils {

    /**
     * Make a delay
     *
     * @param time     time
     * @param timeUnit time unit
     */
    public static void delay(long time, TimeUnit timeUnit) {
        try {
            Thread.sleep(timeUnit.toMillis(time));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Explicitly waiter for <i>SelenideElement</i> with <b>Selenide</b> condition.
     * If element not execute condition, exception will not throw.
     *
     * @param condition       waiting exit condition
     * @param selenideElement web element
     * @return true or false
     */
    public static boolean isSelenideElement(WebElementCondition condition, SelenideElement selenideElement, Duration duration) {
        try {
            selenideElement.shouldBe(condition, duration);
            return true;
        } catch (UIAssertionError e) {
            return false;
        }
    }
}

