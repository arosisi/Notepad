package com.example.notepad;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private DBHandler dbHandler;
    private ListView listView;
    private EditText editText;
    private FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);
        editText = findViewById(R.id.editTextSearch);
        floatingActionButton = findViewById(R.id.floatingActionButton);
        dbHandler = new DBHandler(this, null, null, 1);

        final ArrayList<Note> notes = dbHandler.getNotes();
        final CustomArrayAdapter customArrayAdapter = new CustomArrayAdapter(this, R.layout.note_layout, notes);
        listView.setAdapter(customArrayAdapter);

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                customArrayAdapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                PopupMenu popupMenu = new PopupMenu(MainActivity.this, view);
                popupMenu.getMenuInflater().inflate(R.menu.popup_menu, popupMenu.getMenu());
                popupMenu.show();

                final Note note = (Note) parent.getItemAtPosition(position);

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        String action = item.getTitle().toString();
                        if (action.equals(getString(R.string.menu_edit))) {
                            Intent intent = new Intent(MainActivity.this, EditActivity.class);
                            intent.putExtra(DBHandler.COLUMN_ID, note);
                            startActivity(intent);
                        } else if (action.equals(getString(R.string.menu_delete))) {
                            dbHandler.deleteNote(note);
                            notes.remove(note);
                            customArrayAdapter.notifyDataSetChanged();
                            toastMessage("Note successfully deleted");
                        } else {
                            return false;
                        }
                        return true;
                    }
                });
                return true;
            }
        });

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                startActivity(intent);
            }
        });
    }

    private void toastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
