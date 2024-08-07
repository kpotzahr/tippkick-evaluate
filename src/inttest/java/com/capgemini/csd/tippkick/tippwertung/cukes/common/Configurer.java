package com.capgemini.csd.tippkick.tippwertung.cukes.common;

import com.capgemini.csd.tippkick.tippwertung.cukes.steps.to.GameBetTestTO;
import com.capgemini.csd.tippkick.tippwertung.cukes.steps.to.UserScoreTestTO;
import cucumber.api.TypeRegistry;
import cucumber.api.TypeRegistryConfigurer;
import io.cucumber.datatable.DataTableType;

import java.util.Locale;

public class Configurer implements TypeRegistryConfigurer {

    @Override
    public Locale locale() {
        return Locale.ENGLISH;
    }

    @Override
    public void configureTypeRegistry(TypeRegistry typeRegistry) {
        typeRegistry.defineDataTableType(DataTableType.entry(GameBetTestTO.class));
        typeRegistry.defineDataTableType(DataTableType.entry(UserScoreTestTO.class));
    }

}
