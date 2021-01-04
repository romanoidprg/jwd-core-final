package com.epam.jwd.core_final;

import com.epam.jwd.core_final.context.Application;
import com.epam.jwd.core_final.domain.ApplicationProperties;
import com.epam.jwd.core_final.domain.Role;
import com.epam.jwd.core_final.exception.InvalidStateException;
import com.epam.jwd.core_final.exception.UnknownEntityException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Main {

    final static Logger logger = LoggerFactory.getLogger(com.epam.jwd.core_final.Main.class);

    public static void main(String[] args) {
        Role role = Role.COMMANDER;
        try {
            Application.start();
        } catch (InvalidStateException e) {
            logger.error(e.getMessage());
        }

        try {
            System.out.println(role.resolveRoleById(8));
        } catch (UnknownEntityException e) {

        }
        logger.error("_" + ApplicationProperties.getInputRootDir() + "_");
        logger.error("_" + ApplicationProperties.getOutputRootDir() + "_");
        logger.error("_" + ApplicationProperties.getCrewFileName() + "_");
        logger.error("_" + ApplicationProperties.getMissionsFileName() + "_");
        logger.error("_" + ApplicationProperties.getSpaceshipsFileName() + "_");
        logger.error("_" + ApplicationProperties.getFileRefreshRate() + "_");
        logger.error("_" + ApplicationProperties.getDateTimeFormat() + "_");
    }
}