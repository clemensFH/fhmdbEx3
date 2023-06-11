package at.ac.fhcampuswien.fhmdb;

import at.ac.fhcampuswien.fhmdb.api.MovieAPI;
import at.ac.fhcampuswien.fhmdb.database.WatchlistMovieEntity;
import at.ac.fhcampuswien.fhmdb.database.WatchlistRepository;
import at.ac.fhcampuswien.fhmdb.exceptions.DatabaseException;
import at.ac.fhcampuswien.fhmdb.models.ClickEventHandler;
import at.ac.fhcampuswien.fhmdb.models.Genre;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import at.ac.fhcampuswien.fhmdb.models.SortedState;
import at.ac.fhcampuswien.fhmdb.ui.MovieCell;
import at.ac.fhcampuswien.fhmdb.ui.WatchlistCell;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import javafx.application.Platform;
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
import java.sql.SQLException;
import java.util.*;


public class WatchlistController implements Initializable, Observer{

    @FXML
    public JFXListView watchListView;

    @FXML
    public JFXButton homeviewBtn;

    protected ObservableList<WatchlistMovieEntity> observableMovies = FXCollections.observableArrayList();

    WatchlistRepository watchlistRepository;

    {
        try {
            watchlistRepository = new WatchlistRepository();
        } catch (DatabaseException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("An error occurred while initializing watchlist");
            alert.setContentText("Could not connect to the database");
            alert.showAndWait();
        }
    }

    private final ClickEventHandler onRemoveFromWatchlistClicked = (clickedItem) ->
    {
        try {
            watchlistRepository.removeFromWatchlist((WatchlistMovieEntity) clickedItem);
        } catch (DatabaseException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("An error occurred while removing Movie from Watchlist");
            alert.setContentText("Could not remove data from the database");
            alert.showAndWait();
        }
        initializeState();

    };


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeState();
        initializeLayout();
        watchlistRepository.addObserver(this); // Registrieren des WatchlistControllers als Beobachter
    }

    public void initializeState() {
        WatchlistRepository watchlistRepo = null;
        try {
            watchlistRepo = new WatchlistRepository();
        } catch (DatabaseException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("An error occurred while initializing watchlist");
            alert.setContentText("Could not connect to the database");
            alert.showAndWait();
        }

        List<WatchlistMovieEntity> result = null;

        try {
            result = watchlistRepo.getAll();
            watchlistRepository.addObserver(this); // Registrieren des WatchlistControllers als Beobachter
        } catch (DatabaseException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("An error occurred while initializing watchlist");
            alert.setContentText("Could not retrieve data from the database");
            alert.showAndWait();
        }

        setMovieList(result);
    }

    public void initializeLayout() {
        watchListView.setItems(observableMovies);   // set the items of the listview to the observable list
        watchListView.setCellFactory(movieListView -> new WatchlistCell(onRemoveFromWatchlistClicked)); // apply custom cells to the listview
    }

    public void setMovieList(List<WatchlistMovieEntity> movies) {
        observableMovies.clear();
        observableMovies.addAll(movies);
    }


    public void homeviewBtnClicked(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("home-view.fxml"));

        Stage window = (Stage)homeviewBtn.getScene().getWindow();
        window.setScene(new Scene(root, 890, 620));
    }


    @Override
    public void update(String message) {
        System.out.println("Update: " + message);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Watchlist");
        alert.setContentText(message);
        alert.showAndWait();
    }
}
