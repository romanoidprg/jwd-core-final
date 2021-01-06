package com.epam.jwd.core_final.criteria;

import com.epam.jwd.core_final.domain.CrewMember;
import com.epam.jwd.core_final.domain.Rank;
import com.epam.jwd.core_final.domain.Role;

/**
 * Should be a builder for {@link com.epam.jwd.core_final.domain.CrewMember} fields
 */
public class CrewMemberCriteria extends Criteria<CrewMember> {
    private final Role role;
    private final Rank rank;
    private final boolean isReadyForNextMissions;

    private CrewMemberCriteria(Builder builder){
        role = builder.role;
        rank = builder.rank;
        isReadyForNextMissions = builder.isReadyForNextMissions;
    }

    public static class Builder{
        private Role role;
        private Rank rank;
        private boolean isReadyForNextMissions;

        public Builder whereRoleIs(Role role) {
            this.role = role;
            return this;
        }
        public Builder whereRankIs(Rank rank) {
            this.rank = rank;
            return this;
        }
        public Builder readyForNextMissionsIs(boolean isReady) {
            this.isReadyForNextMissions = isReady;
            return this;
        }
        public CrewMemberCriteria build(){
            return new CrewMemberCriteria(this);
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
