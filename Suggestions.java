package com.example.voicenot;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Switch;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Calendar;

public class Suggestions extends AppCompatActivity {
    private Switch enableSuggestionsSwitch;
    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggestions);

        // Initialize UI Components
        enableSuggestionsSwitch = findViewById(R.id.enableSuggestionsSwitch);
        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        // Enable/Disable Suggestions
        enableSuggestionsSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                startSuggestions(); // Start scheduled suggestions
                Toast.makeText(this, "Suggestions Enabled", Toast.LENGTH_SHORT).show();
            } else {
                stopSuggestions(); // Stop scheduled suggestions
                Toast.makeText(this, "Suggestions Disabled", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void startSuggestions() {
        // Create an intent to trigger the BroadcastReceiver
        Intent intent = new Intent(this, SuggestionReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        // Set the alarm to start after 5 minutes and repeat every 5 minutes
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.add(Calendar.MINUTE, 2); // Start after 5 minutes

        alarmManager.setRepeating(
                AlarmManager.RTC_WAKEUP, // Wake up the device
                calendar.getTimeInMillis(), // Start time
                2 * 60 * 1000, // Repeat interval (5 minutes)
                pendingIntent // PendingIntent to trigger
        );
    }

    private void stopSuggestions() {
        if (pendingIntent != null) {
            alarmManager.cancel(pendingIntent); // Cancel the alarm
        }
    }
}