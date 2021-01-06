package com.epam.jwd.core_final.context.impl;

import com.epam.jwd.core_final.context.ApplicationContext;
import com.epam.jwd.core_final.domain.*;
import com.epam.jwd.core_final.exception.InvalidStateException;
import com.fasterxml.jackson.databind.ser.Serializers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

// todo
public class NassaContext implements ApplicationContext {

    // no getters/setters for them
    private Collection<CrewMember> crewMembers = new ArrayList<>();
    private Collection<Spaceship> spaceships = new ArrayList<>();

    private final File crewFile = new File(ApplicationProperties.getCrewFileName());
    private final File spaceshipsFile = new File(ApplicationProperties.getSpaceshipsFileName());

    private final Logger logger = LoggerFactory.getLogger(NassaContext.class);

    @Override
    public <T extends BaseEntity> Collection<T> retrieveBaseEntityList(Class<T> tClass) {
        Collection<T> list = null;
        if (tClass.getSimpleName().equals("CrewMember"))
            list = (Collection<T>) crewMembers;
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

        throw new InvalidStateException();
    }

    private void fillCrewMembersListFromFile(Collection<CrewMember> crewMembers, File file) {
        try {
            Scanner scanner = new Scanner(file);
            scanner.useDelimiter(";");
            while (scanner.hasNext()) {
                crewMembers.add(new CrewMember(scanner.next()));
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            logger.error(e.getMessage());
        }

    }

    private void fillSpaceshipsListFromFile(Collection<Spaceship> spaceships, File file) throws InvalidStateException {
        try {
            Scanner scanner = new Scanner(file);
            scanner.useDelimiter(";");
            String s;
            while (scanner.hasNext()) {
                s = scanner.next();
                if (Pattern.matches("[1-4]{1},[\\w\s]{1,},[1-4]{1}", s))
                    crewMembers.add(new CrewMember(scanner.next()));
                else
                    throw new InvalidStateException();
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            logger.error(e.getMessage());
        }

    }
}
