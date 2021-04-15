package com.manning.bddinaction.frequentflyer.acceptancetests.screenplay.ux;

import net.serenitybdd.screenplay.targets.Target;

class MaterialComponents {
    static final Target MATERIAL_OPTION = Target.the("option").locatedBy("css:mat-option[value='{0}']");
}
