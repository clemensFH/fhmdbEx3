package at.ac.fhcampuswien.fhmdb.database;

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


    private static DatabaseManager instance; // II 4. Singleton Pattern, allowing only 1 object to be instantiated at a time
    private DatabaseManager() {  // II 4. constructor
        try { //? besser propagieren, hier drin catchen n. sinnvoll
            createConnectionSource(); // 1. create connection
            dao = DaoManager.createDao(connectionSource, WatchlistMovieEntity.class); // 2. fill up dao 15
            createTables(); // 3. create table
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static DatabaseManager getInstance() {  // other class calls Database
        if (instance == null) { // if there is no instance yet, creates a new one
            instance = new DatabaseManager();
        }
        return instance;
    }
    public Dao<WatchlistMovieEntity, Long> getDao() {
        return this.dao;
    }

    public static void createTables() throws SQLException { // II 4.3. if not existing yet, create table einmalig
        TableUtils.createTableIfNotExists(connectionSource, WatchlistMovieEntity.class);
    }
    private static void createConnectionSource() throws SQLException {  // 2b.
        connectionSource = new JdbcConnectionSource(DB_URL, user, password);
    }

    public static JdbcConnectionSource getConnectionSource() {
        return connectionSource;
    }

    public Dao<WatchlistMovieEntity, Long> getWatchlistDao() {
        return dao;
    }

    /*
    public void testDB() throws SQLException { // III test entry, before final integration
        WatchlistMovieEntity movie = new WatchlistMovieEntity("1234", "The Thing", "bad movie", "ACTION", 1943, "http", 96, 0.5);
        dao.create(movie);
    }
     */
}
