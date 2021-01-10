package com.epam.jwd.core_final.factory.impl;

import com.epam.jwd.core_final.domain.CrewMember;
import com.epam.jwd.core_final.domain.FlightMission;
import com.epam.jwd.core_final.domain.Spaceship;
import com.epam.jwd.core_final.exception.UnknownEntityException;
import com.epam.jwd.core_final.factory.EntityFactory;

import java.time.LocalDate;
import java.util.List;

// do the same for other entities
public enum FlightMissionFactory implements EntityFactory<FlightMission> {
    INSTANCE;

    @Override
    public FlightMission create(Object... args) throws UnknownEntityException {
        FlightMission flightMission;
        if ((args.length == 6)
                && (args[0] instanceof String)
                && (args[1] instanceof LocalDate)
                && (args[2] instanceof LocalDate)
                && (args[3] instanceof Long)
                && (args[4] instanceof Spaceship)
                && (args[5] instanceof List)) {
            flightMission = new FlightMission(
                    (String) args[0], (LocalDate) args[1], (LocalDate) args[2],
                    (Long) args[3], (Spaceship) args[4], (List<CrewMember>) args[5]);
        } else
            throw new UnknownEntityException("FlightMission", args);
        return flightMission;
    }
}
