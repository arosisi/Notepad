package com.example.notepad;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "noteDB.db";
    private static final String TABLE_NOTES = "notes";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_CONTENT = "content";
    public static final String COLUMN_IMAGE_ID = "image_id";

    public DBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_NOTES_TABLE = "CREATE TABLE " + TABLE_NOTES + " ( " + COLUMN_ID + " INTEGER PRIMARY KEY, " + COLUMN_CONTENT + " TEXT, " + COLUMN_IMAGE_ID + " INTEGER " + ")";
        db.execSQL(CREATE_NOTES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTES);
        onCreate(db);
    }

    public boolean addNote(Note note) {
        boolean result = false;
        ContentValues values = new ContentValues();
        values.put(COLUMN_CONTENT, note.getContent());
        values.put(COLUMN_IMAGE_ID, note.getImageId());
        SQLiteDatabase db = this.getWritableDatabase();
        if (db.insert(TABLE_NOTES, null, values) != -1) {
            result = true;
        }
        db.close();
        return result;
    }

    public boolean deleteNote(Note note) {
        boolean result = false;
        int id = note.getId();
        SQLiteDatabase db = this.getWritableDatabase();
        if (db.delete(TABLE_NOTES, COLUMN_ID + " = ?", new String[] { String.valueOf(id) }) != -1) {
            result = true;
        }
        db.close();
        return result;
    }

    public boolean editNote(Note note, String newEntry, int newImageId) {
        boolean result = false;
        int id = note.getId();
        ContentValues values = new ContentValues();
        values.put(COLUMN_CONTENT, newEntry);
        values.put(COLUMN_IMAGE_ID, newImageId);
        SQLiteDatabase db = this.getWritableDatabase();
        if (db.update(TABLE_NOTES, values, COLUMN_ID + " = ?", new String[] { String.valueOf(id) }) != -1) {
            result = true;
        }
        db.close();
        return result;
    }

    public ArrayList<Note> getNotes() {
        ArrayList<Note> notes = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_NOTES;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                Note note = new Note();
                note.setId(cursor.getInt(cursor.getColumnIndex(DBHandler.COLUMN_ID)));
                note.setContent(cursor.getString(cursor.getColumnIndex(DBHandler.COLUMN_CONTENT)));
                note.setImageId(cursor.getInt(cursor.getColumnIndex(DBHandler.COLUMN_IMAGE_ID)));
                notes.add(note);
            } while (cursor.moveToNext());
        }
        db.close();
        return notes;
    }
}
