package at.ac.fhcampuswien.fhmdb.exceptions;

import java.sql.SQLException;

public class DatabaseException extends Exception {
    private SQLException sqlException;

    public DatabaseException(String message, SQLException e) {
        super(message);
        this.sqlException = e;
    }

    public SQLException getSqlException() {
        return sqlException;
    }
}
