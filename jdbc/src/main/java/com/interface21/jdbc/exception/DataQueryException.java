package com.interface21.jdbc.exception;

import java.sql.SQLException;

public class DataQueryException extends RuntimeException {

    public DataQueryException(String message) {
        super(message);
    }

    public DataQueryException(String message, Exception cause) {
        super(message, cause);
    }
}
