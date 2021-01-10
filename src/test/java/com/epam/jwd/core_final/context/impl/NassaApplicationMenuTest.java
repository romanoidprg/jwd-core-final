package com.epam.jwd.core_final.context.impl;

import com.epam.jwd.core_final.domain.CrewMember;
import com.epam.jwd.core_final.domain.Spaceship;
import com.epam.jwd.core_final.exception.InvalidStateException;
import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;

public class NassaApplicationMenuTest extends TestCase {

    public void testCompleteCrew() {
        NassaContext nassaContext = new NassaContext();
        try {
            nassaContext.init();
        } catch (InvalidStateException e) {
            e.printStackTrace();
        }
        NassaApplicationMenu nassaApplicationMenu = new NassaApplicationMenu(nassaContext);
        Spaceship spaceship = new Spaceship("Test;201117;{1:1,2:1,3:1,4:1}");
        List<CrewMember> crewMembers = new ArrayList<>();
        List<CrewMember> testCrewMembers = new ArrayList<>();

        crewMembers.add(new CrewMember("1,Zoe Day,1"));
        crewMembers.add(new CrewMember("2,Jkjsdd,1"));
        crewMembers.add(new CrewMember("3,KJNJ,1"));
        crewMembers.add(new CrewMember("4,JLLlds,1"));
        testCrewMembers = nassaApplicationMenu.completeCrew(spaceship);

        assertEquals(crewMembers.size(), testCrewMembers.size());

        spaceship = new Spaceship("Test;201117;{1:1,2:1,3:2,4:1}");
        testCrewMembers = nassaApplicationMenu.completeCrew(spaceship);
        crewMembers.add(new CrewMember("3,gjjtt,1"));

        assertEquals(crewMembers.size(), testCrewMembers.size());

    }
}