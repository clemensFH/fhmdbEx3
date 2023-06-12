package at.ac.fhcampuswien.fhmdb.database;

import at.ac.fhcampuswien.fhmdb.Observable;
import at.ac.fhcampuswien.fhmdb.Observer;
import at.ac.fhcampuswien.fhmdb.exceptions.DatabaseException;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class WatchlistRepository implements Observable {
    private List<Observer> observers; // List to hold the registered observers
    Dao<WatchlistMovieEntity, Long> dao;
    private static WatchlistRepository instance;

    private WatchlistRepository() throws DatabaseException {

        this.dao = DatabaseManager.getInstance().getWatchlistDao();
        observers = new ArrayList<>();

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

            if (!getAll().stream().map(entity -> entity.getApiId()).anyMatch(id -> id.equals(apiMovie.getId()))) {

                dao.create(movieToWatchlist(apiMovie));
                notifyObservers("Movie successfully added to watchlist"); // Notify the observers about the event
            } else {
                notifyObservers("Movie already on watchlist"); // Notify the observers about the event
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error while adding movie to watchlist", e);
        }
    }

    public void removeFromWatchlist(WatchlistMovieEntity watchlistMovie) throws DatabaseException {
        try {
            dao.delete(watchlistMovie);
            //notifyObservers("Movie successfully removed from watchlist"); // Notify the observers about the event
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

    // Implementing Observable interface methods:

    // Adds an observer to the list of registered observers.
    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    // Removes an observer from the list of registered observers.
    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    // Notifies all the registered observers by calling their update method.
    @Override
    public void notifyObservers(String message) {
        for (Observer observer : observers) {
            observer.update(message);
        }
    }
}
