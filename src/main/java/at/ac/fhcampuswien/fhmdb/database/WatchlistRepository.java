package at.ac.fhcampuswien.fhmdb.database;

import at.ac.fhcampuswien.fhmdb.exceptions.DatabaseException;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

public class WatchlistRepository {
    Dao<WatchlistMovieEntity,Long> dao;

    public WatchlistRepository () throws DatabaseException {
        this.dao = DatabaseManager.getInstance().getDao();
    }

    public void addToWatchlist(Movie apiMovie) throws DatabaseException {
        try {
            dao.create(movieToWatchlist(apiMovie));
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

    public List<WatchlistMovieEntity> getAll() throws DatabaseException { // read all movies
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

