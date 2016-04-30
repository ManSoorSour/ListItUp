package nemphys.listitup.model;

import java.util.ArrayList;

/**
 * The model that manages our applications state
 */
public class ToDoModel extends SimpleObservable<ToDoModel> {

    private ArrayList<String> toDoList = new ArrayList<>();

    public ArrayList<String> getToDoList() {
        return toDoList;
    }

    public void addItem(String toDoItem) {
        toDoList.add(toDoItem);
        notifyObservers(this);
    }

    public void removeItem(String toDoItem) {
        toDoList.remove(toDoItem);
        notifyObservers(this);
    }

}
