package nemphys.listitup.model;

import java.util.ArrayList;

/**
 * Simple Observable implementation that informs all registered listeners about changes via the
 * notifyObservers method. Listeners have to implement the OnChangeListener interface.
 *
 * @param <T> type of the model the listeners listen to
 */
public class SimpleObservable<T> {

    private final ArrayList<OnChangeListener<T>> listeners = new ArrayList<>();

    /**
     * Add listeners to the observable.
     *
     * @param listener listener to add
     */
    public void addListener(OnChangeListener<T> listener) {
        synchronized (listeners) {
            listeners.add(listener);
        }
    }

    /**
     * Remove listeners from the observable.
     *
     * @param listener listener to remove
     */
    public void removeListener(OnChangeListener<T> listener) {
        synchronized (listeners) {
            listeners.remove(listener);
        }
    }

    /**
     * Perform the onChange method on all listeners
     *
     * @param model reference to the observable model
     */
    protected void notifyObservers(final T model) {
        synchronized (listeners) {
            for (OnChangeListener<T> listener : listeners) {
                listener.onChange(model);
            }
        }
    }

}
