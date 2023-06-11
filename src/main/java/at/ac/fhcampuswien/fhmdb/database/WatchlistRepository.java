package at.ac.fhcampuswien.fhmdb.database;

import at.ac.fhcampuswien.fhmdb.exceptions.DatabaseException;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

//Layer wraps Dao (abstrahiert)
public class WatchlistRepository {
    Dao<WatchlistMovieEntity,Long> dao;
    private static WatchlistRepository instance; //1a. Singleton Pattern: private static instance

    private WatchlistRepository () throws DatabaseException {   //1b. constructor: private
        this.dao = DatabaseManager.getInstance().getWatchlistDao();
    }

    public static WatchlistRepository getInstance() {   //1c.
        if (instance == null) {
            try {
                instance = new WatchlistRepository();
            } catch (DatabaseException e) {
                throw new RuntimeException(e);
            }
        }
        return instance;
    }

    public void addToWatchlist(Movie apiMovie) throws DatabaseException {
        try {
            //ToDo: Ressourcenschonendere Methode möglich
            if(!getAll().stream().map(entity -> entity.getApiId()).anyMatch(id -> id.equals(apiMovie.getId()))){
                dao.create(movieToWatchlist(apiMovie));
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error while adding movie to watchlist", e);
        }
    }

    public void removeFromWatchlist(WatchlistMovieEntity watchlistMovie) throws DatabaseException {
        try {
            dao.delete(watchlistMovie);
        } catch (SQLException e) {
            throw new DatabaseException("Error removing movie from watchlist", e);
        }
    }

    public List<WatchlistMovieEntity> getAll() throws DatabaseException { // read all movies (im Watchlist Screen ausrufen & movies auslesen)
        try {
            return dao.queryForAll();
        } catch (SQLException e) {
            throw new DatabaseException("Error getting all movies from watchlist", e);
        }
    }

    private WatchlistMovieEntity movieToWatchlist(Movie apiMovie) {
        return new WatchlistMovieEntity(apiMovie.getId(), apiMovie.getTitle(), apiMovie.getDescription(), apiMovie.getGenres(), apiMovie.getReleaseYear(), apiMovie.getImgUrl(), apiMovie.getLengthInMinutes(), apiMovie.getRating());
    }
}

