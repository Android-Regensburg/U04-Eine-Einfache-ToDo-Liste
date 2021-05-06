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

public class TaskListAdapter extends ArrayAdapter<Task> implements View.OnLongClickListener {

    private ArrayList<Task> tasks;
    private TaskListAdapterListener listener;

    public TaskListAdapter(@NonNull Context context) {
        super(context, android.R.layout.simple_list_item_1);
    }

    public void setTasks(ArrayList<Task> tasks) {
        this.tasks = tasks;
        this.notifyDataSetChanged();
    }

    public void setTaskListAdapterListener(TaskListAdapterListener listener) {
        this.listener = listener;
    }

    private View inflateViewTask(int resource, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(resource, parent, false);
        view.setOnLongClickListener(this);
        return view;
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
        viewForTask.setTag(R.string.task_id_tag_for_views, position);
        return viewForTask;
    }

    @Override
    public int getCount() {
        if (tasks == null) {
            return 0;
        }
        return tasks.size();
    }

    @Override
    public boolean onLongClick(View v) {
        Object positionTag = v.getTag(R.string.task_id_tag_for_views);
        if (positionTag != null) {
            int position = (Integer) positionTag;
            Task selectedTask = tasks.get(position);
            if (selectedTask != null) {
                if (listener != null) {
                    listener.onTaskSelected(selectedTask);
                    return true;
                }
            }
        }
        return false;
    }
}
