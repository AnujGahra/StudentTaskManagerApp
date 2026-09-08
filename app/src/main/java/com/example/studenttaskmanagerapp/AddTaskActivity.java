package com.example.studenttaskmanagerapp;



import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

public class AddTaskActivity extends AppCompatActivity {

    private TextInputEditText editTextTaskTitle;
    private TextInputEditText editTextTaskDescription;
    private Button buttonSaveTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_task);

        editTextTaskTitle = findViewById(R.id.editTextTaskTitle);
        editTextTaskDescription = findViewById(R.id.editTextTaskDescription);
        buttonSaveTask = findViewById(R.id.buttonSaveTask);

        buttonSaveTask.setOnClickListener(v -> saveTask());
    }

    private void saveTask() {

        String title = editTextTaskTitle.getText().toString().trim();
        String description = editTextTaskDescription.getText().toString().trim();

        if (title.isEmpty()) {
            editTextTaskTitle.setError("Please enter a task title");
            editTextTaskTitle.requestFocus();
            return;
        }

        if (description.isEmpty()) {
            editTextTaskDescription.setError("Please enter a description");
            editTextTaskDescription.requestFocus();
            return;
        }

        Intent resultIntent = new Intent();

        resultIntent.putExtra("task_title", title);
        resultIntent.putExtra("task_description", description);

        setResult(RESULT_OK, resultIntent);

        finish();
    }
}
