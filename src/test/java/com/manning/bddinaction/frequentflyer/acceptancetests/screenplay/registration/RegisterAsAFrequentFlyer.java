package com.manning.bddinaction.frequentflyer.acceptancetests.screenplay.registration;


import com.manning.bddinaction.frequentflyer.acceptancetests.domain.persona.Traveller;
import com.manning.bddinaction.frequentflyer.acceptancetests.screenplay.material.SelectFromDropdown;
import com.manning.bddinaction.frequentflyer.acceptancetests.screenplay.navigation.Navigate;
import net.serenitybdd.core.steps.Instrumented;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import net.serenitybdd.screenplay.actions.EnterValue;
import net.serenitybdd.screenplay.actions.SendKeys;
import net.serenitybdd.screenplay.actions.type.TypeValue;
import net.serenitybdd.screenplay.conditions.Check;
import org.openqa.selenium.Keys;

import static com.manning.bddinaction.frequentflyer.acceptancetests.screenplay.registration.RegistrationForm.*;

/**
 * The registration page is defined in the serenity.conf file, in the `pages.registration` property.
 */
public class RegisterAsAFrequentFlyer {
    public static Performable withMemberDetailsFrom(Traveller memberDetails) {
        return Task.where("{0} registers for a new Frequent Flyer account with email " + memberDetails.getEmail(),
                // Open the registraton page
                Navigate.toTheRegistrationPage(),

                // Complete the registration form
                Enter.theValue(memberDetails.getEmail()).into(EMAIL),
                Enter.theValue(memberDetails.getPassword()).into(PASSWORD),
                Enter.theValue(memberDetails.getFirstName()).into(FIRSTNAME),
                Enter.theValue(memberDetails.getLastName()).into(LASTNAME),
                Enter.theValue(memberDetails.getAddress()).into(ADDRESS),
                Enter.theValue(memberDetails.getCountry()).into(COUNTRY).thenHit(Keys.TAB),
                SelectFromDropdown.locatedBy(TITLE).selectingOption(memberDetails.getTitle()),
                Click.on(SEAT_PREFERENCE.of(memberDetails.getSeatPreference())),
                UpdateTermsAndConditions.basedOn(memberDetails.agreesToTermsAndConditions()),

                // Submit the registration
                Click.on(REGISTER)
        );
    }

    /** Since this is a static factory method, we need to create an instrumented version of the API object
     * that includes the API dependencies it needs to work.  */
    public static RegisterViaTheAPI viaTheAPI() {
        return Instrumented.instanceOf(RegisterViaTheAPI.class).newInstance();
    }
}
