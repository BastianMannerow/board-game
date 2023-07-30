package tnt.util;

import java.util.ArrayList;
import java.util.List;

/**
 * everything inherit this class can be observed by adding observer
 */
public class Observable {
    private List<Observer> observers = new ArrayList<Observer>();

    /**
     * add an observer to this observable
     * @param obs the oberserver listen to this observable
     */
    public void addObserver(Observer obs) {
        observers.add(obs);
    }

    /**
     * removes the observer from this oberservable
     * @param obs the observe to get removed
     */
    public void removeObserver(Observer obs) {
        observers.remove(obs);
    }

    /**
     * notifies all registered observer, so they can update their data
     */
    public void notifyObservers() {
        for (Observer o : observers) {
            o.update();
        }
    }
}
