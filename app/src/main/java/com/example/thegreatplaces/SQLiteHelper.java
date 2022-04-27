package com.example.thegreatplaces;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class SQLiteHelper extends SQLiteOpenHelper {
    SQLiteDatabase db;
    public static final String db_name = "db_great";
    public static final String t_name = "t_great";
    public static final String r_id = "_id";
    public static final String r_title = "Title";
    public static final String r_image = "Image";
    public static final String r_date = "Date";
    public static final String r_address = "Address";



    public SQLiteHelper(@Nullable Context context) {
        super(context, db_name, null, 2);
        db = getWritableDatabase();
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE "+t_name+"("+r_id+" INTEGER PRIMARY KEY AUTOINCREMENT," +r_title +" TEXT , "+r_image +" BLOB, " +
                r_date +" TEXT,"+r_address +" TEXT)";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS "+ t_name);
    }

    // GET ALL SQLITE DATA
    public Cursor allData(){
        Cursor cursor = db.rawQuery("SELECT * FROM "+t_name+" ORDER BY "+r_id+" DESC ",null);
        return cursor;
    }
    public void insertData(ContentValues values){
        db.insert(t_name,null,values);
    }

    public void deleteData(long id){
        db.delete(t_name,r_id+ " = "+ id,null);
    }

    public Cursor oneData(long id){
        Cursor cursor = db.rawQuery("SELECT * FROM "+t_name+" WHERE "+r_id+" = " + id ,null);
        return cursor;
    }


}
