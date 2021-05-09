package de.ur.mi.android.demos.todo.ui.viewholder;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TaskListViewHolder extends RecyclerView.ViewHolder {

    public final View taskView;
    public final TaskListViewHolderListener viewHolderListener;

    public TaskListViewHolder(@NonNull View itemView, TaskListViewHolderListener listener) {
        super(itemView);
        this.viewHolderListener = listener;
        taskView = itemView;
        taskView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                viewHolderListener.onViewHolderLongClicked(getAdapterPosition());
                return true;
            }
        });
    }

    public interface TaskListViewHolderListener {
        void onViewHolderLongClicked(int position);
    }


}
