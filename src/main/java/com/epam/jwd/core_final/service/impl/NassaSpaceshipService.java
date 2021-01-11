package com.epam.jwd.core_final.service.impl;

import com.epam.jwd.core_final.context.impl.NassaContext;
import com.epam.jwd.core_final.criteria.Criteria;
import com.epam.jwd.core_final.criteria.SpaceshipCriteria;
import com.epam.jwd.core_final.domain.CrewMember;
import com.epam.jwd.core_final.domain.FlightMission;
import com.epam.jwd.core_final.domain.Spaceship;
import com.epam.jwd.core_final.exception.BaseEntityAlreadyExistException;
import com.epam.jwd.core_final.exception.BaseEntityIsAssignedException;
import com.epam.jwd.core_final.exception.BaseEntityIsFailedException;
import com.epam.jwd.core_final.exception.UnknownEntityException;
import com.epam.jwd.core_final.service.SpaceshipService;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class NassaSpaceshipService implements SpaceshipService {

    private static NassaSpaceshipService instance;

    public static synchronized NassaSpaceshipService getInstance(NassaContext nassaContext) {
        if (instance == null) {
            instance = new NassaSpaceshipService(nassaContext);
        }
        return instance;
    }

    private final List<Spaceship> spaceships;
    private final List<CrewMember> crewMembers;
    private final List<FlightMission> flightMissions;

    public NassaSpaceshipService(NassaContext nassaContext) {
        this.crewMembers = (List<CrewMember>) nassaContext.retrieveBaseEntityList(CrewMember.class);
        this.spaceships = (List<Spaceship>) nassaContext.retrieveBaseEntityList(Spaceship.class);
        this.flightMissions = (List<FlightMission>) nassaContext.retrieveBaseEntityList(FlightMission.class);
    }

    @Override
    public List<Spaceship> findAllSpaceships() {
        return spaceships;
    }

    @Override
    public List<Spaceship> findAllSpaceshipsByCriteria(Criteria<? extends Spaceship> criteria) {
        SpaceshipCriteria spaceshipCriteria = (SpaceshipCriteria) criteria;
        return spaceships.stream()
                .filter(c -> spaceshipCriteria.getId() == null || c.getId() == spaceshipCriteria.getId())
                .filter(c -> spaceshipCriteria.getCrew() == null || c.getCrew() == spaceshipCriteria.getCrew())
                .filter(c -> spaceshipCriteria.getFlightDistance() == null || c.getFlightDistance() == spaceshipCriteria.getFlightDistance())
                .filter(c -> spaceshipCriteria.getName() == null || c.getName() == spaceshipCriteria.getName())
                .filter(c -> c.getReadyForNextMissions() == spaceshipCriteria.getReadyForNextMissions())
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Spaceship> findSpaceshipByCriteria(Criteria<? extends Spaceship> criteria) {
        return Optional.empty();
    }

    @Override
    public Spaceship updateSpaceshipDetails(Spaceship spaceship) {
        spaceship.setReadyForNextMissions(false);
        return spaceship;
    }

    @Override
    public void assignSpaceshipOnMission(Spaceship spaceship)
            throws BaseEntityIsFailedException, BaseEntityIsAssignedException, UnknownEntityException {
        if (spaceship != null) {
            if (!spaceship.getReadyForNextMissions())
                throw new BaseEntityIsFailedException(spaceship);
            else if (flightMissions.stream().anyMatch(fm ->
                    fm.getAssignedSpaceShip().getName().equals(spaceship.getName())))
                throw new BaseEntityIsAssignedException(spaceship);
        } else
            throw new UnknownEntityException("null");
    }

    @Override
    public Spaceship createSpaceship(Spaceship spaceship) throws BaseEntityAlreadyExistException {
        if (spaceships.stream().anyMatch(s -> s.getName().equals(spaceship.getName())))
            throw new BaseEntityAlreadyExistException(spaceship);
        else {
            spaceships.add(spaceship);
            return null;
        }
    }
}
