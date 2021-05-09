package de.ur.mi.android.demos.todo.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import de.ur.mi.android.demos.todo.R;
import de.ur.mi.android.demos.todo.tasks.Task;
import de.ur.mi.android.demos.todo.ui.viewholder.TaskListViewHolder;

public class TaskListRecyclerAdapter extends RecyclerView.Adapter<TaskListViewHolder> implements TaskListViewHolder.TaskListViewHolderListener {

    private static final int VIEW_TYPE_FOR_OPEN_ITEMS = 1;
    private static final int VIEW_TYPE_FOR_CLOSED_ITEMS = 2;

    private final TaskListAdapterListener listener;
    private ArrayList<Task> tasks;

    public TaskListRecyclerAdapter(TaskListAdapterListener listener) {
        this.listener = listener;
        this.tasks = new ArrayList<>();
    }

    public void setTasks(ArrayList<Task> tasks) {
        this.tasks = tasks;
        this.notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        Task task = tasks.get(position);
        if (task == null || task.isClosed()) {
            return VIEW_TYPE_FOR_CLOSED_ITEMS;
        }
        return VIEW_TYPE_FOR_OPEN_ITEMS;
    }

    @NonNull
    @Override
    public TaskListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        TaskListViewHolder vh = createViewHolderForType(parent, viewType);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull TaskListViewHolder holder, int position) {
        Task task = tasks.get(position);
        TextView description = holder.taskView.findViewById(R.id.list_item_description);
        TextView createdAt = holder.taskView.findViewById(R.id.list_item_creationDate);
        description.setText(task.getDescription());
        createdAt.setText(getFormattedDateForUI(task.getCreationDate()));
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    private TaskListViewHolder createViewHolderForType(ViewGroup parent, int viewType) {
        View v;
        if (viewType == VIEW_TYPE_FOR_CLOSED_ITEMS) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_list_item_done, parent, false);
        } else {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_list_item, parent, false);
        }
        TaskListViewHolder vh = new TaskListViewHolder(v, this);
        return vh;
    }

    private String getFormattedDateForUI(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
        Date now = new Date();
        long timeDifferenceInMilliseconds = Math.abs(now.getTime() - date.getTime());
        long timeDifferenceInDays = TimeUnit.DAYS.convert(timeDifferenceInMilliseconds, TimeUnit.MILLISECONDS);
        if (timeDifferenceInDays > 0) {
            sdf = new SimpleDateFormat("dd. MMMM", Locale.getDefault());
        }
        return sdf.format(date);
    }

    @Override
    public void onViewHolderLongClicked(int position) {
        Task task = tasks.get(position);
        if (task != null) {
            listener.onItemSelected(task);
        }
    }

    public interface TaskListAdapterListener {
        void onItemSelected(Task task);
    }

}

