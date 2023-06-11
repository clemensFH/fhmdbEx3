package at.ac.fhcampuswien.fhmdb.models.sorting;

import at.ac.fhcampuswien.fhmdb.HomeController;
import at.ac.fhcampuswien.fhmdb.models.Movie;

import java.util.Comparator;

public class DefaultSortingState implements SortingState{
    public HomeController homeController;

    public DefaultSortingState(HomeController homeController) {
        this.homeController = homeController;
    }

    @Override
    public void sortObservableMovies(boolean isFiltered) {
        homeController.observableMovies.sort(Comparator.comparing(Movie::getTitle));
        homeController.changeState(new AscendingSortingState(homeController));
    }
}
