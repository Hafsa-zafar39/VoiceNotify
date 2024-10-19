package com.sid.voicenot;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TimePicker;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private TimePicker timePicker;
    private EditText taskDescription, notes;
    private Spinner taskTypeSpinner;
    private Button submitButton;
    private ListView taskList;
    private ArrayList<String> tasks;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize UI components
        timePicker = findViewById(R.id.timePicker);
        taskDescription = findViewById(R.id.taskDescription);
        notes = findViewById(R.id.notes);  // Handle the notes input field
        taskTypeSpinner = findViewById(R.id.taskTypeSpinner);
        submitButton = findViewById(R.id.submitButton);
        taskList = findViewById(R.id.taskList);

        // Initialize task list
        tasks = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, tasks);
        taskList.setAdapter(adapter);

        // Set up the Spinner with task types
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.task_types, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        taskTypeSpinner.setAdapter(spinnerAdapter);

        // Set the TimePicker to 24-hour format
        timePicker.setIs24HourView(true);

        // Handle the Submit button click
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int hour = timePicker.getHour();
                int minute = timePicker.getMinute();
                String task = taskDescription.getText().toString();
                String taskNotes = notes.getText().toString();  // Get the notes from the user
                String taskType = taskTypeSpinner.getSelectedItem().toString();

                // Make sure that the task description is not empty before adding
                if (!task.isEmpty()) {
                    // Format the task with time, task, type, and notes
                    String formattedTask = String.format("%02d:%02d - %s (%s)\nNotes: %s", hour, minute, task, taskType, taskNotes);
                    tasks.add(formattedTask);  // Add the new task to the list
                    adapter.notifyDataSetChanged();  // Refresh the list to show the new task

                    // Clear the input fields after the task is added
                    taskDescription.setText("");
                    notes.setText("");
                    taskTypeSpinner.setSelection(0);  // Reset the spinner to the first item
                }
            }
        });
    }
}
