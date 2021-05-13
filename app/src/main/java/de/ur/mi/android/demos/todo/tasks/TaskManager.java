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

    public void createNewTask(String description) {
        Task task = new Task(description);
        tasks.add(task);
        listener.onTaskListUpdated();
    }

    public void toggleTaskStateAtPosition(int position) {
        Task task = tasks.get(position);
        toggleTaskState(task);
    }

    public void toggleTaskStateForId(String id) {
        for (Task task : tasks) {
            if (task.getID().toString().equals(id)) {
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
            listener.onTaskListUpdated();
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

        void onTaskListUpdated();

    }
}
