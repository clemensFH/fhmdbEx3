package at.ac.fhcampuswien.fhmdb.exceptions;

import java.sql.SQLException;

public class DatabaseException extends Exception {
    public DatabaseException(String message, SQLException e) {  // SQL ignorieren. mein Fehler.
        super(message);}
}
