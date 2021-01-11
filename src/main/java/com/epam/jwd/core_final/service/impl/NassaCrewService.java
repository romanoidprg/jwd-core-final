package com.epam.jwd.core_final.service.impl;

import com.epam.jwd.core_final.context.impl.NassaContext;
import com.epam.jwd.core_final.criteria.CrewMemberCriteria;
import com.epam.jwd.core_final.criteria.Criteria;
import com.epam.jwd.core_final.domain.CrewMember;
import com.epam.jwd.core_final.domain.FlightMission;
import com.epam.jwd.core_final.exception.BaseEntityAlreadyExistException;
import com.epam.jwd.core_final.exception.BaseEntityIsAssignedException;
import com.epam.jwd.core_final.exception.BaseEntityIsFailedException;
import com.epam.jwd.core_final.exception.UnknownEntityException;
import com.epam.jwd.core_final.service.CrewService;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class NassaCrewService implements CrewService {

    private static NassaCrewService instance;

    public static synchronized NassaCrewService getInstance(NassaContext nassaContext) {
        if (instance == null) {
            instance = new NassaCrewService(nassaContext);
        }
        return instance;
    }

    private final List<CrewMember> crewMembers;
    private final List<FlightMission> flightMissions;

    public NassaCrewService(NassaContext nassaContext) {
        this.crewMembers = (List<CrewMember>) nassaContext.retrieveBaseEntityList(CrewMember.class);
        this.flightMissions = (List<FlightMission>) nassaContext.retrieveBaseEntityList(FlightMission.class);
    }

    @Override
    public List<CrewMember> findAllCrewMembers() {
        return crewMembers;
    }

    @Override
    public List<CrewMember> findAllCrewMembersByCriteria(Criteria<? extends CrewMember> criteria) {
        CrewMemberCriteria crewMemberCriteria = (CrewMemberCriteria) criteria;
        return crewMembers.stream()
                .filter(c -> crewMemberCriteria.getId() == null || c.getId() == crewMemberCriteria.getId())
                .filter(c -> crewMemberCriteria.getRank() == null || c.getRank() == crewMemberCriteria.getRank())
                .filter(c -> crewMemberCriteria.getRole() == null || c.getRole() == crewMemberCriteria.getRole())
                .filter(c -> crewMemberCriteria.getName() == null || c.getName() == crewMemberCriteria.getName())
                .filter(c -> c.isReadyForNextMissions() == crewMemberCriteria.isReadyForNextMissions())
                .collect(Collectors.toList());
    }

    @Override
    public Optional<CrewMember> findCrewMemberByCriteria(Criteria<? extends CrewMember> criteria) {
        return Optional.empty();
    }

    @Override
    public CrewMember updateCrewMemberDetails(CrewMember crewMember) {
        crewMember.setReadyForNextMissions(false);
        return crewMember;
    }

    @Override
    public void assignCrewMemberOnMission(CrewMember crewMember)
            throws BaseEntityIsFailedException, BaseEntityIsAssignedException, UnknownEntityException {
        if (crewMember != null) {
            if (!crewMember.isReadyForNextMissions())
                throw new BaseEntityIsFailedException(crewMember);
            else if (flightMissions.stream().anyMatch(fm -> fm.getAssignedCrew().stream()
                    .anyMatch(cm -> cm.getName().equals(crewMember.getName()))))
                throw new BaseEntityIsAssignedException(crewMember);
        } else
            throw new UnknownEntityException("null");
    }

    @Override
    public CrewMember createCrewMember(CrewMember crewMember) throws BaseEntityAlreadyExistException {
        if (crewMembers.stream().anyMatch(cm -> cm.getName().equals(crewMember.getName())))
            throw new BaseEntityAlreadyExistException(crewMember);
        else {
            crewMembers.add(crewMember);
            return crewMember;
        }
    }
}
