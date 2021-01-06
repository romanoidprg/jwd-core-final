package com.epam.jwd.core_final;

import com.epam.jwd.core_final.context.Application;
import com.epam.jwd.core_final.context.impl.NassaContext;
import com.epam.jwd.core_final.criteria.CrewMemberCriteria;
import com.epam.jwd.core_final.domain.ApplicationProperties;
import com.epam.jwd.core_final.domain.CrewMember;
import com.epam.jwd.core_final.domain.Rank;
import com.epam.jwd.core_final.domain.Role;
import com.epam.jwd.core_final.exception.InvalidStateException;
import com.epam.jwd.core_final.exception.UnknownEntityException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;


public class Main {

    final static Logger logger = LoggerFactory.getLogger(Main.class);
    final static NassaContext nassa = new NassaContext();

    public static void main(String[] args) {
        Role role = Role.COMMANDER;
        try {
            Application.start();
        } catch (InvalidStateException e) {
            logger.error(e.getMessage());
        }

        try {
            System.out.println(role.resolveRoleById(3));
        } catch (UnknownEntityException e) {

        }
        logger.info("_" + ApplicationProperties.getInputRootDir() + "_");
        logger.info("_" + ApplicationProperties.getOutputRootDir() + "_");
        logger.info("_" + ApplicationProperties.getCrewFileName() + "_");
        logger.info("_" + ApplicationProperties.getMissionsFileName() + "_");
        logger.info("_" + ApplicationProperties.getSpaceshipsFileName() + "_");
        logger.info("_" + ApplicationProperties.getFileRefreshRate() + "_");
        logger.info("_" + ApplicationProperties.getDateTimeFormat() + "_");

        CrewMemberCriteria criteria = new CrewMemberCriteria.Builder()
                .whereRankIs(Rank.CAPTAIN)
                .whereRoleIs(Role.COMMANDER)
                .readyForNextMissionsIs(true)
                .build();
        logger.info(criteria.toString());


    }
}