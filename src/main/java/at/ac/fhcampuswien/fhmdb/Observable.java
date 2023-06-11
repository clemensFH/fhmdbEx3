package at.ac.fhcampuswien.fhmdb;

public interface Observable {
    // Observable object in the Observer pattern

    // adds an observer to the list of observers.
    void addObserver(Observer observer);

    // removes an observer from the list of observers.
    void removeObserver(Observer observer);

    // This method notifies all observers about a change or update -> passing a message as a parameter
    void notifyObservers(String message);
}
