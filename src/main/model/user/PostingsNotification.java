package src.main.model.user;

import src.main.model.property.Property;

import java.util.ArrayList;

public class PostingsNotification implements Subject {
    private Property latestInsertedProperty;
    private ArrayList<Observer> observers;

    // Promises: add observer to observers array
    // Requires: observer to be added
    public void registerObserver(Observer o) {
        this.observers.add(o);
    }

    // Promises: remove observer to observers array
    // Requires: observer to be removed
    public void removeObserver(Observer o) {
        this.observers.remove(o);
    }

    // Promises: notify all observers of new property added to their subscription specifications
    public void notifyAllObservers() {
        for (int i = 0; i < observers.size(); i++) {
            observers.get(i).update(latestInsertedProperty);
        }
    }
}
