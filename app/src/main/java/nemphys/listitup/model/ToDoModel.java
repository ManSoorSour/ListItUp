package nemphys.listitup.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import nemphys.listitup.activity.MainActivity;

public class ToDoModel extends SimpleObservable<ToDoModel> {

    private static final String TAG = MainActivity.class.getSimpleName();

    private ToDoDbHelper dbHelper;

    public ToDoModel(Context context) {
        dbHelper = new ToDoDbHelper(context);
    }

    public ArrayList<String> getToDoList() {
        ArrayList<String> toDoList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(ToDoContract.ToDoEntry.TABLE_NAME,
                new String[]{ToDoContract.ToDoEntry._ID,
                        ToDoContract.ToDoEntry.COLUMN_TASK_TITLE},
                null, null, null, null, null);
        while (cursor.moveToNext()) {
            int i = cursor.getColumnIndex(ToDoContract.ToDoEntry.COLUMN_TASK_TITLE);
            toDoList.add(cursor.getString(i));
        }
        cursor.close();
        db.close();
        return toDoList;
    }

    public void addItem(String toDoItem) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ToDoContract.ToDoEntry.COLUMN_TASK_TITLE, toDoItem);
        db.insertWithOnConflict(ToDoContract.ToDoEntry.TABLE_NAME,
                null,
                values,
                SQLiteDatabase.CONFLICT_REPLACE);
        db.close();
        notifyObservers(this);
    }

    public void removeItem(String toDoItem) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(ToDoContract.ToDoEntry.TABLE_NAME,
                ToDoContract.ToDoEntry.COLUMN_TASK_TITLE + " = ?",
                new String[]{toDoItem});
        db.close();
        notifyObservers(this);
    }

}
