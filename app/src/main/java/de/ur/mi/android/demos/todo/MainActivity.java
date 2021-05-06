package de.ur.mi.android.demos.todo;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;

import de.ur.mi.android.demos.todo.tasks.Task;
import de.ur.mi.android.demos.todo.ui.TaskListAdapter;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Task> tasks;
    private TaskListAdapter taskListAdapter;
    private EditText taskDescriptionInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initTasks();
        initUI();
    }

    private void initTasks() {
        tasks = new ArrayList<>();
    }

    private void initUI() {
        setContentView(R.layout.activity_main);
        taskListAdapter = new TaskListAdapter(this);
        ListView taskList = findViewById(R.id.task_list);
        taskList.setAdapter(taskListAdapter);
        taskList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                toggleTaskAtPosition(position);
                return true;
            }
        });
        taskDescriptionInput = findViewById(R.id.input_text);
        Button inputButton = findViewById(R.id.input_button);
        inputButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onInputButtonClicked();
            }
        });
    }

    private void addTask(String description) {
        Task taskToAdd = new Task(description);
        tasks.add(taskToAdd);
        updateTasksInAdapter();
    }

    private void toggleTaskAtPosition(int position) {
        Task taskToToggle = tasks.get(position);
        if (taskToToggle != null) {
            if (taskToToggle.isClosed()) {
                taskToToggle.markAsOpen();
            } else {
                taskToToggle.markAsClosed();
            }
        }
        updateTasksInAdapter();
    }

    private void updateTasksInAdapter() {
        Collections.sort(tasks);
        taskListAdapter.setTasks(tasks);
    }

    private void onInputButtonClicked() {
        String currentInput = taskDescriptionInput.getText().toString();
        if (currentInput.length() > 0) {
            addTask(currentInput);
            taskDescriptionInput.setText("");
            taskDescriptionInput.requestFocus();
        }
    }
}