package com.example.assigment2_22h1120116.DATABASE;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {
    private static final String DBNAME ="22H1120103_TRANHUYHOANG.db";
    public DbHelper(Context context){
        super(context,DBNAME,null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String qNguoidung = "CREATE TABLE NGUOIDUNG(username TEXT PRIMARY KEY, password TEXT, email TEXT)";
        db.execSQL(qNguoidung);

        String qSanpham = "CREATE TABLE SANPHAM(masp INTEGER PRIMARY KEY AUTOINCREMENT,tensp TEXT,dessp TEXT, giaban INTEGER, anhsp TEXT)";
        db.execSQL(qSanpham);



//        String dNguoidung ="INSERT INTO NGUOIDUNG VALUES('Yeta','123','Tan2709'),('tan','274','Tan2004')";
//        db.execSQL(dNguoidung);
//
       String dSanpham =" INSERT INTO SANPHAM VALUES(1,'CAFE G7', 'Cafe G7, một trong những loại cafe đang được thịnh hành top 1 tại VN.',20000,'https://res.cloudinary.com/dnwkflkwu/image/upload/v1713832758/wmihlvuba5nqqd8aes7g.jpg')," +
               "(2,'CAFE NAPOLI','Cafe Napoli, một trong những loại cafe đang được thịnh hành top 1 tại VN.',30000,'https://res.cloudinary.com/dnwkflkwu/image/upload/v1713832758/pb6nenrpolxiwrwrb1gf.jpg')," +
               "(3,'CAFE Phố','Cafe Phố, một trong những loại cafe đang được thịnh hành top 1 tại VN.',20000,'https://res.cloudinary.com/dnwkflkwu/image/upload/v1713832762/ji7q4dgga50cy22ereab.jpg')," +
               "(4,'CAFE Abrabica','Cafe Abrabica, một trong những loại cafe đang được thịnh hành top 1 tại VN. ',800000,'https://res.cloudinary.com/dnwkflkwu/image/upload/v1713832759/qe5oyddxkwy9qgwxajww.jpg')," +
               "(5,'CAFE Ethiopia','Cafe Ethiopia, một trong những loại cafe đang được thịnh hành top 1 tại VN.',100000,'https://res.cloudinary.com/dnwkflkwu/image/upload/v1713832758/hnmw9amru2n2jxaguum8.jpg')," +
               "(6,'CAFE Tây Nguyên','Cafe Tây Nguyên, một trong những loại cafe đang được thịnh hành top 1 tại VN.',90000,'https://res.cloudinary.com/dnwkflkwu/image/upload/v1713832760/j7pm0qdzercdkq6byejd.jpg')";
        db.execSQL(dSanpham);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(oldVersion != newVersion){
            db.execSQL("DROP TABLE IF EXISTS NGUOIDUNG ");
            db.execSQL("DROP TABLE IF EXISTS SANPHAM");
            onCreate(db);

        }
    }
}
