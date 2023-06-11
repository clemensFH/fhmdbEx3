package at.ac.fhcampuswien.fhmdb.database;

import at.ac.fhcampuswien.fhmdb.exceptions.DatabaseException;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

public class DatabaseManager {  // class connects ORMLite & DB
    public static final String DB_URL = "jdbc:h2:file: ./db/watchlistdb"; // I 1. connection to database
    public static final String user = "user";
    public static final String password = "pass";
    public static JdbcConnectionSource connectionSource; // I 2a. DAO configuration (connection source)
    private Dao<WatchlistMovieEntity, Long> dao; // I 3. create DAO for accessing database
    private static final String DATABASE_ERROR_MESSAGE = "DatabaseException: Error while accessing database. Please try again later.\n";


    private static DatabaseManager instance; // II 4. Singleton Pattern, allowing only 1 object to be instantiated at a time
    private DatabaseManager() throws DatabaseException {  // II 4. constructor
        try {
            createConnectionSource(); // 1. create connection
            dao = DaoManager.createDao(connectionSource, WatchlistMovieEntity.class); // 2. fill up dao 15
            createTables(); // 3. create table
        } catch (SQLException e) {
            throw new DatabaseException(DATABASE_ERROR_MESSAGE, e);

        }
    }

    public static DatabaseManager getInstance() throws DatabaseException {  // other class calls Database
        if (instance == null) { // if there is no instance yet, creates a new one
            instance = new DatabaseManager() ;
        }
        return instance;
    }
    public Dao<WatchlistMovieEntity, Long> getWatchlistDao() {
        return this.dao;
    }

    public static void createTables() throws DatabaseException {
        try {
            TableUtils.createTableIfNotExists(connectionSource, WatchlistMovieEntity.class);
        } catch (SQLException e) {
            throw new DatabaseException(DATABASE_ERROR_MESSAGE, e);
        }
    }
    private static void createConnectionSource() throws DatabaseException {
        try {
            connectionSource = new JdbcConnectionSource(DB_URL, user, password);
        } catch (SQLException e) {
            throw new DatabaseException("Error occurred while trying to creating a Connection Source.", e);
        }

    }
    public static JdbcConnectionSource getConnectionSource() {
        return connectionSource;
    }
}
