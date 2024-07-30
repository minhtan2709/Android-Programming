package com.example.assigment2_22h1120116.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.assigment2_22h1120116.DATABASE.DbHelper;
import com.example.assigment2_22h1120116.model.Product;

import java.util.ArrayList;

public class SanPhamDao {
    private DbHelper dbHelper;
    public SanPhamDao(Context context){
        dbHelper = new DbHelper(context);

    }
    // lay danh sach san pham
    public ArrayList<Product> getDS(){
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        ArrayList<Product> list = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM SANPHAM",null);
        if(cursor.getCount()>0){
            cursor.moveToFirst();
            do{
                list.add(new Product(cursor.getInt(0),cursor.getString(1),  cursor.getString(2),cursor.getInt(3), cursor.getString(4)));
            }
            while(cursor.moveToNext());
        }
        return list;
    }
    // them san pham
    public boolean themSPmoi(Product product){
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("tensp",product.getTensp());
        contentValues.put("dessp",product.getDessp());
        contentValues.put("giaban",product.getGiaban());
        contentValues.put("anhsp",product.getAnhsp());

        long check =  sqLiteDatabase.insert("SANPHAM",null,contentValues);
        if(check == -1){
            return false;

        }
        else{
            return true;
        } //hoac co the nhap return check!=-1;
    }
    public boolean chinhsuaSP(Product product){
        SQLiteDatabase sqLiteDatabase  = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("tensp",product.getTensp());
        contentValues.put("dessp",product.getDessp());
        contentValues.put("giaban",product.getGiaban());
        contentValues.put("anhsp",product.getAnhsp());



        int check = sqLiteDatabase.update("SANPHAM", contentValues,"masp = ?",new String[]{String.valueOf(product.getMasp())});
        if(check  <= 0){
            return false;
        }
        else{
            return true;
        }

    }
    public boolean XoaSp(int masp){
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();

        int check = sqLiteDatabase.delete("SANPHAM","masp = ?",new String[]{String.valueOf(masp)});
        if(check<=0){
            return false;
        }
        else{
            return true;
        }
    }
}
