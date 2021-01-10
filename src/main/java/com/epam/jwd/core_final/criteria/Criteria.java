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

    protected abstract class BaseEntityBuilder {
        public <E extends BaseEntityBuilder> E whereIdIs(Long id) {
            Criteria.this.id = id;
            return (E) this;
        }

        public <E extends BaseEntityBuilder> E whereNameIs(String name) {
            Criteria.this.name = name;
            return (E) this;
        }

        public abstract Criteria<T> build();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
