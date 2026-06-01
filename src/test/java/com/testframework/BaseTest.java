package com.testframework;

import com.testframework.model.constants.Environments;
import listeners.ApplicationTestListener;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;

@Listeners({ApplicationTestListener.class})
public class BaseTest {

    @BeforeSuite(alwaysRun = true)
    public void initEnvironmentsLink() {
        Environments.GAME_ENV = String.valueOf(getClass()
                .getClassLoader()
                .getResource("index.html"));;
    }
}
