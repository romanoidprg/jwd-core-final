package com.epam.jwd.core_final;

import com.epam.jwd.core_final.context.Application;
import com.epam.jwd.core_final.exception.InvalidStateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Main {
    final static Logger logger =LoggerFactory.getLogger(com.epam.jwd.core_final.Main.class);
    public static void main(String[] args) {
        try {
            Application.start();
        } catch (InvalidStateException e) {
            logger.error(e.getMessage());
        }
    }
}