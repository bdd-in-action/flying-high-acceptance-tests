package com.manning.bddinaction.frequentflyer.acceptancetests.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginForm {
    private final WebDriver driver;

    public LoginForm(WebDriver driver) {
        this.driver = driver;
    }

    public void open() {
        driver.get("http://localhost:3000/login");
    }

    public void signinWithCredentials(String email, String password) {
        driver.findElement(By.id("email")).sendKeys(email);
        driver.findElement(By.id("password")).sendKeys(password);
        driver.findElement(By.id("login-button")).click();
    }
}
