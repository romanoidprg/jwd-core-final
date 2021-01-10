package com.epam.jwd.core_final.criteria;

import com.epam.jwd.core_final.domain.*;

import java.time.LocalDate;
import java.util.List;

/**
 * Should be a builder for {@link com.epam.jwd.core_final.domain.FlightMission} fields
 */
public class FlightMissionCriteria extends Criteria<FlightMission> {
    private LocalDate startDate;
    private LocalDate endDate;
    private Long distance;
    private Spaceship assignedSpaceSheep;
    private List<CrewMember> assignedCrew;
    private MissionResult missionResult;

    private FlightMissionCriteria() {
    }

    public static Builder newBuilder() {
        return new FlightMissionCriteria().new Builder();
    }

    public class Builder extends BaseEntityBuilder {
        private Builder() {
        }

        public Builder whereStartDateIs(LocalDate date) {
            FlightMissionCriteria.this.startDate = date;
            return this;
        }

        public Builder whereEndDateIs(LocalDate date) {
            FlightMissionCriteria.this.endDate = date;
            return this;
        }

        public Builder whereDistanceIs(Long distance) {
            FlightMissionCriteria.this.distance = distance;
            return this;
        }

        public Builder whereAssignedSpaceSheepIs(Spaceship spaceSheep) {
            FlightMissionCriteria.this.assignedSpaceSheep = spaceSheep;
            return this;
        }

        public Builder whereAssignedCrewIs(List<CrewMember> crew) {
            FlightMissionCriteria.this.assignedCrew = crew;
            return this;
        }

        public Builder whereMissionResultIs(MissionResult missionResult) {
            FlightMissionCriteria.this.missionResult = missionResult;
            return this;
        }

        @Override
        public FlightMissionCriteria build() {
            return FlightMissionCriteria.this;
        }
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

    public Spaceship getAssignedSpaceSheep() {
        return assignedSpaceSheep;
    }

    public List<CrewMember> getAssignedCrew() {
        return assignedCrew;
    }

    public MissionResult getMissionResult() {
        return missionResult;
    }
}
