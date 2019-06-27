package com.davidbaines.split.dbmanager;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "workout_db";
    public static final String WORKOUT_ID = "id";
    public static final String WORKOUT_NAME = "name";
    public static final String WORKOUT_MAX = "max";
    public static final String WORKOUT_REP = "count";

    public DbHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS WORKOUTS (id INTEGER PRIMARY KEY, name TEXT, max INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Nothing implemented yet
    }
}
