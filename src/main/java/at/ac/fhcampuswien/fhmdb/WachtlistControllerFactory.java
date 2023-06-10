package at.ac.fhcampuswien.fhmdb;

import javafx.util.Callback;

import java.lang.reflect.InvocationTargetException;

public class WachtlistControllerFactory implements Callback<Class<?>, Object> {

    private static WatchlistController watchlistController;
    @Override
    public Object call(Class<?> aClass) {
        System.out.println("Watch factory");
        if(watchlistController == null){
            System.out.println("newly created");
            try {
                watchlistController = (WatchlistController) aClass.getDeclaredConstructor().newInstance();
            } catch (Exception e) {
                WatchlistController.showError(e.getMessage());
            }
        } else{
            System.out.println("using old");
        }
        return watchlistController;
    }
}
