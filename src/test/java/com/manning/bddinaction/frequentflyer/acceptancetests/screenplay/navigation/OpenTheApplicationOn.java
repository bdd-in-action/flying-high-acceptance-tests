package com.manning.bddinaction.frequentflyer.acceptancetests.screenplay.navigation;

import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Open;

/**
 * Open the application directly on a given page, without passing via the home page.
 */
public class OpenTheApplicationOn {
    public static Performable theRegistrationPage() {
        return Task.where("{0} goes to the registration page",
                Open.browserOn().thePageNamed("pages.registration")
        );
    }

    public static Performable toMyAccount() {
        return Task.where("{0} goes to the My Accounts page",
                Open.browserOn().thePageNamed("pages.my.accounts")
        );
    }

    public static Performable toMyBookings() {
        return Task.where("{0} goes to the My Bookings page",
                Open.browserOn().thePageNamed("pages.my.bookings")
        );
    }

    public static Performable toBookFlights() {
        return Task.where("{0} goes to the Book Flights page",
                Open.browserOn().thePageNamed("pages.search")
        );
    }
}
