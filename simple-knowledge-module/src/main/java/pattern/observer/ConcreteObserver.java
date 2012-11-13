package pattern.observer;

public class ConcreteObserver implements IObserver {

    public void stateChanged(String state) {
        // mm, this does feel a bit like callback
        // chance for observer to react to change in subject state
        System.out.println("reacted to change in subject state="+state);
    }

}
