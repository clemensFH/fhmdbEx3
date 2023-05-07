package at.ac.fhcampuswien.fhmdb.ui;

import at.ac.fhcampuswien.fhmdb.database.WatchlistMovieEntity;
import at.ac.fhcampuswien.fhmdb.database.WatchlistRepository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class WatchlistViewController {  //to read contacts
    public void initialize() {
        WatchlistRepository repo;
        System.out.println("WatchlistController initialized");

        repo = new WatchlistRepository();
        List< WatchlistMovieEntity> watchlist = new ArrayList<>(); // want to have all watchlist-movies when initialized
        try {
            watchlist = repo.getAll();
        } catch (SQLException e) {
            System.out.println("Cannot load Watchlist"); // ToDO: ? richtige Fehler
        }

        for (WatchlistMovieEntity item : watchlist) { // ToDo: Kontakte sollten auf Konsole ausgegeben werden & noch kein Watchlist Button
            System.out.println(item);
        }

        // ToDo: Kontakte in Liste ausgeben
    }
}
