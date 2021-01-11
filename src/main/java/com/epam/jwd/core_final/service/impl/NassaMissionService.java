package com.epam.jwd.core_final.service.impl;

import com.epam.jwd.core_final.context.impl.NassaContext;
import com.epam.jwd.core_final.criteria.Criteria;
import com.epam.jwd.core_final.criteria.FlightMissionCriteria;
import com.epam.jwd.core_final.domain.FlightMission;
import com.epam.jwd.core_final.exception.BaseEntityAlreadyExistException;
import com.epam.jwd.core_final.service.MissionService;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class NassaMissionService implements MissionService {

    private static NassaMissionService instance;

    public static synchronized NassaMissionService getInstance(NassaContext nassaContext) {
        if (instance == null) {
            instance = new NassaMissionService(nassaContext);
        }
        return instance;
    }

    List<FlightMission> flightMissions;

    public NassaMissionService(NassaContext nassaContext) {
        this.flightMissions = (List<FlightMission>) nassaContext.retrieveBaseEntityList(FlightMission.class);
    }


    @Override
    public List<FlightMission> findAllMissions() {
        return flightMissions;
    }

    @Override
    public List<FlightMission> findAllMissionsByCriteria(Criteria<? extends FlightMission> criteria) {
        FlightMissionCriteria flightMissionCriteria = (FlightMissionCriteria) criteria;
        return flightMissions.stream()
                .filter(c -> flightMissionCriteria.getId() == null
                        || c.getId() == flightMissionCriteria.getId())
                .filter(c -> flightMissionCriteria.getStartDate() == null
                        || c.getStartDate() == flightMissionCriteria.getStartDate())
                .filter(c -> flightMissionCriteria.getEndDate() == null
                        || c.getEndDate() == flightMissionCriteria.getEndDate())
                .filter(c -> flightMissionCriteria.getName() == null
                        || c.getName() == flightMissionCriteria.getName())
                .filter(c -> flightMissionCriteria.getAssignedCrew() == null
                        || c.getAssignedCrew() == flightMissionCriteria.getAssignedCrew())
                .filter(c -> flightMissionCriteria.getAssignedSpaceSheep() == null
                        || c.getAssignedSpaceShip() == flightMissionCriteria.getAssignedSpaceSheep())
                .filter(c -> flightMissionCriteria.getDistance() == null
                        || c.getDistance() == flightMissionCriteria.getDistance())
                .filter(c -> flightMissionCriteria.getMissionResult() == null
                        || c.getMissionResult() == flightMissionCriteria.getMissionResult())
                .collect(Collectors.toList());
    }

    @Override
    public Optional<FlightMission> findMissionByCriteria(Criteria<? extends FlightMission> criteria) {
        return findAllMissionsByCriteria(criteria).stream().findAny();
    }

    @Override
    public FlightMission updateMissionDetails(FlightMission flightMission) {
        return flightMission;
    }

    @Override
    public FlightMission createMission(FlightMission flightMission) {
        if (flightMissions.stream().anyMatch(s -> s.getName().equals(flightMission.getName())))
            throw new BaseEntityAlreadyExistException(flightMission);
        else {
            flightMissions.add(flightMission);
            return flightMission;
        }
    }
}
