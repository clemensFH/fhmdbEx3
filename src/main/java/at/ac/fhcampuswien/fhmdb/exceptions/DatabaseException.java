package at.ac.fhcampuswien.fhmdb.exceptions;

import java.sql.SQLException;

public class DatabaseException extends Exception{
    public DatabaseException(String message) {
        super(message);
    }

    public DatabaseException(String databaseErrorMessage, SQLException e) {
    }
}
