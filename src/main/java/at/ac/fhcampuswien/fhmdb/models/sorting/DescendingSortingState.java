package at.ac.fhcampuswien.fhmdb.models.sorting;

import at.ac.fhcampuswien.fhmdb.HomeController;
import at.ac.fhcampuswien.fhmdb.models.Movie;

import java.util.Comparator;

public class DescendingSortingState implements SortingState{

    //selbe wie AscendingSortingState nur umgekehrt.
    public HomeController homeController;
    public DescendingSortingState(HomeController homeController) {
        this.homeController = homeController;
    }
    @Override
    public void sortObservableMovies(boolean isFiltered) {
        if (isFiltered) {
            homeController.observableMovies.sort(Comparator.comparing(Movie::getTitle).reversed());
        } else {
            homeController.observableMovies.sort(Comparator.comparing(Movie::getTitle));
            homeController.changeState(new AscendingSortingState(homeController));
        }
    }
}
