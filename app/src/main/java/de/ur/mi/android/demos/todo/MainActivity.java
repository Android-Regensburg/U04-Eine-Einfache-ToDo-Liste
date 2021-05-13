package de.ur.mi.android.demos.todo;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import de.ur.mi.android.demos.todo.tasks.TaskManager;

public class MainActivity extends AppCompatActivity implements TaskManager.TaskManagerListener {

    private TaskManager taskManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initTasks();
        initUI();
    }


    private void initTasks() {
        taskManager = new TaskManager(this);
        taskManager.createNewTask("TEST");
    }


    private void initUI() {
        setContentView(R.layout.activity_main);
    }


    @Override
    public void onTaskListUpdated() {
        Log.d("TODO-App", "TaskManager updated");
    }
}