package com.example.books;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class MyDataBaseHelper extends SQLiteOpenHelper{
    private Context context;
    public static final String DATABASE_NAME="baza.db";
    public static final int DATABASE_VERSION=1;

    public MyDataBaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null , DATABASE_VERSION);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_TABLE = "CREATE TABLE " +
                "tabela" + " (" +
                "id" + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name" + " TEXT NOT NULL, " +
                "author" + " TEXT NOT NULL, " +
                "rating" + " REAL NOT NULL, " +
                "year" + " TEXT NOT NULL, " +
                "summary" + " TEXT NOT NULL, " +
                "img" + " BLOB NOT NULL " +
                ");";

        db.execSQL(SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + "tabela");
        onCreate(db);
    }

    void addBook(String name, String author, Float rating, String year, String summary, byte[] img){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv=new ContentValues();

        cv.put("name",name);
        cv.put("author",author);
        cv.put("rating",rating);
        cv.put("year",year);
        cv.put("summary",summary);
        cv.put("img",img);
        //dodavat se u bazata
        long result=db.insert("tabela",null,cv);
        if(result==-1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(context, "Added succ", Toast.LENGTH_SHORT).show();
        }
    }

    Cursor readAllData(){
        String query="SELECT * FROM "+"tabela";
        SQLiteDatabase db=this.getWritableDatabase();

        Cursor cursor=null;
        if(db!=null){
            cursor=db.rawQuery(query,null);
        }
        return cursor;
    }
}