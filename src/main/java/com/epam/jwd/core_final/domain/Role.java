package com.epam.jwd.core_final.domain;

import com.epam.jwd.core_final.exception.UnknownEntityException;

public enum Role implements BaseEntity {
    MISSION_SPECIALIST(1L),
    FLIGHT_ENGINEER(2L),
    PILOT(3L),
    COMMANDER(4L);

    private final Long id;

    Role(Long id) {
        this.id = id;
    }

    @Override
    public Long getId() {
        return id;
    }

    /**
     * todo via java.lang.enum methods!
     */
    @Override
    public String getName() {
        return this.getName();
    }

    /**
     * todo via java.lang.enum methods!
     *
     * @throws UnknownEntityException if such id does not exist
     */
    public static Role resolveRoleById(int id) throws UnknownEntityException {
        if (id>0 && id<5) {
            switch (id) {
                case 1:
                    return Enum.valueOf(Role.class, "MISSION_SPECIALIST");
                break;
                case 2:
                    return Enum.valueOf(Role.class, "FLIGHT_ENGINEER");
                break;
                case 3:
                    return Enum.valueOf(Role.class, "PILOT");
                break;
                case 4:
                    return Enum.valueOf(Role.class, "COMMANDER");
                break;
            }
        } else {
            throw new UnknownEntityException("Such id doesn't exist");
            return Enum.valueOf(Role.class, "MISSION_SPECIALIST");
        }
    }
}
