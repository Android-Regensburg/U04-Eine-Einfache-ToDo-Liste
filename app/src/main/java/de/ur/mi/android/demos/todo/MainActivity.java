package de.ur.mi.android.demos.todo;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import de.ur.mi.android.demos.todo.tasks.Task;
import de.ur.mi.android.demos.todo.tasks.TaskManager;
import de.ur.mi.android.demos.todo.ui.TaskListRecyclerAdapter;

public class MainActivity extends AppCompatActivity implements TaskListRecyclerAdapter.TaskListAdapterListener, TaskManager.TaskManagerListener {

    private TaskManager taskManager;
    private EditText taskInputElement;
    private TaskListRecyclerAdapter taskListAdapter;

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
        RecyclerView taskList = findViewById(R.id.task_list);
        taskListAdapter = new TaskListRecyclerAdapter(this);
        taskList.setAdapter(taskListAdapter);
        taskInputElement = findViewById(R.id.input_text);
        Button button = findViewById(R.id.input_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTaskFromUI();
            }
        });
    }

    private void addTaskFromUI() {
        String description = taskInputElement.getText().toString();
        if (description.length() > 0) {
            taskManager.addTask(description);
        }
    }

    @Override
    public void onItemSelected(Task task) {
        taskManager.toggleTaskStateForId(task.getID());
    }

    @Override
    public void onTaskAdded(Task task) {
        taskListAdapter.setTasks(taskManager.getCurrentTasks());
        taskInputElement.setText("");
        taskInputElement.requestFocus();
    }

    @Override
    public void onTaskChanged(Task task) {
        taskListAdapter.setTasks(taskManager.getCurrentTasks());
    }
}