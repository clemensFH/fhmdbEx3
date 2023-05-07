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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.*;


public class WatchlistController implements Initializable {

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
            e.printStackTrace();
        }
    }

    private final ClickEventHandler onRemoveFromWatchlistClicked = (clickedItem) ->
    {
        try {
            watchlistRepository.removeFromWatchlist((WatchlistMovieEntity) clickedItem);
            initializeState();
        } catch (SQLException e) {
            throw new RuntimeException(e); //TODO
        }
    };

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeState();
        initializeLayout();
    }

    public void initializeState() {
        WatchlistRepository watchlistRepo = null;
        try {
            watchlistRepo = new WatchlistRepository();
        } catch (DatabaseException e) {
            e.printStackTrace();
        }
        List<WatchlistMovieEntity> result = null;
        try {
            result = watchlistRepo.getAll();
        } catch (DatabaseException e) {
            e.printStackTrace();
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


}
