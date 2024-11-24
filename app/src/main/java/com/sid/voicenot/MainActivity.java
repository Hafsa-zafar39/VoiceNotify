package com.sid.voicenot;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private TimePicker timePicker;
    private EditText taskDescription, notesBox;
    private Spinner taskTypeSpinner;
    private Button submitButton;
    private TableLayout taskTable;

    // Firebase Database reference
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize Firebase database reference
        databaseReference = FirebaseDatabase.getInstance().getReference("tasks");

        // Initialize UI components
        timePicker = findViewById(R.id.timePicker);
        taskDescription = findViewById(R.id.taskDescription);
        taskTypeSpinner = findViewById(R.id.taskTypeSpinner); // Spinner
        notesBox = findViewById(R.id.notesBox);
        submitButton = findViewById(R.id.submitButton);
        taskTable = findViewById(R.id.taskTable);

        // Set TimePicker to 24-hour format
        timePicker.setIs24HourView(true);

        // Handle Add Task button click
        submitButton.setOnClickListener(v -> {
            String description = taskDescription.getText().toString().trim();
            String notes = notesBox.getText().toString().trim();
            String taskType = taskTypeSpinner.getSelectedItem().toString(); // Get selected item
            int hour = timePicker.getHour();
            int minute = timePicker.getMinute();
            String taskTime = String.format("%02d:%02d", hour, minute);

            // Validate inputs
            if (description.isEmpty()) {
                showToast("Please enter a task description");
                return;
            }
            if (taskType.isEmpty()) {
                showToast("Please select a task type");
                return;
            }
            if (notes.isEmpty()) {
                showToast("Please enter task notes");
                return;
            }

            // Add task to the table
            addTaskToTable(description, taskTime, taskType, notes);

            // Save task to Firebase
            saveTaskToFirebase(description, taskTime, taskType, notes);

            // Clear form fields
            clearFormFields();
        });
    }

    private void addTaskToTable(String description, String time, String type, String notes) {
        TableRow row = new TableRow(this);
        row.setLayoutParams(new TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT));

        row.addView(createTableCell(description));
        row.addView(createTableCell(time));
        row.addView(createTableCell(type));
        row.addView(createTableCell(notes));

        taskTable.addView(row);
    }

    private TextView createTableCell(String text) {
        TextView cell = new TextView(this);
        cell.setText(text);
        cell.setPadding(16, 8, 16, 8);
        return cell;
    }

    private void clearFormFields() {
        taskDescription.setText("");
        taskTypeSpinner.setSelection(0); // Reset to the first item
        notesBox.setText("");
        timePicker.setHour(0);
        timePicker.setMinute(0);
    }

    private void saveTaskToFirebase(String description, String time, String type, String notes) {
        String taskId = databaseReference.push().getKey();
        if (taskId != null) {
            HashMap<String, String> taskData = new HashMap<>();
            taskData.put("description", description);
            taskData.put("time", time);
            taskData.put("type", type);
            taskData.put("notes", notes);

            databaseReference.child(taskId).setValue(taskData)
                    .addOnSuccessListener(aVoid -> showToast("Task saved successfully"))
                    .addOnFailureListener(e -> showToast("Failed to save task: " + e.getMessage()));
        }
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
