package com.example.studenttaskmanagerapp;


import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private final ArrayList<Task> taskList;
    private final OnTaskActionListener listener;

    public interface OnTaskActionListener {
        void onTaskDeleted(int position);
        void onTaskCompleted(int position, boolean completed);
    }

    public TaskAdapter(ArrayList<Task> taskList, OnTaskActionListener listener) {
        this.taskList = taskList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_task, parent, false);

        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {

        Task task = taskList.get(position);

        holder.textViewTitle.setText(task.getTitle());
        holder.textViewDescription.setText(task.getDescription());

        holder.checkBoxCompleted.setOnCheckedChangeListener(null);
        holder.checkBoxCompleted.setChecked(task.isCompleted());

        updateTaskAppearance(holder, task.isCompleted());

        holder.checkBoxCompleted.setOnCheckedChangeListener((buttonView, isChecked) -> {

            task.setCompleted(isChecked);

            updateTaskAppearance(holder, isChecked);

            listener.onTaskCompleted(position, isChecked);
        });

        holder.buttonDelete.setOnClickListener(v -> {

            listener.onTaskDeleted(holder.getAdapterPosition());

        });
    }

    private void updateTaskAppearance(TaskViewHolder holder, boolean completed) {

        if (completed) {

            holder.textViewTitle.setPaintFlags(
                    holder.textViewTitle.getPaintFlags()
                            | Paint.STRIKE_THRU_TEXT_FLAG
            );

            holder.textViewDescription.setPaintFlags(
                    holder.textViewDescription.getPaintFlags()
                            | Paint.STRIKE_THRU_TEXT_FLAG
            );

        } else {

            holder.textViewTitle.setPaintFlags(
                    holder.textViewTitle.getPaintFlags()
                            & (~Paint.STRIKE_THRU_TEXT_FLAG)
            );

            holder.textViewDescription.setPaintFlags(
                    holder.textViewDescription.getPaintFlags()
                            & (~Paint.STRIKE_THRU_TEXT_FLAG)
            );
        }
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    public static class TaskViewHolder extends RecyclerView.ViewHolder {

        TextView textViewTitle;
        TextView textViewDescription;
        CheckBox checkBoxCompleted;
        ImageButton buttonDelete;

        public TaskViewHolder(@NonNull View itemView) {

            super(itemView);

            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewDescription = itemView.findViewById(R.id.textViewDescription);
            checkBoxCompleted = itemView.findViewById(R.id.checkBoxCompleted);
            buttonDelete = itemView.findViewById(R.id.buttonDelete);
        }
    }
}
