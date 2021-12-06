package src.main.model.user;

import src.main.model.property.Property;

import java.util.ArrayList;

public class PostingsNotification implements Subject {
    private Property latestInsertedProperty;
    private ArrayList<Observer> observers;

    public void registerObserver(Observer o) {
        this.observers.add(o);
    }

    public void removeObserver(Observer o) {
        this.observers.remove(o);
    }

    public void notifyAllObservers() {
        for (int i = 0; i < observers.size(); i++) {
            observers.get(i).update(latestInsertedProperty);
        }
    }
}
