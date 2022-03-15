package com.manning.bddinaction.frequentflyer.acceptancetests.screenplay.navigation;

import com.manning.bddinaction.frequentflyer.acceptancetests.screenplay.myaccount.StatusPanel;
import com.manning.bddinaction.frequentflyer.acceptancetests.screenplay.search.SearchFlightsForm;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Open;
import net.serenitybdd.screenplay.waits.WaitUntil;
import net.thucydides.core.annotations.Title;

import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isNotEmpty;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

/**
 * Navigate to different parts of the application via the main navigation bar
 */
public class Navigate {
    public static Performable toTheRegistrationPage() {
        return Task.where("{0} navigates to the registration page",
                Open.browserOn().thePageNamed("pages.home"),
                Click.on(MenuBar.REGISTER)
        );
    }

    public static Performable toTheLoginPage() {
        return Task.where("{0} navigates to the login page",
                Open.browserOn().thePageNamed("pages.home"),
                Click.on(MenuBar.LOGIN_BUTTON)
        );
    }


    public static Performable toBookFlights() {
        return Task.where("{0} navigates to the booking page",
                Open.browserOn().thePageNamed("pages.home"),
                Click.on(MenuBar.BOOK_FLIGHTS),
                WaitUntil.the(SearchFlightsForm.FROM, isVisible()).forNoMoreThan(10).seconds()
        );
    }

    public static Performable toMyAccount() {
        return Task.where("{0} navigates to my accounts",
                Click.on(MenuBar.MY_ACCOUNT),
                WaitUntil.the(StatusPanel.STATUS_LEVEL, isNotEmpty()),
                WaitUntil.the(StatusPanel.POINT_BALANCE, isNotEmpty())
        );
    }
}
