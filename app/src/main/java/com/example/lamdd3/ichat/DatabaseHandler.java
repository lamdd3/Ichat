package com.example.lamdd3.ichat;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.HashMap;

/**
 * Created by LamDD3 on 4/19/2016.
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    //Phiên bản Database (Version)
    private static final int DATABASE_VERSION = 1;

    //Tên Database
    private static final String DATABASE_NAME = "Sql";

    //Tên table đăng nhập
    private static final String TABLE_LOGIN = "acc";

    // Login Table Columns names
    private static final String KEY_ID = "ID";
    private static final String KEY_PASS = "Pass";
    private static final String KEY_EMAIL = "Email";
    private static final String KEY_NOTE = "Note";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

        //add user to database
    public void addUser(String email, String pass) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        //Thêm giá tị EMAIL
        values.put(KEY_EMAIL, email);
        //Thêm giá trị Pass
        values.put(KEY_PASS, pass);
        //Chèn thêm row vào table
        db.insert(TABLE_LOGIN, null, values);
        //Đóng kết nối với database
        db.close();
    }

    //Lấy dữ liệu người dùng trong database
    public HashMap<String, String> getUserDetails(){
        HashMap<String,String> user = new HashMap<String,String>();
        String selectQuery = "SELECT  * FROM " + TABLE_LOGIN;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        //Di chuyển tới row đầu tiên
        cursor.moveToFirst();
        if(cursor.getCount() > 0){
            user.put("email", cursor.getString(1));
            user.put("pass", cursor.getString(2));
        }
        cursor.close();
        db.close();
        return user;
    }

    //Xem trạng thái đăng nhập, trả về số row trong table
    public int getRowCount() {
        String countQuery = "SELECT  * FROM " + TABLE_LOGIN;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int rowCount = cursor.getCount();
        db.close();
        cursor.close();
        return rowCount;
    }
    //Thực hiện xóa tất cả row trong table
    public void resetTables(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_LOGIN, null, null);
        db.close();
    }
}
