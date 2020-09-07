package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.wearable.activity.WearableActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends WearableActivity {

    private ListView listView;

    int REQUEST_CODE = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.list_item);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                if (position == 0) {
                    displaySpeechScreen();
                } else {
                    Note note = (Note) adapterView.getItemAtPosition(position);
                    Intent intent = new Intent(getApplicationContext(), DeleteActivity.class);
                    intent.putExtra("id", note.getId());
                    startActivity(intent);
                }
            }
        });

        updateUI();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateUI();
    }

    public void updateUI() {
        ArrayList<Note> notes = Helper.getAllNotes(this);
        notes.add(0, new Note("", ""));
        listView.setAdapter(new ListviewAdapter(this, 0, notes));
    }

    public void displaySpeechScreen() {

        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                "Voice recognition Demo...");
        String defaultLanguage = Locale.getDefault().toString();
        Log.d("bdhfjsbj",defaultLanguage);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale
                .getDefault());
        startActivityForResult(intent,REQUEST_CODE );

//        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
//        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
//        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "What is the Title");
//        startActivityForResult(intent, 1001);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE && requestCode == RESULT_OK) {
            List<String> results = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            String message = results.get(0);
            Log.d("message", message);
            Note note = new Note(null, message);

            Helper.saveNote(note, this);
            Helper.displayConfirmation("Note Saved", this);
            updateUI();
        }
        else {
            Toast.makeText(getApplicationContext(), "it's not working", Toast.LENGTH_SHORT).show();
        }
    }
}
