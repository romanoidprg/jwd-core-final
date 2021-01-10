package com.epam.jwd.core_final.exception;

import com.epam.jwd.core_final.domain.BaseEntity;

public class BaseEntityIsAssignedException extends BaseEntityException {

    public <T extends BaseEntity> BaseEntityIsAssignedException(T baseEntity) {
        super(baseEntity);
    }

    @Override
    public String getMessage() {
        return super.getMessage() + " is already assigned with another mission.";
    }
}
