package com.example.assigment2_22h1120116.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.assigment2_22h1120116.DATABASE.DbHelper;

public class NguoiDungDao {
    private DbHelper dbHelper;

    public NguoiDungDao(Context context){
        dbHelper = new DbHelper(context);
    }
    //user
    public boolean Checkuser(String username){
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM NGUOIDUNG WHERE username = ?",new String []{username});
        if(cursor.getCount()>0){
            return true;

        }
        else{
            return false;
        }
    }
    //login
    public boolean CheckLogin(String username, String password){
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        Cursor cursor =sqLiteDatabase.rawQuery("SELECT * FROM NGUOIDUNG WHERE username = ? AND password = ?",new String []{username,password});
        if(cursor.getCount()>0){
            return true;

        }
        else{
            return false;
        }
    }
    //sign_up
    public boolean Sign_up(String username,String password,String email){
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("username",username);
        contentValues.put("password",password);
        contentValues.put("email",email);

        long check = sqLiteDatabase.insert("NGUOIDUNG",null, contentValues);
        if(check ==-1){
            return false;
        }
        else{
            return true;
        }



    }
    public boolean HoaDon(int madh,int masp,String tensp,int giaban,int soluong){
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("madonhang",madh);
        contentValues.put("masanpham",masp);
        contentValues.put("tensanpham",tensp);
        contentValues.put("giasanpham",giaban);
        contentValues.put("soluongsanpham",soluong);


        long check = sqLiteDatabase.insert("HOADON",null, contentValues);
        if(check ==-1){
            return false;
        }
        else{
            return true;
        }



    }
    //forgot
    public String ForgotPassword(String email){
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT password FROM NGUOIDUNG WHERE email=?",new String[]{email});
        if(cursor.getCount()>0){
            cursor.moveToFirst();
            return cursor.getString(0);

        }
        else{
            return "";
        }
    }


}
