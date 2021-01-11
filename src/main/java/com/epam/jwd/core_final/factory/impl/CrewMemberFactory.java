package com.epam.jwd.core_final.factory.impl;

import com.epam.jwd.core_final.domain.CrewMember;
import com.epam.jwd.core_final.domain.Rank;
import com.epam.jwd.core_final.domain.Role;
import com.epam.jwd.core_final.exception.UnknownEntityException;
import com.epam.jwd.core_final.factory.EntityFactory;

// do the same for other entities
public enum CrewMemberFactory implements EntityFactory<CrewMember> {
    INSTANCE;

    @Override
    public CrewMember create(Object... args) throws UnknownEntityException {
        CrewMember crewMember;
        if ((args.length >= 3) && (args[0] instanceof Role) && (args[1] instanceof String) && (args[2] instanceof Rank))
            crewMember = new CrewMember((Role) args[0], (String) args[1], (Rank) args[2]);
        else if ((args.length == 1) && (args[0] instanceof String))
            crewMember = new CrewMember((String) args[0]);
        else
            throw new UnknownEntityException("CrewMember", args);
        return crewMember;
    }
}
