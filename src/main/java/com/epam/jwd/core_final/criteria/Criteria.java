package com.epam.jwd.core_final.criteria;

import com.epam.jwd.core_final.domain.BaseEntity;

/**
 * Should be a builder for {@link BaseEntity} fields
 */
public abstract class Criteria<T extends BaseEntity> {
    private Long id;
    private String name;

    protected Criteria() {
    }

    protected abstract class BaseEntityBuilder<E extends BaseEntityBuilder<E>> {
        public E whereIdIs(Long id) {
            Criteria.this.id = id;
            return returnBuilder();
        }

        public E whereNameIs(String name) {
            Criteria.this.name = name;
            return returnBuilder();
        }

        public abstract Criteria<T> build();
        public abstract E returnBuilder();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
