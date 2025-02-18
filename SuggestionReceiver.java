package com.example.voicenot;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.speech.tts.TextToSpeech;
import android.widget.Toast;
import java.util.Locale;
import java.util.Random;

public class SuggestionReceiver extends BroadcastReceiver {
    private TextToSpeech textToSpeech;

    @Override
    public void onReceive(Context context, Intent intent) {
        // Initialize TextToSpeech
        textToSpeech = new TextToSpeech(context, status -> {
            if (status == TextToSpeech.SUCCESS) {
                textToSpeech.setLanguage(Locale.US);
                String suggestion = getRandomSuggestion();
                speakSuggestion(suggestion); // Speak the suggestion
                Toast.makeText(context, suggestion, Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(context, "Text-to-Speech initialization failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String getRandomSuggestion() {
        String[] suggestions = {
                "Don't forget to drink water!",
                "Take a short break and stretch.",
                "Remember to take deep breaths.",
                "Stay positive and keep going!",
                "You're doing great! Keep it up."
        };
        Random random = new Random();
        return suggestions[random.nextInt(suggestions.length)];
    }

    private void speakSuggestion(String suggestion) {
        if (textToSpeech != null) {
            textToSpeech.speak(suggestion, TextToSpeech.QUEUE_FLUSH, null, null);
        }
    }
}