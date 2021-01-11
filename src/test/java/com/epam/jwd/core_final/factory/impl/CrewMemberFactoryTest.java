package com.epam.jwd.core_final.factory.impl;

import com.epam.jwd.core_final.domain.CrewMember;
import com.epam.jwd.core_final.domain.Rank;
import com.epam.jwd.core_final.domain.Role;
import com.epam.jwd.core_final.exception.UnknownEntityException;
import org.testng.Assert;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class CrewMemberFactoryTest extends Assert {
    Role role = Role.COMMANDER;
    String name = "Spok";
    Rank rank = Rank.CAPTAIN;
    CrewMemberFactory factory = CrewMemberFactory.INSTANCE;
    CrewMember crewMemberAct;
    CrewMember crewMemberExp;

    @Test
    public void testCreate() {
        try {
            crewMemberAct = new CrewMember("4,Spok,4");
            crewMemberExp = factory.create(role, name, rank);
            assertEquals(crewMemberAct.getRole(), crewMemberExp.getRole());
            assertEquals(crewMemberAct.getName(), crewMemberExp.getName());
            assertEquals(crewMemberAct.getRank(), crewMemberExp.getRank());

            crewMemberAct = new CrewMember("4,Spok,4");
            crewMemberExp = factory.create(role, name, rank, 45);
            assertEquals(crewMemberAct.getRole(), crewMemberExp.getRole());
            assertEquals(crewMemberAct.getName(), crewMemberExp.getName());
            assertEquals(crewMemberAct.getRank(), crewMemberExp.getRank());

            crewMemberAct = new CrewMember(role, name, rank);
            crewMemberExp = factory.create("4,Spok,4");
            assertEquals(crewMemberAct.getRole(), crewMemberExp.getRole());
            assertEquals(crewMemberAct.getName(), crewMemberExp.getName());
            assertEquals(crewMemberAct.getRank(), crewMemberExp.getRank());

            crewMemberAct = new CrewMember(role, name, rank);
            crewMemberExp = factory.create(name);
            assertEquals(crewMemberAct.getRole(), crewMemberExp.getRole());
            assertEquals(crewMemberAct.getName(), crewMemberExp.getName());
            assertEquals(crewMemberAct.getRank(), crewMemberExp.getRank());
        } catch (UnknownEntityException e) {

        }
    }
}