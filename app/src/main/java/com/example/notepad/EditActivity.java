package com.example.notepad;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class EditActivity extends AppCompatActivity {

    private DBHandler dbHandler;
    private Button btnSave, btnView;
    private EditText editText;
    private ImageView imageViewSleeping;
    private ImageView imageViewYawing;
    private ImageView imageViewGrumpy;
    private ImageView imageViewCurious;

    private int imageId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        btnSave = findViewById(R.id.buttonSave);
        btnView = findViewById(R.id.buttonViewFromEdit);
        editText = findViewById(R.id.editTextEdit);
        imageViewSleeping = findViewById(R.id.imageViewSleepingEdit);
        imageViewYawing = findViewById(R.id.imageViewYawningEdit);
        imageViewGrumpy = findViewById(R.id.imageViewGrumpyEdit);
        imageViewCurious = findViewById(R.id.imageViewCuriousEdit);
        dbHandler = new DBHandler(this, null, null, 1);

        Intent intent = getIntent();
        final Note note = intent.getParcelableExtra(DBHandler.COLUMN_ID);
        editText.setText(note.getContent());
        editText.setSelection(editText.getText().length());
        imageId = note.getImageId();

        imageViewSleeping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageId = R.mipmap.sleeping_round;
                toastMessage("Sleeping cat selected");
            }
        });
        imageViewYawing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageId = R.mipmap.yawning_round;
                toastMessage("Yawning cat selected");
            }
        });
        imageViewGrumpy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageId = R.mipmap.grumpy_round;
                toastMessage("Grumpy cat selected");
            }
        });
        imageViewCurious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageId = R.mipmap.curious_round;
                toastMessage("Curious cat selected");
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newEntry = editText.getText().toString();
                if (editText.length() != 0) {
                    saveNote(note, newEntry);
                } else {
                    toastMessage("You need to type something");
                }
            }
        });

        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void saveNote(Note note, String newEntry) {
        boolean result = dbHandler.editNote(note, newEntry, imageId);
        if (result) {
            toastMessage("Note successfully saved");
        } else {
            toastMessage("Note not successfully saved");
        }
    }

    private void toastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
