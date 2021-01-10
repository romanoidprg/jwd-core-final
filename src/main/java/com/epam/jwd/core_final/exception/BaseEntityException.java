package com.epam.jwd.core_final.exception;

import com.epam.jwd.core_final.domain.BaseEntity;

public class BaseEntityException extends RuntimeException {

    private final String name;
    private final String entity;

    public BaseEntityException(BaseEntity baseEntity) {
        super();
        name = baseEntity.getName();
        entity = baseEntity.getClass().getSimpleName();
    }

    @Override
    public String getMessage() {
        return entity + " " + name;
    }
}
