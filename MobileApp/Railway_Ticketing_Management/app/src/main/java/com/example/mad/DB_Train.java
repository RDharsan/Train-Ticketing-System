package com.example.mad;



import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

//Database Train table
public class DB_Train extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Bus_manager.db";
    public static final String TABLE_NAME = "Bus";
    public static final String COL_1 = "TicketId";
    public static final String COL_2 = "Nic";
    public static final String COL_3 = "Pname";
    public static final String COL_4 = "TravCode";
    public static final String COL_5 = "TravDate";
    public static final String COL_6 = "TravSrc";
    public static final String COL_7 = "TravDest";
    public static final String COL_8 = "TravCost";

    public DB_Train(Context context) {
        super(context, DATABASE_NAME, null, 1);
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();


    }

    //Create table - Train
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_NAME + "(TicketId text primary key, Nic text , Pname text,TravCode text, TravDate text, TravSrc text, TravDest text,TravCost text)");
    }

    //Drop Table Train
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    //Insert Train data
    public boolean insertDetail(String TicketId, String Nic, String Pname, String TravCode, String TravDate, String TravSrc, String TravDest, String TravCost) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, TicketId);
        contentValues.put(COL_2, Nic);
        contentValues.put(COL_3, Pname);
        contentValues.put(COL_4, TravCode);
        contentValues.put(COL_5, TravDate);
        contentValues.put(COL_6, TravSrc);
        contentValues.put(COL_7, TravDest);
        contentValues.put(COL_8, TravCost);

        long result = sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
        Log.d("DB_Bus", "Insert result: " + result);

        if (result == -1)
            return false;
        else
            return true;
    }

    //Get all Train data
    public Cursor getAllData(){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor res=sqLiteDatabase.rawQuery("select * from "+TABLE_NAME,null);
        return res;
    }

    //Update Train data
    public  boolean updateDetail(String TicketId, String Nic, String Pname, String TravCode, String TravDate, String TravSrc, String TravDest, String TravCost){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put(COL_1, TicketId);
        contentValues.put(COL_2, Nic);
        contentValues.put(COL_3, Pname);
        contentValues.put(COL_4, TravCode);
        contentValues.put(COL_5, TravDate);
        contentValues.put(COL_6, TravSrc);
        contentValues.put(COL_7, TravDest);
        contentValues.put(COL_8, TravCost);

        sqLiteDatabase.update(TABLE_NAME,contentValues,"TicketId = ?",new String[] {TicketId});
        return true;
    }

    //delete Train data
    public Integer deleteDetail (String TicketId){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        return sqLiteDatabase.delete(TABLE_NAME,"TicketId = ?",new String[] {TicketId});
    }

    //Search Train data
    public Cursor searchDetail(String TicketId) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor data = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + COL_1 + "= '" + TicketId + "'",null);
        return data;
    };

}
