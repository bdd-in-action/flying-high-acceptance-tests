package com.manning.bddinaction.frequentflyer.acceptancetests.screenplay.registration;

import com.manning.bddinaction.frequentflyer.acceptancetests.domain.persona.Traveller;
import com.manning.bddinaction.frequentflyer.acceptancetests.screenplay.api.LoginAPI;
import com.manning.bddinaction.frequentflyer.acceptancetests.screenplay.api.UserAPI;
import net.serenitybdd.markers.IsSilent;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.thucydides.core.annotations.Steps;

/**
 * Create a new user via the API.
 * We wrap a simple Rest Assured call in a Performable, as we are using the API to setup test data, not testing the API itself.
 */
public class RegisterViaTheAPI implements Performable, IsSilent {
    private Traveller memberDetails;

    public Performable withMemberDetailsFrom(Traveller memberDetails) {
        this.memberDetails = memberDetails;
        return this;
    }

    /**
     * This class provides methods to obtain the auth token needed to perform API operations
     */
    @Steps
    private LoginAPI loginAPI;

    @Steps
    private UserAPI userAPI;

    @Override
    public <T extends Actor> void performAs(T actor) {
        String authToken = loginAPI.getAuthToken();
        userAPI.createNewUser(authToken, memberDetails);
    }
}
