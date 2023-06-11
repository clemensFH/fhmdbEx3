package at.ac.fhcampuswien.fhmdb.models.sorting;

import at.ac.fhcampuswien.fhmdb.HomeController;
import at.ac.fhcampuswien.fhmdb.models.Movie;

import java.util.Comparator;

public class AscendingSortingState implements SortingState{

    public HomeController homeController;

    public AscendingSortingState(HomeController homeController) {
        this.homeController = homeController;
    }
    @Override
    public void sortObservableMovies(boolean isFiltered) {
        if (isFiltered) {
            homeController.observableMovies.sort(Comparator.comparing(Movie::getTitle));
        } else {
            homeController.observableMovies.sort(Comparator.comparing(Movie::getTitle).reversed());
            homeController.changeState(new DescendingSortingState(homeController));
        }
    }
}
