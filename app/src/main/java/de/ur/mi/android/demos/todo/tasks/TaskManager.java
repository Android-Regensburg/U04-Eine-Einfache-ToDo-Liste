package de.ur.mi.android.demos.todo.tasks;

import java.util.ArrayList;
import java.util.Collections;

public class TaskManager {

    private final ArrayList<Task> tasks;

    public TaskManager() {
        this.tasks = new ArrayList<>();
    }

    public void addTask(String description) {
        Task taskToAdd = new Task(description);
        tasks.add(taskToAdd);
    }

    public void toggleTaskStateAtPosition(int position) {
        Task taskToToggle = tasks.get(position);
        if (taskToToggle != null) {
            if (taskToToggle.isClosed()) {
                taskToToggle.markAsOpen();
            } else {
                taskToToggle.markAsClosed();
            }
        }
    }

    public void toggleTaskStateForId(String id) {
        for (Task task : tasks) {
            if (task.getID().equals(id)) {
                if (task.isClosed()) {
                    task.markAsOpen();
                } else {
                    task.markAsClosed();
                }
                return;
            }
        }
    }

    public ArrayList<Task> getCurrentTasks() {
        ArrayList<Task> currentTasks = new ArrayList<>();
        for (Task task : tasks) {
            currentTasks.add(task.copy());
        }
        Collections.sort(currentTasks);
        return currentTasks;
    }
}
