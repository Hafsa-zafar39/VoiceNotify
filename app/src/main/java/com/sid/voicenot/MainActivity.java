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
    private EditText taskDescription;
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
                int hour = timePicker.getHour(); // Use getHour() instead of getCurrentHour()
                int minute = timePicker.getMinute(); // Use getMinute() instead of getCurrentMinute()
                String task = taskDescription.getText().toString();
                String taskType = taskTypeSpinner.getSelectedItem().toString();

                if (!task.isEmpty()) {
                    String formattedTask = String.format("%02d:%02d - %s (%s)", hour, minute, task, taskType);
                    tasks.add(formattedTask);
                    adapter.notifyDataSetChanged();
                    taskDescription.setText(""); // Clear the input field
                }
            }
        });
    }
}
