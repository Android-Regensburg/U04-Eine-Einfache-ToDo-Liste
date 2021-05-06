package de.ur.mi.android.demos.todo;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import de.ur.mi.android.demos.todo.tasks.Task;
import de.ur.mi.android.demos.todo.tasks.TaskManager;
import de.ur.mi.android.demos.todo.ui.TaskListAdapter;
import de.ur.mi.android.demos.todo.ui.TaskListAdapterListener;

public class MainActivity extends AppCompatActivity implements TaskListAdapterListener {

    private TaskManager taskManager;
    private TaskListAdapter taskListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initTasks();
        initUI();
        testList();
    }

    private void initTasks() {
        taskManager = new TaskManager();
    }

    private void initUI() {
        setContentView(R.layout.activity_main);
        ListView taskList = findViewById(R.id.task_list);
        taskListAdapter = new TaskListAdapter(this);
        taskListAdapter.setTaskListAdapterListener(this);
        taskList.setAdapter(taskListAdapter);
    }

    private void testList() {
        taskManager.addTask("Java lernen");
        taskManager.addTask("Android lernen");
        taskManager.addTask("Stream schauen");
        taskManager.toggleTaskStateAtPosition(0);
        taskListAdapter.setTasks(taskManager.getCurrentTasks());
    }

    @Override
    public void onTaskSelected(Task task) {
        taskManager.toggleTaskStateForId(task.getID());
        taskListAdapter.setTasks(taskManager.getCurrentTasks());
    }
}