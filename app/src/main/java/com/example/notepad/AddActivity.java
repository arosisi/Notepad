package com.example.notepad;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class AddActivity extends AppCompatActivity {

    private DBHandler dbHandler;
    private Button btnAdd, btnView;
    private EditText editText;
    private ImageView imageViewSleeping;
    private ImageView imageViewYawing;
    private ImageView imageViewGrumpy;
    private ImageView imageViewCurious;

    private int imageId = R.mipmap.ic_launcher_round;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        btnAdd = findViewById(R.id.buttonAdd);
        btnView = findViewById(R.id.buttonViewFromAdd);
        editText = findViewById(R.id.editTextEnter);
        imageViewSleeping = findViewById(R.id.imageViewSleepingAdd);
        imageViewYawing = findViewById(R.id.imageViewYawningAdd);
        imageViewGrumpy = findViewById(R.id.imageViewGrumpyAdd);
        imageViewCurious = findViewById(R.id.imageViewCuriousAdd);
        dbHandler = new DBHandler(this, null, null, 1);

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

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newEntry = editText.getText().toString();
                if (editText.length() != 0) {
                    addNote(newEntry, imageId);
                    editText.setText("");
                } else {
                    toastMessage("You need to type something");
                }
            }
        });

        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    public void addNote(String newEntry, int imageId){
        boolean result = dbHandler.addNote(new Note(newEntry, imageId));
        if (result) {
            toastMessage("Note successfully added");
        } else {
            toastMessage("Note not successfully added");
        }
    }

    private void toastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
