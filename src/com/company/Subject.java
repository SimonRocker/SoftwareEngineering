package com.company;

import java.util.ArrayList;
import java.util.Iterator;

public class Subject {

    private ArrayList<IObserver> observers = new ArrayList<IObserver>();

    public void attach(IObserver observer) {
        observers.add(observer);
    }

    public void notify(int state) {
        Iterator iterator = observers.iterator();
        while (iterator.hasNext()) {
            ((IObserver) iterator.next()).update(state);
        }
    }
}
