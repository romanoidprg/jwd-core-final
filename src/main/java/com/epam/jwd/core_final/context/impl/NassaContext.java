package com.epam.jwd.core_final.context.impl;

import com.epam.jwd.core_final.context.ApplicationContext;
import com.epam.jwd.core_final.domain.ApplicationProperties;
import com.epam.jwd.core_final.domain.BaseEntity;
import com.epam.jwd.core_final.domain.CrewMember;
import com.epam.jwd.core_final.domain.FlightMission;
import com.epam.jwd.core_final.domain.Spaceship;
import com.epam.jwd.core_final.exception.InvalidStateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;

// todo
public class NassaContext implements ApplicationContext {

    // no getters/setters for them
    private final Collection<CrewMember> crewMembers = new ArrayList<>();
    private final Collection<Spaceship> spaceships = new ArrayList<>();
    private final Collection<FlightMission> flightMissions = new ArrayList<>();
    private final File crewFile = new File(ApplicationProperties.getCrewFileName());
    private final File spaceshipsFile = new File(ApplicationProperties.getSpaceshipsFileName());
    private final Logger logger = LoggerFactory.getLogger(NassaContext.class);

    @Override
    public <T extends BaseEntity> Collection<T> retrieveBaseEntityList(Class<T> tClass) {
        Collection<T> list = null;
        if (tClass.getSimpleName().equals("CrewMember"))
            list = (Collection<T>) crewMembers;
        else if (tClass.getSimpleName().equals("Spaceship"))
            list = (Collection<T>) spaceships;
        else if (tClass.getSimpleName().equals("FlightMission"))
            list = (Collection<T>) flightMissions;
        return list;
    }

    /**
     * You have to read input files, populate collections
     *
     * @throws InvalidStateException
     */
    @Override
    public void init() throws InvalidStateException {
        fillCrewMembersListFromFile(crewMembers, crewFile);
        fillSpaceshipsListFromFile(spaceships, spaceshipsFile);
    }

    private void fillCrewMembersListFromFile(Collection<CrewMember> crewMembers, File file) throws InvalidStateException {
        try {
            Scanner scanner = new Scanner(file);
            scanner.useDelimiter("");
            while (scanner.hasNext("#")) {
                scanner.nextLine();
            }
            scanner.useDelimiter(";");
            while (scanner.hasNext()) {
                crewMembers.add(new CrewMember(scanner.next()));
                if (crewMembers.contains(null))
                    throw new InvalidStateException(file);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            logger.error(e.getMessage());
        }
    }

    private void fillSpaceshipsListFromFile(Collection<Spaceship> spaceships, File file) throws InvalidStateException {
        try {
            Scanner scanner = new Scanner(file);
            scanner.useDelimiter("");
            while (scanner.hasNext("#")) {
                scanner.nextLine();
            }
            while (scanner.hasNext()) {
                spaceships.add(new Spaceship(scanner.nextLine()));
                if (spaceships.contains(null))
                    throw new InvalidStateException(file);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            logger.error(e.getMessage());
        }
    }
}
