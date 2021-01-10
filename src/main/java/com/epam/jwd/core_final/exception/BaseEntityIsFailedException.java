package com.epam.jwd.core_final.exception;

import com.epam.jwd.core_final.domain.BaseEntity;

public class BaseEntityIsFailedException extends BaseEntityException {

    public <T extends BaseEntity> BaseEntityIsFailedException(T baseEntity) {
        super(baseEntity);
    }

    @Override
    public String getMessage() {
        return super.getMessage() + " can't be assigned with mission because it was failed.";
    }
}
