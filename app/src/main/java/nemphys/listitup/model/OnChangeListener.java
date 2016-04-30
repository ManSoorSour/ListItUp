package nemphys.listitup.model;

/**
 * Simple listener interface to force listeners to implement the method to be performed when a
 * change occurs.
 *
 * @param <T> type of the model the listener is listening to
 */
public interface OnChangeListener<T> {
    /**
     * To be performed by the listener when a change occurs.
     *
     * @param model the model the change occurs on
     */
    void onChange(T model);
}
