package at.ac.fhcampuswien.fhmdb;

import at.ac.fhcampuswien.fhmdb.database.WatchlistMovieEntity;
import at.ac.fhcampuswien.fhmdb.database.WatchlistRepository;
import at.ac.fhcampuswien.fhmdb.exceptions.DatabaseException;
import at.ac.fhcampuswien.fhmdb.models.ClickEventHandler;
import at.ac.fhcampuswien.fhmdb.ui.WatchlistCell;
import com.jfoenix.controls.JFXButton;
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
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.*;


// Selbe Prinzip wie HomeController
public class WatchlistController implements Initializable{

    @FXML
    public JFXListView watchListView;

    @FXML
    public JFXButton homeviewBtn;

    protected ObservableList<WatchlistMovieEntity> observableMovies = FXCollections.observableArrayList();

    WatchlistRepository watchlistRepository = WatchlistRepository.getInstance();    //1e.

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

    public static void showError(String errormsg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("An error occurred while trying to initialize WatchlistController");
        alert.setContentText(errormsg);
        alert.showAndWait();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeState();
        initializeLayout();
        // Register this WatchlistController as an observer
    }

    public void initializeState() { //1f.
        watchlistRepository = null;     //?? Wozu brauchen wir hier null?
        watchlistRepository = WatchlistRepository.getInstance();

        List<WatchlistMovieEntity> result = null;

        try {

            result = watchlistRepository.getAll();  //1g.

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
        HomeControllerFactory homeControllerFactory = new HomeControllerFactory();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("home-view.fxml"));
        loader.setControllerFactory(homeControllerFactory);
        Parent root = loader.load();

        Stage window = (Stage)homeviewBtn.getScene().getWindow();
        window.setScene(new Scene(root, 890, 620));
    }

}
