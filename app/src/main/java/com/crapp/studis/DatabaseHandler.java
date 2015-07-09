package com.crapp.studis;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "classroomsManager";

    // Classrooms table name
    private static final String TABLE_CLASSROOMS = "classrooms";

    // Classrooms Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_SERVER_ID = "server_id";
    private static final String KEY_NAME = "name";
    private static final String KEY_SUBJECT = "subject";
    private static final String KEY_IMAGE_PATH = "image_path";
    private static final String KEY_CREATED_AT = "created_at";
    private static final String KEY_LAST_UPDATED_AT = "last_updated_at";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CLASSROOMS_TABLE = "CREATE TABLE " + TABLE_CLASSROOMS + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_SERVER_ID + " TEXT,"
                + KEY_NAME + " TEXT,"
                + KEY_SUBJECT + " TEXT,"
                + KEY_IMAGE_PATH + " TEXT,"
                + KEY_CREATED_AT + " TEXT,"
                + KEY_LAST_UPDATED_AT + " TEXT" + ")";
        db.execSQL(CREATE_CLASSROOMS_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CLASSROOMS);

        // Create tables again
        onCreate(db);
    }

    // Adding new classroom
    public void addClassroom(Classroom classroom) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_SERVER_ID, classroom.getClassroomServerID()); // Classroom ServerID
        values.put(KEY_NAME, classroom.getClassroomName()); // Classroom Name
        values.put(KEY_SUBJECT,classroom.getClassroomSubject()); // Cassroom Subject
        values.put(KEY_IMAGE_PATH, classroom.getClassroomImagePath()); // Classroom Image Path
        values.put(KEY_CREATED_AT, classroom.getCreatedAt()); // Created At
        values.put(KEY_LAST_UPDATED_AT, classroom.getLastUpdateAt()); // Last Updated AT

        // Inserting Row
        db.insert(TABLE_CLASSROOMS, null, values);
        db.close(); // Closing database connection
    }

    // Getting single classroom
    public Classroom getClassroom(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_CLASSROOMS, new String[] { KEY_ID,
                        KEY_SERVER_ID, KEY_NAME, KEY_SUBJECT, KEY_IMAGE_PATH, KEY_CREATED_AT, KEY_LAST_UPDATED_AT }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Classroom classroom = new Classroom(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4),
                cursor.getString(5),cursor.getString(6));
        // return contact
        return classroom;
    }

    // Getting All Classrooms
    public List<Classroom> getAllClassroom() {
        List<Classroom> classroomList = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_CLASSROOMS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Classroom classroom = new Classroom();
                classroom.setClassroomID(Integer.parseInt(cursor.getString(0)));
                classroom.setClassroomServerID(cursor.getString(1));
                classroom.setClassroomName(cursor.getString(2));
                classroom.setClassroomSubject(cursor.getString(3));
                classroom.setClassroomImagePath(cursor.getString(4));
                classroom.setCreatedAt(cursor.getString(5));
                classroom.setLastUpdateAt(cursor.getString(6));
                // Adding contact to list
                classroomList.add(classroom);
            } while (cursor.moveToNext());
        }

        // return contact list
        return classroomList;
    }

    // Getting classrooms Count
    public int getClassroomCount() {
        String countQuery = "SELECT  * FROM " + TABLE_CLASSROOMS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }

    // Updating single classroom
    public int updateClassroom(Classroom classroom) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_SERVER_ID, classroom.getClassroomServerID());
        values.put(KEY_NAME, classroom.getClassroomName());
        values.put(KEY_SUBJECT,classroom.getClassroomSubject());
        values.put(KEY_IMAGE_PATH, classroom.getClassroomImagePath());
        values.put(KEY_CREATED_AT, classroom.getCreatedAt());
        values.put(KEY_LAST_UPDATED_AT, classroom.getLastUpdateAt());

        // updating row
        return db.update(TABLE_CLASSROOMS, values, KEY_ID + " = ?",
                new String[]{String.valueOf(classroom.getClassroomID())});
    }

    // Deleting single classroom
    public void deleteClassroom(Classroom classroom) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CLASSROOMS, KEY_ID + " = ?",
                new String[]{String.valueOf(classroom.getClassroomID())});
        db.close();
    }

}
