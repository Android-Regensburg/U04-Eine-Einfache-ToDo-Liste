package de.ur.mi.android.demos.todo.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import de.ur.mi.android.demos.todo.R;
import de.ur.mi.android.demos.todo.tasks.Task;

public class TaskListAdapter extends ArrayAdapter<Task> {

    private ArrayList<Task> tasks;

    public TaskListAdapter(@NonNull Context context) {
        super(context, android.R.layout.simple_list_item_1);
    }

    public void setTasks(ArrayList<Task> tasks) {
        this.tasks = tasks;
        this.notifyDataSetChanged();
    }

    private View inflateViewTask(int resource, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        return inflater.inflate(resource, parent, false);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Task task = tasks.get(position);
        View viewForTask = convertView;
        if (viewForTask == null) {
            viewForTask = inflateViewTask(R.layout.task_list_item, parent);
        }
        if (task != null) {
            if (task.isClosed()) {
                viewForTask = inflateViewTask(R.layout.task_list_item_done, parent);
            } else {
                viewForTask = inflateViewTask(R.layout.task_list_item, parent);
            }
            TextView taskDescription = viewForTask.findViewById(R.id.list_item_description);
            TextView taskCreationDate = viewForTask.findViewById(R.id.list_item_creationDate);
            SimpleDateFormat sdf = new SimpleDateFormat("dd. MMMM");
            String creationDateForUI = sdf.format(task.getCreationDate());
            taskDescription.setText(task.getDescription());
            taskCreationDate.setText(creationDateForUI);
        }
        return viewForTask;
    }

    @Override
    public int getCount() {
        if (tasks == null) {
            return 0;
        }
        return tasks.size();
    }
}
