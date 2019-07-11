package com.example.opentriviaquizapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "records.db";
    private static final String DATABASE_USERS_TABLE = "users";

    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_USER_NAME = "author";

    DBHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query1 = "CREATE TABLE "+ DATABASE_USERS_TABLE + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_USER_NAME + " TEXT );";

        db.execSQL(query1);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ DATABASE_USERS_TABLE);
        onCreate(db);
    }

    boolean userExists(String userName){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor =  db.rawQuery("select * from "+ DATABASE_USERS_TABLE +
                " WHERE " + COLUMN_USER_NAME + " = \"" +  userName + "\"" +
                " order by " + COLUMN_ID + " desc limit 1", null);
        return cursor != null && cursor.moveToFirst();
    }

    boolean createUser(String name){
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_USER_NAME, name.trim());

        SQLiteDatabase db = getWritableDatabase();
        db.insert(DATABASE_USERS_TABLE, null, contentValues);
        db.close();
        return true;
    }
}
