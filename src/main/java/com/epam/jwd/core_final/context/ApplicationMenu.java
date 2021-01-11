package com.epam.jwd.core_final.context;

import java.io.InputStream;
import java.util.Scanner;

// todo replace Object with your own types
@FunctionalInterface
public interface ApplicationMenu {

    ApplicationContext getApplicationContext();

    default String printAvailableOptions(String options) {
        return "Please, enter number of choice and press Enter:\n" + options;
    }

    default Scanner handleUserInput(InputStream input) {
        return new Scanner(input);
    }
}
