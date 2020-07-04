package com.example.docreader;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Locale;

public class TextToSpeechActivity extends AppCompatActivity {

    private TextToSpeech mTTS;
    private FloatingActionButton mButtonSpeak;

    EditText editText;
    String detectedText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_to_speech);

        Intent i = getIntent();
        detectedText = i.getStringExtra("text");

        editText = findViewById(R.id.detectedText);
        editText.setText(detectedText);

        mButtonSpeak = findViewById(R.id.speak_button);

        mTTS = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    int result = mTTS.setLanguage(Locale.ENGLISH);
                    if (result == TextToSpeech.LANG_MISSING_DATA  || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        //Log.e("TTS", "Language not supported");
                        Toast.makeText(TextToSpeechActivity.this, "AAA", Toast.LENGTH_SHORT).show();
                    } else {
                        //mButtonSpeak.setEnabled(true);
                    }
                } else {
                    //Log.e("TTS", "Initialization failed");
                    Toast.makeText(TextToSpeechActivity.this, "FAiled", Toast.LENGTH_SHORT).show();
                }
            }
        });

        mButtonSpeak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speak();
            }
        });
    }

    private void speak() {
        mTTS.setPitch(1);
        mTTS.setSpeechRate(1);
        mTTS.speak(detectedText,TextToSpeech.QUEUE_FLUSH,null,null);
        //mTTS.speak(detectedText, TextToSpeech.QUEUE_FLUSH, null);
    }
    @Override
    protected void onDestroy() {
        if (mTTS != null) {
            mTTS.stop();
            mTTS.shutdown();
        }
        super.onDestroy();
    }
}
