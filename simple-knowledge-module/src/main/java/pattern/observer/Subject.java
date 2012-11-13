package pattern.observer;

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
        // simulate asynch notification
        Runnable runnable = new Runnable() {
            public void run() {
                notifyObservers();
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
    }
}
