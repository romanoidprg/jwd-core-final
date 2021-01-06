package com.epam.jwd.core_final.domain;

/**
 * Expected fields:
 * <p>
 * role {@link Role} - member role
 * rank {@link Rank} - member rank
 * isReadyForNextMissions {@link Boolean} - true by default. Set to false, after first failed mission
 */
public class CrewMember extends AbstractBaseEntity {
    // todo
    private Role role;
    private Rank rank;
    private boolean isReadyForNextMissions;

    public CrewMember(Role role, String name, Rank rank) {
        this.role = role;
        this.rank = rank;
        isReadyForNextMissions = true;
        this.name = name;
    }
    public CrewMember(String member) {
        this.role = Role.resolveRoleById(Integer.parseInt(String.valueOf(member.charAt(0))));
        this.rank = Rank.resolveRankById(Integer.parseInt(String.valueOf(member.charAt(member.length()-1))));
        isReadyForNextMissions = true;
        this.name = (String) member.subSequence(2, member.length()-3);
    }



    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Rank getRank() {
        return rank;
    }

    public void setRank(Rank rank) {
        this.rank = rank;
    }

    public boolean isReadyForNextMissions() {
        return isReadyForNextMissions;
    }

    public void setReadyForNextMissions(boolean readyForNextMissions) {
        isReadyForNextMissions = readyForNextMissions;
    }
}
