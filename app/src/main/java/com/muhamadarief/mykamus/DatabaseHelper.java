package com.muhamadarief.mykamus;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Muhamad Arief on 18/10/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "db_kamus";
    public static final String TABLE_NAME_ENG_IND = "dictionary_eng";
    public static final String TABLE_NAME_IND_ENG = "dictionary_ind";
    public static final String FIELD_ID = "id";
    public static final String FIELD_WORD = "word";
    public static final String FIELD_TRANSLATE = "translate";

    public static final int DATABASE_VERSION = 1;

    private static String CREATE_TABLE_ENG_IND = "CREATE TABLE "+TABLE_NAME_ENG_IND+" ("+FIELD_ID+" integer primary key autoincrement, " +
            FIELD_WORD + " text not null, " +
            FIELD_TRANSLATE + " text not null);";

    private static String CREATE_TABLE_IND_ENG = "CREATE TABLE "+TABLE_NAME_IND_ENG+" ("+FIELD_ID+" integer primary key autoincrement, " +
            FIELD_WORD + " text not null, " +
            FIELD_TRANSLATE + " text not null);";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_ENG_IND);
        sqLiteDatabase.execSQL(CREATE_TABLE_IND_ENG);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME_ENG_IND);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME_IND_ENG);
        onCreate(sqLiteDatabase);
    }
}
