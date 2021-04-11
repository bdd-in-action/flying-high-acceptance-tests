package com.manning.bddinaction.frequentflyer.acceptancetests.screenplay.navigation;

import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Open;

public class Navigate {
    public static Performable toTheLoginPage() {
        return Task.where("{0} goes to the login page",
                Open.browserOn().thePageNamed("pages.home"),
                Click.on(MenuBar.LOGIN_BUTTON)
        );
    }

    public static Performable toTheRegistrationPage() {
        return Task.where("{0} goes to the login page",
                Open.browserOn().thePageNamed("pages.registration")
        );
    }

    public static Performable toMyAccount() {
        return Task.where("{0} goes to the My Accounts page",
                Click.on(MenuBar.MY_ACCOUNT)
        );
    }
}
