package com.example.opentriviaquizapp.system;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 3;
    private static final String DATABASE_NAME = "records.db";

    private static final String DATABASE_USERS_TABLE = "users";
    private static final String DATABASE_SCORES_TABLE = "scores";
    private static final String DATABASE_PRIZES_TABLE = "prizes";

    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_USER_NAME = "user";

    private static final String COLUMN_USER_SCORE = "score";
    private static final String COLUMN_QUIZ_CATEGORY_ID = "categoryid";
    private static final String COLUMN_QUIZ_DIFFICULTY_ID = "difficultyid";

    private static final String COLUMN_PRIZE_ID = "prizeid";
    private static final String COLUMN_PRIZE_NAME = "prizename";
    private static final String COLUMN_PRIZE_DESCRIPTION = "prizedescription";


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query1 = "CREATE TABLE "+ DATABASE_USERS_TABLE + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_USER_NAME + " TEXT );";

        String query2 = "CREATE TABLE "+ DATABASE_SCORES_TABLE + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_USER_NAME + " TEXT, " +
                COLUMN_USER_SCORE + " INTEGER, " +
                COLUMN_QUIZ_CATEGORY_ID + " INTEGER, " +
                COLUMN_QUIZ_DIFFICULTY_ID + " INTEGER );";

        String query3 = "CREATE TABLE "+ DATABASE_PRIZES_TABLE + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_USER_NAME + " TEXT , " +
                COLUMN_PRIZE_ID + " INTEGER, " +
                COLUMN_PRIZE_NAME + " TEXT , " +
                COLUMN_PRIZE_DESCRIPTION + " TEXT );";

        db.execSQL(query1);
        db.execSQL(query2);
        db.execSQL(query3);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ DATABASE_USERS_TABLE);
        db.execSQL("DROP TABLE IF EXISTS "+ DATABASE_SCORES_TABLE);
        db.execSQL("DROP TABLE IF EXISTS "+ DATABASE_PRIZES_TABLE);
        onCreate(db);
    }

    public boolean userExists(String userName){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor =  db.rawQuery("select * from "+ DATABASE_USERS_TABLE +
                " WHERE " + COLUMN_USER_NAME + " = \"" +  userName + "\"" +
                " order by " + COLUMN_ID + " desc limit 1", null);
        return cursor != null && cursor.moveToFirst();
    }

    public boolean createUser(String name){
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_USER_NAME, name.trim());

        SQLiteDatabase db = getWritableDatabase();
        db.insert(DATABASE_USERS_TABLE, null, contentValues);
        db.close();
        return true;
    }

    public boolean storeUserScore(String userName, int score, int categoryId, int difficultyId){
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_USER_NAME, userName.trim());
        contentValues.put(COLUMN_USER_SCORE, score);
        contentValues.put(COLUMN_QUIZ_CATEGORY_ID, categoryId);
        contentValues.put(COLUMN_QUIZ_DIFFICULTY_ID, difficultyId);

        SQLiteDatabase db = getWritableDatabase();
        db.insert(DATABASE_SCORES_TABLE, null, contentValues);
        db.close();
        return true;
    }

    public Cursor getPrevUserScores(String userName){

        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("select " + COLUMN_QUIZ_CATEGORY_ID +", " +
                        COLUMN_QUIZ_DIFFICULTY_ID + ", " +
                COLUMN_USER_SCORE + ", " + COLUMN_ID +
                " from "+ DATABASE_SCORES_TABLE +
                " where " + COLUMN_USER_NAME + " = \"" + userName.trim() + "\" " +
                " order by " + COLUMN_ID + " desc", null);
    }

    public boolean hasWonPrize(String username, int prizeid){

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor =  db.rawQuery("select " + COLUMN_PRIZE_ID + " from "+ DATABASE_PRIZES_TABLE +
                " WHERE " + COLUMN_USER_NAME + " = \"" +  username + "\"" +
                " AND " + COLUMN_PRIZE_ID + " = " + prizeid +
                " order by " + COLUMN_ID + " desc limit 1", null);
        return cursor != null && cursor.moveToFirst();
    }

    public boolean storePrize(String username, int prizeid, String prizeName, String prizeDescription){
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_USER_NAME, username.trim());
        contentValues.put(COLUMN_PRIZE_ID, prizeid);
        contentValues.put(COLUMN_PRIZE_NAME, prizeName);
        contentValues.put(COLUMN_PRIZE_DESCRIPTION, prizeDescription);

        SQLiteDatabase db = getWritableDatabase();
        db.insert(DATABASE_PRIZES_TABLE, null, contentValues);
        db.close();
        return true;
    }

    public Cursor getUserEarnedPrizes(String userName){

        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("select " + COLUMN_PRIZE_ID +", " +
                COLUMN_PRIZE_NAME + ", " +
                COLUMN_PRIZE_DESCRIPTION + ", " +
                COLUMN_ID +
                " from "+ DATABASE_PRIZES_TABLE +
                " where " + COLUMN_USER_NAME + " = \"" + userName.trim() + "\" " +
                " order by " + COLUMN_ID + " desc", null);
    }
}
