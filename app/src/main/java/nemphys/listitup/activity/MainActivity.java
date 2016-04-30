package nemphys.listitup.activity;

import android.content.DialogInterface;
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

import nemphys.listitup.R;
import nemphys.listitup.model.OnChangeListener;
import nemphys.listitup.model.ToDoModel;

public class MainActivity extends AppCompatActivity implements OnChangeListener<ToDoModel> {

    private static final String TAG = MainActivity.class.getSimpleName();

    private ToDoModel model;
    private ListView listToDo;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        model = new ToDoModel();
        model.addListener(this);
        listToDo = (ListView) findViewById(R.id.listToDo);
        adapter = new ArrayAdapter<>(this, R.layout.item_todo, R.id.txtItemName,
                model.getToDoList());
        listToDo.setAdapter(adapter);
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
                        .setPositiveButton("Hinzufügen", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String task = String.valueOf(taskEditText.getText());
                                model.addItem(task);
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
        String task = String.valueOf(taskTextView.getText());
        model.removeItem(task);
    }

    @Override
    public void onChange(ToDoModel model) {
        adapter.notifyDataSetChanged();
    }
}
