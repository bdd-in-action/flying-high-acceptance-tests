package com.manning.bddinaction.frequentflyer.acceptancetests.screenplay.login;

import com.manning.bddinaction.frequentflyer.acceptancetests.domain.FrequentFlyer;
import com.manning.bddinaction.frequentflyer.acceptancetests.domain.persona.Traveller;
import com.manning.bddinaction.frequentflyer.acceptancetests.screenplay.navigation.Navigate;
import com.manning.bddinaction.frequentflyer.acceptancetests.screenplay.ux.Acknowledge;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.SendKeys;

public class Login {
    public static Performable as(Traveller traveller) {
        return Task.where("{0} logs in as " + traveller.email(),
                Navigate.toTheLoginPage(),
                SendKeys.of(traveller.email()).into(LoginForm.EMAIL),
                SendKeys.of(traveller.password()).into(LoginForm.PASSWORD),
                Click.on(LoginForm.LOGIN_BUTTON)
        );
    }
}
