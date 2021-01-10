package com.epam.jwd.core_final.exception;

import com.epam.jwd.core_final.domain.BaseEntity;

public class BaseEntityAlreadyExistException extends BaseEntityException {

    public <T extends BaseEntity> BaseEntityAlreadyExistException(T baseEntity) {
        super(baseEntity);
    }

    @Override
    public String getMessage() {
        return super.getMessage() + " is already exist and can't be duplicate.";
    }
}
