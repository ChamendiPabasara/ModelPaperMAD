package com.example.modelpaper;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;


public class DBhelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "UserInfo.db";
    public static final int DATABASE_VERSION = 3;

    public static final String SQL_CREATE_ENTRIES="CREATE TABLE IF NOT EXISTS "+UserProfile.Users.TABLE_NAME+"("+
    UserProfile.Users._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"+
    UserProfile.Users.COL_NAME + " TEXT,"+
    UserProfile.Users.COL_DOB + " TEXT,"+
    UserProfile.Users.COL_GENDER + " TEXT,"+
    UserProfile.Users.COL_PASSWORD + " TEXT)";



    public static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS "+ UserProfile.Users.TABLE_NAME;

    public DBhelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {

      //  db.execSQL(SQL_CREATE_ENTRIES);

        try{
            db.execSQL(SQL_CREATE_ENTRIES);
            Log.d("myerror", "Table create Successful!!!");


        }catch (Exception e){
            Log.d("myerror", "Table create Error!!!"+e.toString());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        try{
            db.execSQL(SQL_DELETE_ENTRIES);
            onCreate(db);
            Log.d("myerror", "Table drop Successful!!!");
        }catch (Exception e){
            Log.d("myerror", "Table drop Error!!!"+e.toString());
        }

    }
    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.setVersion(oldVersion);
    }

    //Supposed if question asked to implement add, update, read methods in this class
    //to check user when login

    public long checkUser(String userName,String password){

        SQLiteDatabase db =getReadableDatabase();

        //check if entered username and password exists in the users table
        String[] selectionArgs = {userName,password};

        String query = "SELECT * FROM " + UserProfile.Users.TABLE_NAME + " WHERE "+ UserProfile.Users.COL_NAME + "= ? AND " +
                UserProfile.Users.COL_PASSWORD + "= ?";

        Cursor cursor = db.rawQuery(query,selectionArgs);

        //if username and password match and row count is greater than 1 get that userId or else assign -1 to @useerId
        long userId = -1;
        if (cursor.moveToFirst()) {
            userId = (cursor.getCount() >= 1) ? cursor.getLong(0) : -1;
        }

        //if the user count greater than(shoul be equal to if properly check) 1 user exists and return true
        //return cursor.getCount() >= 1;

        //to login user from primary key

        return userId;

    }

    //register/add user to db table
    public long addInfo(String userName,String password) {

        SQLiteDatabase db=getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(UserProfile.Users.COL_NAME,userName);
        values.put(UserProfile.Users.COL_PASSWORD,password);

        return db.insert(UserProfile.Users.TABLE_NAME, null, values);


    }

    public boolean  updateInfor(long userId,String userName,String password,String dateOfBirth,String Gender)
    {
        SQLiteDatabase db=getReadableDatabase();
        //to check for the user to update
        String[] selectionArgs = {userId+""};
        String whereClause = UserProfile.Users._ID + " = ?";

        ContentValues values = new ContentValues();
        values.put(UserProfile.Users.COL_NAME,userName);
        values.put(UserProfile.Users.COL_PASSWORD,password);
        values.put(UserProfile.Users.COL_DOB,dateOfBirth);
        values.put(UserProfile.Users.COL_GENDER,Gender);

        return db.update(UserProfile.Users.TABLE_NAME, values, whereClause, selectionArgs) > 0;


    }

    public Cursor readAllInfor()
    {
        SQLiteDatabase db=getReadableDatabase();
        String[] projection ={
                UserProfile.Users._ID,
                UserProfile.Users.COL_NAME,
                UserProfile.Users.COL_DOB,
                UserProfile.Users.COL_GENDER,
                UserProfile.Users.COL_PASSWORD
        };

       // String sortOrder = UserProfile.Users.COL_NAME +" DESC";

        Cursor cursor =db.query(
                UserProfile.Users.TABLE_NAME,
          projection,
          null,
          null,
          null,
          null,
          null



        );

        //if the user count greater than(shoul be equal to if properly check) 1 user exists and return true
       return cursor;
    }

    public Cursor readAllInfor(long userId) {
        SQLiteDatabase db = getReadableDatabase();

        //retrieve the user using primary key
        String[] selectionArgs = {userId+""};
        String query = "SELECT * FROM " + UserProfile.Users.TABLE_NAME + " WHERE "+ UserProfile.Users._ID + " = ? ";

        Cursor cursor = db.rawQuery(query, selectionArgs);

        //if the user count greater than(shoul be equal to if properly check) 1 user exists and return true
        return cursor;
    }

    public boolean  deleteInfo(long userId)
    {
        SQLiteDatabase db=getReadableDatabase();
        String[] selectionArgs = {userId+""};
        String whereClause = UserProfile.Users._ID + " = ?";
        return db.delete(UserProfile.Users.TABLE_NAME, whereClause, selectionArgs) > 0;
    }
}
