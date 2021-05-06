package de.ur.mi.android.demos.todo;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import de.ur.mi.android.demos.todo.tasks.TaskManager;
import de.ur.mi.android.demos.todo.ui.TaskListAdapter;

public class MainActivity extends AppCompatActivity {

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
        taskList.setAdapter(taskListAdapter);
    }

    private void testList() {
        taskManager.addTask("Java lernen");
        taskManager.addTask("Android lernen");
        taskManager.addTask("Stream schauen");
        taskManager.toggleTaskStateAtPosition(0);
        taskListAdapter.setTasks(taskManager.getCurrentTasks());
    }
}