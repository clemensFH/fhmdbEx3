package at.ac.fhcampuswien.fhmdb;

import at.ac.fhcampuswien.fhmdb.api.MovieAPI;
import at.ac.fhcampuswien.fhmdb.database.WatchlistRepository;
import at.ac.fhcampuswien.fhmdb.exceptions.DatabaseException;
import at.ac.fhcampuswien.fhmdb.exceptions.MovieApiException;
import at.ac.fhcampuswien.fhmdb.models.ClickEventHandler;
import at.ac.fhcampuswien.fhmdb.models.Genre;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import at.ac.fhcampuswien.fhmdb.models.sorting.DefaultSortingState;
import at.ac.fhcampuswien.fhmdb.models.sorting.SortingState;
import at.ac.fhcampuswien.fhmdb.ui.MovieCell;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class HomeController implements Initializable, Observer {    //Initializable -> beim Initialisieren der FXML-Datei anzupassen
    @FXML
    public JFXButton searchBtn;

    @FXML
    public TextField searchField;

    @FXML
    public JFXListView movieListView;

    @FXML
    public JFXComboBox genreComboBox;

    @FXML
    public JFXComboBox releaseYearComboBox;

    @FXML
    public JFXComboBox ratingFromComboBox;

    @FXML
    public JFXButton sortBtn;

    @FXML
    public JFXButton watchlistBtn;

    public List<Movie> allMovies;

    public ObservableList<Movie> observableMovies = FXCollections.observableArrayList();

    public SortingState sortingState;

    private WatchlistRepository watchlistRepository = WatchlistRepository.getInstance();    //WatchlistRepository-Klasse, um die Watchlist-Datenbank zu verwalten

    /*
    Lambda Expression der onAddToWatchlistClicked:
    die onClick-Methode des ClickEventHandler implementiert (Interface)
    hat einen einzelnen Parameter clickedItem.
    Funktionskörper:
        den geklickten Gegenstand zur Watchlist hinzuzufügen, indem die addToWatchlist-Methode der WatchlistRepository aufgerufen wird.
        Hier wird das Singleton-Pattern angewendet (da WatchlistRepository eine Singleton-Klasse ist; d.h., es gibt nur eine einzige Instanz dieser Klasse in der Anwendung)
        Falls Fehler: DatabaseException
     */
    private final ClickEventHandler onAddToWatchlistClicked = (clickedItem) ->
    {
        try {
            WatchlistRepository.getInstance().addToWatchlist((Movie) clickedItem); // 1d. Singleton Pattern
        } catch (DatabaseException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("An error occurred while trying to add movie to watchlist");
            alert.setContentText("Could not add data from the database");
            alert.showAndWait();
        }
    };

    public HomeController() throws DatabaseException {
        //HomeController als Observer beim WatchlistRepository registriert wird
        // -> Dadurch kann HomeController über Änderungen in der ObservableList informiert werden und entsprechend reagieren
        watchlistRepository.addObserver(this);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {    // Implementierung der Schnittstelle Initializable.
        initializeState();
        initializeLayout();
    }

    public void initializeState() {     // Anfangszustand von HomeController
        List<Movie> result = null;  //zuerst result leer
        try {
            result = MovieAPI.getAllMovies();   //result mit Movies aus API befüllen
        } catch (MovieApiException e) {         //Errormeldung falls problem kommt
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("An error occurred while trying to initialize Move API");
            alert.setContentText("Could not initialize movie from API");
            alert.showAndWait();
        }
        setMovies(result);
        setMovieList(result);
        sortingState = new DefaultSortingState(this);   // zuerst DefaultSortingState
    }

    public static void showError(String errormsg){  //Error
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("An error occurred while trying to initialize HomeController");
        alert.setContentText(errormsg);
        alert.showAndWait();
    }

    public void initializeLayout() {
        movieListView.setItems(observableMovies);   // set the items of the listview to the observable list
        movieListView.setCellFactory(movieListView -> new MovieCell(onAddToWatchlistClicked)); // apply custom cells to the listview

        // GENRE combobox
        Object[] genres = Genre.values();   // get all genres
        genreComboBox.getItems().add("No filter");  // add "no filter" to the combobox
        genreComboBox.getItems().addAll(genres);    // add all genres to the combobox
        genreComboBox.setPromptText("Filter by Genre");

        // RELEASEYEAR combobox
        releaseYearComboBox.getItems().add("No filter");  // add "no filter" to the combobox
        // fill array with numbers from 1900 to 2023
        Integer[] years = new Integer[124];
        for (int i = 0; i < years.length; i++)  years[i] = 1900 + i;
        releaseYearComboBox.getItems().addAll(years);    // add all years to the combobox
        releaseYearComboBox.setPromptText("Filter by Release Year");

        // RATING combobox
        ratingFromComboBox.getItems().add("No filter");  // add "no filter" to the combobox
        // fill array with numbers from 0 to 10
        Integer[] ratings = new Integer[11];
        for (int i = 0; i < ratings.length; i++)    ratings[i] = i;
        ratingFromComboBox.getItems().addAll(ratings);    // add all ratings to the combobox
        ratingFromComboBox.setPromptText("Filter by Rating");
    }

    public void setMovies(List<Movie> movies) {
        allMovies = movies;
    }

    public void setMovieList(List<Movie> movies) {
        observableMovies.clear();
        observableMovies.addAll(movies);
    }

    public void sortMovies(boolean isFiltered) {
        sortingState.sortObservableMovies(isFiltered);
    }
    // sort movies based on sortedState
    // by default sorted state is NONE
    // afterward it switches between ascending and descending

    // Searchbar
    public List<Movie> filterByQuery(List<Movie> movies, String query) {
        if (query == null || query.isEmpty()) return movies;

        if (movies == null) {
            throw new IllegalArgumentException("movies must not be null");
        }

        return movies.stream().filter(movie ->
                        movie.getTitle().toLowerCase().contains(query.toLowerCase()) ||
                                movie.getDescription().toLowerCase().contains(query.toLowerCase()))
                .toList();
    }

    // Genre Filter
    public List<Movie> filterByGenre(List<Movie> movies, Genre genre) {
        if (genre == null) return movies;

        if (movies == null) {
            throw new IllegalArgumentException("movies must not be null");
        }

        return movies.stream().filter(movie -> movie.getGenres().contains(genre)).toList();
    }


    @Deprecated //die Methode als veraltet markiert
    public void applyAllFilters(String searchQuery, Object genre) {
        List<Movie> filteredMovies = allMovies;

        if (!searchQuery.isEmpty()) {
            filteredMovies = filterByQuery(filteredMovies, searchQuery);
        }

        if (genre != null && !genre.toString().equals("No filter")) {
            filteredMovies = filterByGenre(filteredMovies, Genre.valueOf(genre.toString()));
        }

        observableMovies.clear();
        observableMovies.addAll(filteredMovies);
    }

    public void searchBtnClicked(ActionEvent actionEvent) {
        String searchQuery = searchField.getText().trim().toLowerCase();    //trim(): Trimmen von Leerzeichen
        String releaseYear = validateComboboxValue(releaseYearComboBox.getSelectionModel().getSelectedItem());
        String ratingFrom = validateComboboxValue(ratingFromComboBox.getSelectionModel().getSelectedItem());
        String genreValue = validateComboboxValue(genreComboBox.getSelectionModel().getSelectedItem());

        Genre genre = null;
        if (genreValue != null) {
            genre = Genre.valueOf(genreValue);
        }

        List<Movie> movies = getMovies(searchQuery, String.valueOf(genre), releaseYear, ratingFrom);
        setMovies(movies);
        setMovieList(movies);
        // applyAllFilters(searchQuery, genre);
        // sort movies using State pattern
        sortMovies(true);
    }

    // Wird COmboboxBox verwendet? Wenn ja dann mach, wenn nein dann null
    public String validateComboboxValue(Object value) {
        if (value != null && !value.toString().equals("No filter")) {
            return value.toString();
        }
        return null;
    }


    public List<Movie> getMovies(String searchQuery, String genre, String releaseYear, String ratingFrom) {
        try {
            //gibt eine Liste von Filmen zurück, die anhand der übergebenen Suchkriterien gefiltert sind
            return MovieAPI.getAllMovies(searchQuery, genre, releaseYear, ratingFrom);
        } catch (MovieApiException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("An error occurred while trying to get movie");
            alert.setContentText("Could not get movie from API");
            alert.showAndWait();
        }
        return null;
    }

    public void sortBtnClicked(ActionEvent actionEvent) {
        // sort movies using State pattern
        sortMovies(false);
    }

    //Wechsel zu Watchlist Seite
    public void watchlistBtnClicked(ActionEvent actionEvent) throws IOException {
        WachtlistControllerFactory controllerFactory = new WachtlistControllerFactory();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("watchlist-view.fxml"));
        loader.setControllerFactory(controllerFactory);
        Parent root = loader.load();

        Stage window = (Stage) watchlistBtn.getScene().getWindow();
        Scene watchlistScene = new Scene(root, 890, 620);
        watchlistScene.getStylesheets().add(Objects.requireNonNull(FhmdbApplication.class.getResource("styles.css")).toExternalForm());
        window.setScene(watchlistScene);
    }

    public void changeState (SortingState state) {
        this.sortingState = state;
    }

    //Observer zur Benachrichtigung bei Änderungen.
    @Override
    public void update(String message) {
        System.out.println("Update: " + message);

        // Show an information alert with the update message
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Watchlist");
        alert.setContentText(message);
        alert.showAndWait();
    }
}