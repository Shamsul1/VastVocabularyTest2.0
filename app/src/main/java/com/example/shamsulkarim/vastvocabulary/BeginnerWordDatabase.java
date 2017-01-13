package com.example.shamsulkarim.vastvocabulary;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by sk on 1/1/17.
 */

public class BeginnerWordDatabase extends SQLiteOpenHelper{



    public static final String DATABASE_NAME = "BeginnerWordDatabase.db";
    public static final String TABLE_NAME = "beginner_table";
    public static final String COL1 = "ID";
    public static final String COL2 = "WORD";
    public static final String COL3 = "FAV";
    public static final String COL4 = "LEARNED";











    public BeginnerWordDatabase(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table "+TABLE_NAME+" (ID INTEGER PRIMARY KEY AUTOINCREMENT,WORD TEXT,FAV TEXT,LEARNED TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);

    }

    public boolean insertData(String wordNo, String fav, String learned){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL2,wordNo);
        cv.put(COL3,fav);
        cv.put(COL4,learned);


        long data = db.insert(TABLE_NAME,null,cv);

        if(data == -1){
            return false;
        }else {
            return true;
        }


    }

    public Cursor getData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_NAME,null);
        return res;


    }



    public boolean dbUpdate(String id, String newInt){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL1,id);
        cv.put(COL2,newInt);
        db.update(TABLE_NAME, cv, "ID = ?", new String[] {id});

        return true;
    }

    public boolean updateFav(String id, String fav){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL1,id);
        cv.put(COL3,fav);
        db.update(TABLE_NAME, cv, "ID = ?", new String[] {id});

        return true;
    }

    public boolean updateLearned(String id, String learned){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL1,id);
        cv.put(COL4,learned);
        db.update(TABLE_NAME, cv, "ID = ?", new String[] {id});

        return true;
    }


    public int deleteData(String id){
        SQLiteDatabase db = this.getWritableDatabase();

        return db.delete(TABLE_NAME, "ID = ?", new String[]{id});



    }

    public int getProfilesCount() {
        String countQuery = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int cnt = cursor.getCount();
        cursor.close();
        return cnt;
    }



}


