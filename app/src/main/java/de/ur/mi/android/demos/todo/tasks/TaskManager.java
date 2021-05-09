package de.ur.mi.android.demos.todo.tasks;

import java.util.ArrayList;
import java.util.Collections;

public class TaskManager {

    private final TaskManagerListener listener;
    private final ArrayList<Task> tasks;

    public TaskManager(TaskManagerListener listener) {
        this.listener = listener;
        this.tasks = new ArrayList<>();
    }

    public void addTask(String description) {
        Task taskToAdd = new Task(description);
        tasks.add(taskToAdd);
        listener.onTaskAdded(taskToAdd);
    }

    public void toggleTaskStateAtPosition(int position) {
        Task task = tasks.get(position);
        toggleTaskState(task);
    }

    public void toggleTaskStateForId(String id) {
        for (Task task : tasks) {
            if (task.getID().equals(id)) {
                toggleTaskState(task);
            }
        }
    }

    private void toggleTaskState(Task taskToToggle) {
        if (taskToToggle != null) {
            if (taskToToggle.isClosed()) {
                taskToToggle.markAsOpen();
            } else {
                taskToToggle.markAsClosed();
            }
            listener.onTaskChanged(taskToToggle);
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

    public interface TaskManagerListener {

        void onTaskAdded(Task task);

        void onTaskChanged(Task task);

    }
}
