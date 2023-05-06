package at.ac.fhcampuswien.fhmdb.database;

import at.ac.fhcampuswien.fhmdb.models.Movie;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

public class WatchlistRepository {
    Dao<WatchlistMovieEntity,Long> dao;

    public WatchlistRepository () {
        this.dao = DatabaseManager.getInstance().getDao();
    }

    public void addToWatchlist(Movie apiMovie) throws SQLException {
        dao.create(movieToWatchlist(apiMovie));  // ? ToDO optional: überprüfen, ob User dem entspricht, was man erwartet
    }
    public void removeFromWatchlist(Movie watchlistMovie) throws SQLException {
        dao.delete(movieToWatchlist(watchlistMovie));
    }
    public List<WatchlistMovieEntity> readAllMovies() throws SQLException { // read all movies
        return dao.queryForAll();
    }

    private WatchlistMovieEntity movieToWatchlist(Movie apiMovie) { // ToDo: Genres
        return new WatchlistMovieEntity(apiMovie.getId(), apiMovie.getTitle(), apiMovie.getDescription(), WatchlistMovieEntity.genresToString(apiMovie.getGenres()), apiMovie.getReleaseYear(), apiMovie.getImgUrl(), apiMovie.getLengthInMinutes(), apiMovie.getRating());
    }
}

