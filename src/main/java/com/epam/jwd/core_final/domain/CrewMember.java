package com.epam.jwd.core_final.domain;

import com.epam.jwd.core_final.exception.UnknownEntityException;
import java.util.regex.Pattern;

/**
 * Expected fields:
 * <p>
 * role {@link Role} - member role
 * rank {@link Rank} - member rank
 * isReadyForNextMissions {@link Boolean} - true by default. Set to false, after first failed mission
 */
public class CrewMember extends AbstractBaseEntity {
    // todo
    private static Long counter = 1L;
    private Role role;
    private Rank rank;
    private boolean isReadyForNextMissions;

    public CrewMember(Role role, String name, Rank rank) {
        this.id = counter++;
        this.role = role;
        this.rank = rank;
        isReadyForNextMissions = true;
        this.name = name;
    }

    public CrewMember(String member) {
        if (Pattern.matches("[1-4]{1},[\\w -]{1,},[1-4]{1}", member)) {
            this.id = counter++;
            this.role = Role.resolveRoleById(Integer.parseInt(String.valueOf(member.charAt(0))));
            this.rank = Rank.resolveRankById(Integer.parseInt(String.valueOf(member.charAt(member.length() - 1))));
            isReadyForNextMissions = true;
            this.name = (String) member.subSequence(2, member.length() - 2);
        } else
            throw new UnknownEntityException(member);
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

    public void setReadyForNextMissions(boolean readyForNextMissions) {
        isReadyForNextMissions = readyForNextMissions;
    }

    @Override
    public String toString() {
        return id + " " + role + " " + name + " " + rank;
    }
}
