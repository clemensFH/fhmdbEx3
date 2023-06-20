package at.ac.fhcampuswien.fhmdb;

import javafx.util.Callback;

import java.lang.reflect.InvocationTargetException;

public class WachtlistControllerFactory implements Callback<Class<?>, Object> {

    // Genau wie HomeControllerFactory!
    private static WatchlistController watchlistController;
    @Override
    public Object call(Class<?> aClass) {
        System.out.println("WatchlistController factory");
        if(watchlistController == null){
            try {
                watchlistController = (WatchlistController) aClass.getDeclaredConstructor().newInstance();
                System.out.println("newly created");
            } catch (Exception e) {
                WatchlistController.showError(e.getMessage());
            }
        } else{
            System.out.println("using old");
        }
        return watchlistController;
    }
}
