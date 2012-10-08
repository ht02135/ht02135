package com.hung.observer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/*
 1>subject has many observers
 2>all observers notified whenever the subject undergoes a change in state
 */
public class Subject {
    
    private List<IObserver> observers = new ArrayList<IObserver>();
    
    public void addObserver(IObserver observer) {
        observers.add(observer);
    }
    
    public void notifyObservers() {
        Iterator<IObserver> iter = observers.iterator();
        while (iter.hasNext()) {
            iter.next().stateChanged("this state changed");
        }
    }
    
    public void dummyMethod() {
        // something might happen to change state of Subject.  then we need to notify all observers
        notifyObservers();
    }
}
