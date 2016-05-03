package nemphys.listitup.activities;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import nemphys.listitup.R;
import nemphys.listitup.models.ToDoContract;
import nemphys.listitup.models.ToDoDbHelper;

public class MainActivity extends AppCompatActivity {

    //private static final String TAG = MainActivity.class.getSimpleName();

    private ToDoDbHelper dbHelper;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new ToDoDbHelper(this);
        ListView listToDo = (ListView) findViewById(R.id.listToDo);

        adapter = new ArrayAdapter<>(this, R.layout.item_todo,
                R.id.txtItemName, getToDoList());
        if (listToDo != null) {
            listToDo.setAdapter(adapter);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.addItem:
                final EditText taskEditText = new EditText(this);
                AlertDialog dialog = new AlertDialog.Builder(this)
                        .setTitle("Neue Aufgabe hinzufügen")
                        .setMessage("Was wollen Sie als nächstes tun?")
                        .setView(taskEditText)
                        .setPositiveButton("Hinzufügen",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        String toDoItem = String.valueOf(taskEditText.getText());
                                        SQLiteDatabase db = dbHelper.getWritableDatabase();
                                        ContentValues values = new ContentValues();
                                        values.put(ToDoContract.ToDoEntry.COLUMN_TASK_TITLE, toDoItem);
                                        db.insertWithOnConflict(ToDoContract.ToDoEntry.TABLE_NAME,
                                                null,
                                                values,
                                                SQLiteDatabase.CONFLICT_REPLACE);
                                        db.close();
                                        updateGUI();
                                    }
                                })
                        .setNegativeButton("Abbrechen", null)
                        .create();
                dialog.show();
                return true;
            default:
                return false;
        }
    }

    public void deleteTask(View view) {
        View parent = (View) view.getParent();
        TextView taskTextView = (TextView) parent.findViewById(R.id.txtItemName);
        String toDoItem = String.valueOf(taskTextView.getText());
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(ToDoContract.ToDoEntry.TABLE_NAME,
                ToDoContract.ToDoEntry.COLUMN_TASK_TITLE + " = ?",
                new String[]{toDoItem});
        db.close();
        updateGUI();
    }

    public void updateGUI() {
        adapter.clear();
        adapter.addAll(getToDoList());
        adapter.notifyDataSetChanged();
    }

    private ArrayList<String> getToDoList() {
        ArrayList<String> toDoList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] projection = new String[]{ToDoContract.ToDoEntry._ID,
                ToDoContract.ToDoEntry.COLUMN_TASK_TITLE};
        Cursor cursor = db.query(ToDoContract.ToDoEntry.TABLE_NAME,
                projection, null, null, null, null, null);
        while (cursor.moveToNext()) {
            int i = cursor.getColumnIndex(ToDoContract.ToDoEntry.COLUMN_TASK_TITLE);
            toDoList.add(cursor.getString(i));
        }
        cursor.close();
        db.close();
        return toDoList;
    }

}
