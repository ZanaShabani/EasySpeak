package com.example.speakeasy;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import org.mindrot.jbcrypt.BCrypt;

public class DB extends SQLiteOpenHelper {

    public static final String DBNAME="login.db";

    public DB(@Nullable Context context) {
        super(context,"login.db",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("Create Table users(email TEXT PRIMARY KEY, password TEXT)");
        db.execSQL("CREATE TABLE adminUser (email TEXT PRIMARY KEY, password TEXT, name TEXT,surname TEXT, phone TEXT)");
        db.execSQL("CREATE TABLE tasks(id INTEGER PRIMARY KEY AUTOINCREMENT, task_name TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("Drop table if exists users");
        db.execSQL("DROP TABLE IF EXISTS adminUser");
        onCreate(db);
    }

    public boolean insertTask(String task_name){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("task_name",task_name);

        long result=db.insert("tasks",null,contentValues);
        return result!= -1;

    }

    public Boolean checkEmail(String email){
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.rawQuery("Select * from users where email=?",new String[]{email});
        if (cursor.getCount()>0){
            return true;
        }else return false;

    }
    public Boolean validateUser(String email,String password){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery("Select password from adminUser where email=?",new String[]{email});

        if(cursor.moveToFirst()){
            String storedHashedPassword=cursor.getString(0);
            cursor.close();

            return BCrypt.checkpw(password,storedHashedPassword);
        }
        cursor.close();
        return false;
    }
    public Boolean insertAdminUser(String email, String password, String name,String surname, String phone) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        contentValues.put("email", email);
        contentValues.put("password", hashedPassword);
        contentValues.put("name", name);
        contentValues.put("surname", surname);
        contentValues.put("phone", phone);

        long result = db.insert("adminUser", null, contentValues);
        return result != -1;
    }

    // Check if admin user exists by email
    public Boolean checkAdminEmail(String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM adminUser WHERE email=?", new String[]{email});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }
}
