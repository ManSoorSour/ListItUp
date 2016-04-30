package nemphys.listitup.model;

import java.util.ArrayList;

public class SimpleObservable<T> {

    private final ArrayList<OnChangeListener<T>> listeners = new ArrayList<>();

    public void addListener(OnChangeListener<T> listener) {
        synchronized (listeners) {
            listeners.add(listener);
        }
    }

    public void removeListener(OnChangeListener<T> listener) {
        synchronized (listeners) {
            listeners.remove(listener);
        }
    }

    protected void notifyObservers(final T model) {
        synchronized (listeners) {
            for (OnChangeListener<T> listener : listeners) {
                listener.onChange(model);
            }
        }
    }

}
