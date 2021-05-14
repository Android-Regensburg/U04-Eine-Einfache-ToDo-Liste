package de.ur.mi.android.demos.todo;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import de.ur.mi.android.demos.todo.tasks.Task;
import de.ur.mi.android.demos.todo.tasks.TaskManager;
import de.ur.mi.android.demos.todo.ui.TaskListAdapter;

public class MainActivity extends AppCompatActivity implements TaskManager.TaskManagerListener {

    private TaskManager taskManager;
    private TaskListAdapter taskAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initTasks();
        initUI();
    }

    private void initTasks() {
        taskManager = new TaskManager(this);
    }

    private void initUI() {
        setContentView(R.layout.activity_main);
        ListView taskList = findViewById(R.id.task_list);
        taskAdapter = new TaskListAdapter(this);
        taskList.setAdapter(taskAdapter);
        taskList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Task taskToToggle = taskAdapter.getTaskAtPosition(position);
                taskManager.toggleTaskStateForId(taskToToggle.getID());
                return true;
            }
        });
        Button button = findViewById(R.id.input_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTaskFromUI();
            }
        });
    }

    private void addTaskFromUI() {
        EditText taskDescription = findViewById(R.id.input_text);
        String currentDescription = taskDescription.getText().toString();
        if (currentDescription.length() > 0) {
            taskManager.createNewTask(currentDescription);
        }
    }

    @Override
    public void onTaskListUpdated() {
        taskAdapter.setTasks(taskManager.getCurrentTasks());
    }
}