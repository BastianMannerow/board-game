package tnt.util;

import java.util.ArrayList;
import java.util.List;

public class Observable {
    private List<Observer> observers = new ArrayList<Observer>();

    public void addObserver(Observer obs) {
        observers.add(obs);
    }

    public void removeObserver(Observer obs) {
        observers.remove(obs);
    }

    public void clearObservers() {
        observers.clear();
    }

    public void notifyObservers() {
        for (Observer o : observers) {
            o.update();
        }
    }

}
