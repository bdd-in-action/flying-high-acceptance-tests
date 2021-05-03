package com.manning.bddinaction.frequentflyer.acceptancetests.pageobjects;

import com.manning.bddinaction.frequentflyer.acceptancetests.domain.FrequentFlyer;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CurrentUser {

    private WebDriver driver;

    public CurrentUser(WebDriver driver) { this.driver = driver; }

    private static final By CURENT_USER = By.id("current-user");

    public String email() {
        return driver.findElement(CURENT_USER).getText();
    }
}
