package com.sid.voicenot;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private LinearLayout formContainer, notesContainer;
    private TimePicker timePicker;
    private EditText taskDescription, notesBox;
    private Spinner taskTypeSpinner;
    private Button submitButton;
    private TableLayout taskTable;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize containers and form components
        formContainer = findViewById(R.id.formContainer);
        notesContainer = findViewById(R.id.notesContainer);
        timePicker = findViewById(R.id.timePicker);
        taskDescription = findViewById(R.id.taskDescription);
        taskTypeSpinner = findViewById(R.id.taskTypeSpinner);
        submitButton = findViewById(R.id.submitButton);
        notesBox = findViewById(R.id.notesBox);
        taskTable = findViewById(R.id.taskTable);

        // Set OnClickListener for formContainer and notesContainer
        formContainer.setOnClickListener(v -> {
            formContainer.setVisibility(View.VISIBLE);
            notesContainer.setVisibility(View.GONE);
        });

        notesContainer.setOnClickListener(v -> {
            formContainer.setVisibility(View.GONE);
            notesContainer.setVisibility(View.VISIBLE);
        });

        // Handle form submission to add tasks to the table
        submitButton.setOnClickListener(v -> {
            String description = taskDescription.getText().toString();
            String notes = notesBox.getText().toString();
            int hour = timePicker.getCurrentHour();
            int minute = timePicker.getCurrentMinute();
            String taskTime = hour + ":" + (minute < 10 ? "0" + minute : minute);

            if (!description.isEmpty()) {
                addTaskToTable(description, taskTime, taskTypeSpinner.getSelectedItem().toString(), notes);
            } else {
                Toast.makeText(MainActivity.this, "Please enter a task description", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addTaskToTable(String description, String time, String type, String notes) {
        TableRow row = new TableRow(this);

        TextView descriptionView = new TextView(this);
        descriptionView.setText(description);
        row.addView(descriptionView);

        TextView timeView = new TextView(this);
        timeView.setText(time);
        row.addView(timeView);

        TextView typeView = new TextView(this);
        typeView.setText(type);
        row.addView(typeView);

        TextView notesView = new TextView(this);
        notesView.setText(notes);
        row.addView(notesView);

        taskTable.addView(row);
    }
}
