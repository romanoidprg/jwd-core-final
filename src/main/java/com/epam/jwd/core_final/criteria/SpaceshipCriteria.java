package com.epam.jwd.core_final.criteria;

import com.epam.jwd.core_final.domain.Role;
import com.epam.jwd.core_final.domain.Spaceship;

import java.util.Map;

/**
 * Should be a builder for {@link Spaceship} fields
 */
public class SpaceshipCriteria extends Criteria<Spaceship> {
    private Map<Role, Short> crew;
    private Long flightDistance;
    private Boolean isReadyForNextMissions;

    private SpaceshipCriteria() {
    }

    public static SpaceshipCriteria.Builder newBuilder() {
        return new SpaceshipCriteria().new Builder();
    }

    public class Builder extends BaseEntityBuilder<Builder> {
        private Builder() {
        }

        public Builder whereCrewIs(Map<Role, Short> crew) {
            SpaceshipCriteria.this.crew = crew;
            return returnBuilder();
        }

        public Builder whereFlightDistanceIs(Long flightDistance) {
            SpaceshipCriteria.this.flightDistance = flightDistance;
            return returnBuilder();
        }

        public Builder readyForNextMissionsIs(boolean isReady) {
            SpaceshipCriteria.this.isReadyForNextMissions = isReady;
            return returnBuilder();
        }

        @Override
        public Builder returnBuilder() {
            return this;
        }

        @Override
        public SpaceshipCriteria build() {
            return SpaceshipCriteria.this;
        }
    }

    public Map<Role, Short> getCrew() {
        return crew;
    }

    public Long getFlightDistance() {
        return flightDistance;
    }

    public Boolean getReadyForNextMissions() {
        return isReadyForNextMissions;
    }
}
