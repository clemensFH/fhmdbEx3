package at.ac.fhcampuswien.fhmdb;

import at.ac.fhcampuswien.fhmdb.exceptions.DatabaseException;
import javafx.util.Callback;

import java.lang.reflect.InvocationTargetException;

public class HomeControllerFactory implements Callback<Class<?>, Object> {  //Callback: Controller für die Home-Ansicht zu erzeugen

    // um Instanzen des HomeController zu erzeugen; sicherzustellen, dass nur eine einzige Instanz des HomeController erstellt wird.
    private static HomeController homeController;

    @Override
    public Object call(Class<?> aClass) {
        System.out.println("HomeController factory");
        if (homeController == null) {   // ist schon eine vorhanden?
            try {
                // neue Instanz des HomeController
                System.out.println("newly created");
                homeController = (HomeController) aClass.getDeclaredConstructor().newInstance();
                // aClass.getDeclaredConstructor().newInstance(): verwendet Reflection, um den standardmäßigen Konstruktor der Klasse aClass aufzurufen & eine neue Instanz zu erzeugen.
            } catch (Exception e) {
                HomeController.showError(e.getMessage());   // Error
            }
        } else {
            System.out.println("using old");    // falls vorhanden dann nutz den.
        }
        return homeController;  //Gib Instanz aus
    }
}
