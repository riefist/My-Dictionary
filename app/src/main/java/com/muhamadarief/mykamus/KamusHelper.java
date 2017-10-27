package com.muhamadarief.mykamus;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.muhamadarief.mykamus.entity.Dictionary;

import java.util.ArrayList;

/**
 * Created by Muhamad Arief on 18/10/2017.
 */

public class KamusHelper {
    private DatabaseHelper mHelper;
    private SQLiteDatabase db;
    private Context context;

    public KamusHelper(Context context) {
        this.context = context;
    }

    public KamusHelper open() throws SQLException {
        mHelper = new DatabaseHelper(context);
        db = mHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        db.close();
    }

    public void insertTransactionEng(ArrayList<Dictionary> arrayList) {
        String sql = "INSERT INTO " + DatabaseHelper.TABLE_NAME_ENG_IND + " (" + DatabaseHelper.FIELD_WORD + ", "+
        DatabaseHelper.FIELD_TRANSLATE + ") VALUES (?, ?)";
        db.beginTransaction();

        SQLiteStatement stmt = db.compileStatement(sql);
        for (int i=0; i < arrayList.size(); i++){
            stmt.bindString(1, arrayList.get(i).getWord());
            stmt.bindString(2, arrayList.get(i).getTranslate());
            stmt.execute();
            stmt.clearBindings();
        }

        db.setTransactionSuccessful();
        db.endTransaction();
    }

    public Cursor searchWordEng(String  word){
        return db.rawQuery("SELECT * FROM " +DatabaseHelper.TABLE_NAME_ENG_IND+ " WHERE " +DatabaseHelper.FIELD_WORD+" LIKE '"+word+"%'", null);
    }

    public ArrayList<Dictionary> getWordEng(String word){
        ArrayList<Dictionary> mResults = new ArrayList<>();
        Cursor cursor = searchWordEng(word);
        cursor.moveToFirst();
        Dictionary dictionary;
        if (cursor.getCount() > 0){
            do {
                dictionary = new Dictionary();
                dictionary.setWord(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.FIELD_WORD)));
                dictionary.setTranslate(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.FIELD_TRANSLATE)));

                mResults.add(dictionary);
                cursor.moveToNext();
            }
            while (!cursor.isAfterLast());
        }
        cursor.close();
        return mResults;
    }
    

    public void insertTransactionInd(ArrayList<Dictionary> arrayList) {
        String sql = "INSERT INTO " + DatabaseHelper.TABLE_NAME_IND_ENG + " (" + DatabaseHelper.FIELD_WORD + ", "+
                DatabaseHelper.FIELD_TRANSLATE + ") VALUES (?, ?)";
        db.beginTransaction();

        SQLiteStatement stmt = db.compileStatement(sql);
        for (int i=0; i < arrayList.size(); i++){
            stmt.bindString(1, arrayList.get(i).getWord());
            stmt.bindString(2, arrayList.get(i).getTranslate());
            stmt.execute();
            stmt.clearBindings();
        }

        db.setTransactionSuccessful();
        db.endTransaction();
    }

    public Cursor searchWord(String  word){
        return db.rawQuery("SELECT * FROM " +DatabaseHelper.TABLE_NAME_IND_ENG+ " WHERE " +DatabaseHelper.FIELD_WORD+" LIKE '"+word+"%'", null);
    }

    public ArrayList<Dictionary> getWordInd(String word){
        ArrayList<Dictionary> mResults = new ArrayList<>();
        Cursor cursor = searchWord(word);
        cursor.moveToFirst();
        Dictionary dictionary;
        if (cursor.getCount() > 0){
            do {
                dictionary = new Dictionary();
                dictionary.setWord(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.FIELD_WORD)));
                dictionary.setTranslate(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.FIELD_TRANSLATE)));

                mResults.add(dictionary);
                cursor.moveToNext();
            }
            while (!cursor.isAfterLast());
        }
        cursor.close();
        return mResults;
    }

}
