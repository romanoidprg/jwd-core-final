package com.epam.jwd.core_final.criteria;

import com.epam.jwd.core_final.domain.CrewMember;
import com.epam.jwd.core_final.domain.Rank;
import com.epam.jwd.core_final.domain.Role;

/**
 * Should be a builder for {@link com.epam.jwd.core_final.domain.CrewMember} fields
 */
public class CrewMemberCriteria extends Criteria<CrewMember> {
    private Role role;
    private Rank rank;
    private boolean isReadyForNextMissions;

    private CrewMemberCriteria() {
    }

    public static Builder newBuilder() {
        return new CrewMemberCriteria().new Builder();
    }

    public class Builder extends BaseEntityBuilder<Builder> {
        private Builder() {
        }

        public Builder whereRoleIs(Role role) {
            CrewMemberCriteria.this.role = role;
            return returnBuilder();
        }

        public Builder whereRankIs(Rank rank) {
            CrewMemberCriteria.this.rank = rank;
            return returnBuilder();
        }

        public Builder readyForNextMissionsIs(boolean isReady) {
            CrewMemberCriteria.this.isReadyForNextMissions = isReady;
            return returnBuilder();
        }

        @Override
        public CrewMemberCriteria build() {
            return CrewMemberCriteria.this;
        }

        @Override
        public Builder returnBuilder() {
            return this;
        }
    }

    public Role getRole() {
        return role;
    }

    public Rank getRank() {
        return rank;
    }

    public boolean isReadyForNextMissions() {
        return isReadyForNextMissions;
    }
}
