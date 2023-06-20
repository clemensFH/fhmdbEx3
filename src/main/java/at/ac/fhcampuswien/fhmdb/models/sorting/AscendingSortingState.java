package at.ac.fhcampuswien.fhmdb.models.sorting;

import at.ac.fhcampuswien.fhmdb.HomeController;
import at.ac.fhcampuswien.fhmdb.models.Movie;

import java.util.Comparator;

public class AscendingSortingState implements SortingState{

    public HomeController homeController;
    // um auf die Liste der beobachtbaren Filme zugreifen & Änderungen vornehmen zu können
    public AscendingSortingState(HomeController homeController) {
        this.homeController = homeController;
    }
    @Override
    public void sortObservableMovies(boolean isFiltered) {  //Methode aus SortingState-Interface , um die Liste der observeble Filme zu sortieren, abhängig davon, ob eine Filterung angewendet wird oder nicht.
        if (isFiltered) {   // aufsteigend sortieren
            homeController.observableMovies.sort(Comparator.comparing(Movie::getTitle));    //Comparator -> Filme nach ihren Titeln sortiert werden
        } else {            // absteigend sortieren
            homeController.observableMovies.sort(Comparator.comparing(Movie::getTitle).reversed()); // absteigend bzw. genau u,gekehrt
            homeController.changeState(new DescendingSortingState(homeController)); // Zustand des HomeController auf DescendingSortingState geändert
        }
    }
}
