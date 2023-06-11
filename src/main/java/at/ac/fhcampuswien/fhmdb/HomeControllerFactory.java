package at.ac.fhcampuswien.fhmdb;

import at.ac.fhcampuswien.fhmdb.exceptions.DatabaseException;
import javafx.util.Callback;

import java.lang.reflect.InvocationTargetException;

public class HomeControllerFactory implements Callback<Class<?>, Object> {

    private static HomeController homeController;

    @Override
    public Object call(Class<?> aClass) {
        System.out.println("HomeController factory");
        if (homeController == null) {
            try {
                System.out.println("newly created");
                homeController = (HomeController) aClass.getDeclaredConstructor().newInstance();
            } catch (Exception e) {
                HomeController.showError(e.getMessage());
            }
        } else {
            System.out.println("using old");
        }
        return homeController;
    }
}
