package com.example.studenttaskmanagerapp;



import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerViewTasks;
    private TextView textViewEmpty;
    private FloatingActionButton fabAddTask;

    private ArrayList<Task> taskList;
    private TaskAdapter taskAdapter;

    private final ActivityResultLauncher<Intent> addTaskLauncher =
            registerForActivityResult(
                    new ActivityResultContracts.StartActivityForResult(),
                    result -> {

                        if (result.getResultCode() == RESULT_OK
                                && result.getData() != null) {

                            String title =
                                    result.getData().getStringExtra("task_title");

                            String description =
                                    result.getData().getStringExtra("task_description");

                            if (title != null && description != null) {

                                Task newTask = new Task(title, description);

                                taskList.add(newTask);

                                taskAdapter.notifyItemInserted(
                                        taskList.size() - 1
                                );

                                updateEmptyMessage();
                            }
                        }
                    }
            );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        recyclerViewTasks = findViewById(R.id.recyclerViewTasks);
        textViewEmpty = findViewById(R.id.textViewEmpty);
        fabAddTask = findViewById(R.id.fabAddTask);

        taskList = new ArrayList<>();

        setupRecyclerView();

        addSampleTasks();

        fabAddTask.setOnClickListener(v -> openAddTaskScreen());

        updateEmptyMessage();
    }

    private void setupRecyclerView() {

        taskAdapter = new TaskAdapter(
                taskList,
                new TaskAdapter.OnTaskActionListener() {

                    @Override
                    public void onTaskDeleted(int position) {

                        if (position != RecyclerView.NO_POSITION) {

                            taskList.remove(position);

                            taskAdapter.notifyItemRemoved(position);

                            updateEmptyMessage();
                        }
                    }

                    @Override
                    public void onTaskCompleted(
                            int position,
                            boolean completed) {

                        if (position != RecyclerView.NO_POSITION) {

                            taskList.get(position)
                                    .setCompleted(completed);
                        }
                    }
                }
        );

        recyclerViewTasks.setLayoutManager(
                new LinearLayoutManager(this)
        );

        recyclerViewTasks.setAdapter(taskAdapter);
    }

    private void addSampleTasks() {

        taskList.add(
                new Task(
                        "Learn Android Basics",
                        "Understand Activities, XML and Views"
                )
        );

        taskList.add(
                new Task(
                        "Practice Java",
                        "Revise OOP and Java fundamentals"
                )
        );

        taskList.add(
                new Task(
                        "Build Android Prototype",
                        "Create the Student Task Manager"
                )
        );

        taskAdapter.notifyDataSetChanged();
    }

    private void openAddTaskScreen() {

        Intent intent =
                new Intent(MainActivity.this, AddTaskActivity.class);

        addTaskLauncher.launch(intent);
    }

    private void updateEmptyMessage() {

        if (taskList.isEmpty()) {

            textViewEmpty.setVisibility(TextView.VISIBLE);

        } else {

            textViewEmpty.setVisibility(TextView.GONE);
        }
    }
}