package nemphys.listitup.models;

import android.provider.BaseColumns;

public final class ToDoContract {

    public ToDoContract() {
    }

    public static class ToDoEntry implements BaseColumns {
        public static final String TABLE_NAME = "tasks";
        public static final String COLUMN_TASK_TITLE = "title";
    }

}
