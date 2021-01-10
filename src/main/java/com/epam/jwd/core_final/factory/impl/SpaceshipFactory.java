package com.epam.jwd.core_final.factory.impl;

import com.epam.jwd.core_final.domain.Spaceship;
import com.epam.jwd.core_final.exception.UnknownEntityException;
import com.epam.jwd.core_final.factory.EntityFactory;

// do the same for other entities
public enum SpaceshipFactory implements EntityFactory<Spaceship> {
    INSTANCE;

    @Override
    public Spaceship create(Object... args) throws UnknownEntityException {
        Spaceship spaceship;
        if ((args.length == 1) && (args[0].getClass() == String.class))
            spaceship = new Spaceship((String) args[0]);
        else
            throw new UnknownEntityException("Spaceship", args);
        return spaceship;
    }
}
