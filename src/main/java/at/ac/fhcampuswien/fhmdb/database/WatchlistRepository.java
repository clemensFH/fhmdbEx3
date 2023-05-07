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
            dao.create(movieToWatchlist(apiMovie));  // ? ToDO optional: überprüfen, ob User dem entspricht, was man erwartet
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void removeFromWatchlist(WatchlistMovieEntity watchlistMovie) throws SQLException {
        dao.delete(watchlistMovie);
    }
    public List<WatchlistMovieEntity> getAll() throws DatabaseException { // read all movies
        try {
            return dao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private WatchlistMovieEntity movieToWatchlist(Movie apiMovie) { // ToDo: Genres
        try {
            return new WatchlistMovieEntity(apiMovie.getId(), apiMovie.getTitle(), apiMovie.getDescription(), apiMovie.getGenres(), apiMovie.getReleaseYear(), apiMovie.getImgUrl(), apiMovie.getLengthInMinutes(), apiMovie.getRating());
        } catch (DatabaseException e) {
            e.printStackTrace();
        }
        return null;
    }
}

