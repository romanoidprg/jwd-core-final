package com.epam.jwd.core_final.exception;

import java.io.File;

public class InvalidStateException extends Exception {
    // todo
    String msg;
    public InvalidStateException(File file){
        super();
        msg = file.getName();
    }

    @Override
    public String getMessage() {
        return "Invalid state in file " + msg;
    }
}
