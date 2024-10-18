package com.sid.voicenot;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private LinearLayout tasksContainer;
    private int taskCount = 4;  // Initial number of tasks

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tasksContainer = findViewById(R.id.tasksContainer);
        Button addTaskButton = findViewById(R.id.addTaskButton);

        // Add more rows dynamically when the plus button is clicked
        addTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                taskCount++;
                addTaskRow(taskCount);
            }
        });
    }

    // Method to dynamically add a new task row
    private void addTaskRow(int taskNumber) {
        EditText newTask = new EditText(this);
        newTask.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        newTask.setHint("Task " + taskNumber);
        newTask.setPadding(12, 12, 12, 12);
        newTask.setTextSize(16);

        // Set background tint using setBackgroundTintList()
        newTask.setBackgroundTintList(getResources().getColorStateList(R.color.primaryColor));

        tasksContainer.addView(newTask);  // Add the new task row to the container
    }
}
