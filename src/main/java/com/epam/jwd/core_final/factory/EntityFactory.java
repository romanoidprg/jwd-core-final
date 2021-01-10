package com.epam.jwd.core_final.factory;

import com.epam.jwd.core_final.domain.BaseEntity;
import com.epam.jwd.core_final.exception.UnknownEntityException;

public interface EntityFactory<T extends BaseEntity> {

    T create(Object... args) throws UnknownEntityException;
}
