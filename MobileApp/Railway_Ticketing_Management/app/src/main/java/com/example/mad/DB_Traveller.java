package com.example.mad;



import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


//Traveller Database  table functions
public class DB_Traveller extends SQLiteOpenHelper {

    //  Properties for traveller table
    public static final String DATABASE_NAME = "Emp_man.db";
    public static final String TABLE_NAME = "Traveller_details";
    public static final String COL_1 = "TravellerID";
    public static final String COL_2 = "Name";
    public static final String COL_3 = "Address";
    public static final String COL_4 = "TelephoneNo";
    public static final String COL_5 = "Nation";
    public static final String COL_6 = "Gender";
    public DB_Traveller(Context context) {
        super(context, DATABASE_NAME, null, 4);  // Change the version to 2

        SQLiteDatabase sqLiteOpenHelper=this.getWritableDatabase();
    }

    //Create Traveller table
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_NAME + "(TravellerID text primary key,Name text, Address text  ,TelephoneNo integer, Nation text, Gender text)");


    }

    //  Drop table
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);

    }//insert Traveller Data
    public boolean insertData(String TravellerID, String Name, String Address, String TelephoneNo, String Nation, String Gender) {

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1,TravellerID);
        contentValues.put(COL_2, Name);
        contentValues.put(COL_3, Address);
        contentValues.put(COL_4, TelephoneNo);
        contentValues.put(COL_5, Nation);
        contentValues.put(COL_6, Gender);

        long result = sqLiteDatabase.insert(TABLE_NAME, null, contentValues);

        if (result == -1)
            return false;
        else
            return true;
    }

    //Get all Traveller data
    public Cursor getAllData(){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor result=sqLiteDatabase.rawQuery("select * from "+TABLE_NAME,null);
        return result;
    }

    //update Traveller Data
    public  boolean updateDetail(String TravellerID, String Name, String Address, String TelephoneNo,String Nation, String Gender){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put(COL_2, Name);
        contentValues.put(COL_3, Address);
        contentValues.put(COL_4, TelephoneNo);
        contentValues.put(COL_5, Nation);
        contentValues.put(COL_6, Gender);



        sqLiteDatabase.update(TABLE_NAME,contentValues,"TravellerID = ?",new String[] {TravellerID});
        return true;
    }

    //Delete Traveller Data
    public Integer deleteDetail (String TravellerID){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        return sqLiteDatabase.delete(TABLE_NAME,"TravellerID = ?",new String[] {TravellerID});
    }

    //Search Traveller Data
    public Cursor searchData(String TravellerID) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor data = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + COL_1 + "= '" + TravellerID + "'",null);
        return data;
    };


}
