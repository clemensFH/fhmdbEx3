package at.ac.fhcampuswien.fhmdb.models;

@FunctionalInterface
public interface ClickEventHandler<T> {
    void onClick(T t) ;
}

/*
    um auf Click zu reagieren.
    Methode onClick wird aufgerufen, wenn ein Click ausgelöst wird.
    generische Typ T gibt den Typ des geklickten Objekts an.
 */
