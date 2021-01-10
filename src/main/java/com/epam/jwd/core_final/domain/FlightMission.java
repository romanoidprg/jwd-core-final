package com.epam.jwd.core_final.domain;

import java.time.LocalDate;
import java.util.List;

/**
 * Expected fields:
 * <p>
 * missions name {@link String}
 * start date {@link java.time.LocalDate}
 * end date {@link java.time.LocalDate}
 * distance {@link Long} - missions distance
 * assignedSpaceShift {@link Spaceship} - not defined by default
 * assignedCrew {@link java.util.List<CrewMember>} - list of missions members based on ship capacity - not defined by default
 * missionResult {@link MissionResult}
 */
public class FlightMission extends AbstractBaseEntity {
    // todo
    private static Long counter = 1L;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final Long distance;
    private final Spaceship assignedSpaceSheep;
    private final List<CrewMember> assignedCrew;
    private MissionResult missionResult;

    public FlightMission(String name, LocalDate startDate,
                         LocalDate endDate,
                         Long distance,
                         Spaceship assignedSpaceSheep,
                         List<CrewMember> assignedCrew) {
        this.id = counter++;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.distance = distance;
        this.assignedSpaceSheep = assignedSpaceSheep;
        this.assignedCrew = assignedCrew;
        this.missionResult = MissionResult.PLANNED;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public Long getDistance() {
        return distance;
    }

    public Spaceship getAssignedSpaceShip() {
        return assignedSpaceSheep;
    }

    public List<CrewMember> getAssignedCrew() {
        return assignedCrew;
    }

    public MissionResult getMissionResult() {
        return missionResult;
    }

    public void setMissionResult(MissionResult missionResult) {
        this.missionResult = missionResult;
    }

    @Override
    public String toString() {
        return id + " " + name + " " + startDate + " " + endDate + " "
                + distance + " " + assignedSpaceSheep + " " + assignedCrew + " " + missionResult;
    }
}
