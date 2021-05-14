package de.ur.mi.android.demos.todo.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

import de.ur.mi.android.demos.todo.R;
import de.ur.mi.android.demos.todo.tasks.Task;

public class TaskListAdapter extends ArrayAdapter<Task> {

    private ArrayList<Task> tasks;

    public TaskListAdapter(@NonNull Context context) {
        super(context, android.R.layout.simple_list_item_1);
        tasks = new ArrayList<>();
    }

    public void setTasks(ArrayList<Task> tasks) {
        this.tasks = tasks;
        this.notifyDataSetChanged();
    }

    public Task getTaskAtPosition(int position) {
        return tasks.get(position);
    }

    private View inflateViewForTask(int layoutID, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        return inflater.inflate(layoutID, parent, false);
    }

    private void bindTaskToView(Task task, View view) {
        TextView taskDescription = view.findViewById(R.id.list_item_description);
        TextView taskCreationDate = view.findViewById(R.id.list_item_creationDate);
        taskDescription.setText(task.getDescription());
        // @TODO Formate date for better display in UI
        taskCreationDate.setText(task.getCreationDate().toString());
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Task task = tasks.get(position);
        // @TODO: Rework needed!
        View view = inflateViewForTask(R.layout.task_list_item, parent);
        if (task.isClosed()) {
            view = inflateViewForTask(R.layout.task_list_item_done, parent);
        }
        bindTaskToView(task, view);
        return view;
    }

    @Override
    public int getCount() {
        return tasks.size();
    }
}
