package com.epam.jwd.core_final.domain;

import com.epam.jwd.core_final.exception.UnknownEntityException;

public enum Rank implements BaseEntity {
    TRAINEE(1L),
    SECOND_OFFICER(2L),
    FIRST_OFFICER(3L),
    CAPTAIN(4L);

    private final Long id;

    Rank(Long id) {
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
        return this.toString();
    }

    /**
     * todo via java.lang.enum methods!
     *
     * @throws UnknownEntityException if such id does not exist
     */
    public static Rank resolveRankById(int id) {
        Rank rank =  Enum.valueOf(Rank.class, "TRAINEE");
        if (id>0 && id<5) {
            switch (id) {
                case 1:
                    rank = Enum.valueOf(Rank.class, "TRAINEE");
                    break;
                case 2:
                    rank =  Enum.valueOf(Rank.class, "SECOND_OFFICER");
                    break;
                case 3:
                    rank =  Enum.valueOf(Rank.class, "FIRST_OFFICER");
                    break;
                case 4:
                    rank =  Enum.valueOf(Rank.class, "CAPTAIN");
                    break;
            }
        } else {
            throw new UnknownEntityException("Rank with id: " + String.valueOf(id));
        }
        return  rank;    }
}
