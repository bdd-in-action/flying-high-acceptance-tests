package com.manning.bddinaction.frequentflyer.acceptancetests.screenplay.navigation;

import net.serenitybdd.screenplay.targets.Target;

class MenuBar {
    private static Target BUTTON = Target.the("{0} button").locatedBy("//button[contains(.,'{0}')]");

    static Target LOGIN_BUTTON = BUTTON.of("Login");
    static Target MY_ACCOUNT = BUTTON.of("My Account");
    static Target REGISTER_BUTTON = BUTTON.of("Register");
}
