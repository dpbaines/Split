package com.davidbaines.split.dbmanager;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

public class DbInterface {

    Context context;
    DbHandler dbHandler;
    SQLiteDatabase db;

    public DbInterface(Context context) {
        this.context = context;
        dbHandler = new DbHandler(context);
        db = dbHandler.getWritableDatabase();
        //dbHandler.onCreate(db);
    }

    public void resetDatabase() {
        ArrayList<String> days = getDays();
        db.execSQL("DROP TABLE WORKOUTS;");
        for(int i = 0; i<days.size(); i++) {
            db.execSQL("DROP TABLE " + days.get(i) + ";");
        }
        dbHandler.onCreate(db);
    }

    public void addNewWorkoutDay(String dayName, int max) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + dayName.replaceAll(" ", "_") + " (" +
                DbHandler.WORKOUT_ID + "INTEGER PRIMARY_KEY, " +
                DbHandler.WORKOUT_NAME + "TEXT, " +
                DbHandler.WORKOUT_MAX + " INTEGER, " +
                DbHandler.WORKOUT_REP + " INTEGER);");
        db.execSQL("INSERT INTO WORKOUTS (name, max) VALUES ('" + dayName + "', " + max + ");");
        Log.d("SQLINFO", dayName.replaceAll(" ", "_") + " added");
    }

    public void addNewWorkout(String day, String name, int max, int rep) {
        db.execSQL("INSERT INTO " + day +
                " VALUES ('" + name + "', " + max + ", " + rep + ");");
    }

    public void deleteWorkout(String day, String name) {
        db.execSQL("DELETE FROM " + day.replaceAll(" ", "_") + " WHERE name='" + name + "';");
    }

    public void deleteDay(String day) {
        db.execSQL("DELETE FROM WORKOUTS WHERE name='" + day + "';");
        db.execSQL("DROP TABLE " + day.replaceAll(" ", "_") + ";");
    }

    public int getField(String day, String workout, String column) {

        String selection = "";

        SQLiteDatabase readDb = dbHandler.getReadableDatabase();
        Cursor cursor = readDb.rawQuery("SELECT name, " + column + " FROM " + day.replaceAll(" ", "_") + " WHERE name=" + workout, null);
        int data = cursor.getInt(0);
        cursor.close();

        return data;
    }

    public ArrayList<String> getDays() {
        ArrayList<String> days = new ArrayList<>();

        SQLiteDatabase readDb = dbHandler.getReadableDatabase();
        Cursor cursor = readDb.rawQuery("SELECT name FROM WORKOUTS", null);
        int i = 0;
        while(cursor.moveToNext()) {
            int index;

            index = cursor.getColumnIndexOrThrow("name");
            days.add(cursor.getString(index));
            Log.d("SQLINFO", "Name: " + cursor.getString(index));
        }

        return days;
    }

    public ArrayList<Integer> getMax() {
        ArrayList<Integer> days = new ArrayList<>();

        SQLiteDatabase readDb = dbHandler.getReadableDatabase();
        Cursor cursor = readDb.rawQuery("SELECT max FROM WORKOUTS", null);
        int i = 0;
        while(cursor.moveToNext()) {
            int index;

            index = cursor.getColumnIndexOrThrow("max");
            days.add(cursor.getInt(index));
        }

        return days;
    }

    public String[] getWorkouts(String day) {
        ArrayList<String> workouts = new ArrayList<>();

        SQLiteDatabase readDb = dbHandler.getReadableDatabase();
        Cursor cursor = readDb.rawQuery("SELECT name FROM " + day.replaceAll(" ", "_"), null);
        int i = 0;
        while(cursor.moveToNext()) {
            int index;

            index = cursor.getColumnIndexOrThrow("name");
            workouts.add(cursor.getString(index));
        }

        return (String[]) workouts.toArray();
    }
}
