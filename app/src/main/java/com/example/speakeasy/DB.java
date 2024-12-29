package com.example.speakeasy;
import static android.content.ContentValues.TAG;
import static java.util.Currency.getInstance;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import org.mindrot.jbcrypt.BCrypt;

public class DB extends SQLiteOpenHelper {

    public static final String DBNAME="login.db";

    public DB(@Nullable Context context) {
        super(context,"login.db",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE users(email TEXT PRIMARY KEY, name TEXT, surname TEXT, phone TEXT, password TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("Drop table if exists users");
        onCreate(db);
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
        Cursor cursor=db.rawQuery("Select password from users where email=?",new String[]{email});

        if(cursor.moveToFirst()){
            String storedHashedPassword=cursor.getString(0);
            cursor.close();

            return BCrypt.checkpw(password,storedHashedPassword);
        }
        cursor.close();
        return false;
    }
    public Boolean insertUser(String email, String password, String name, String surname, String phone) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        // Hash the password before storing it
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

        contentValues.put("email", email);
        contentValues.put("password", hashedPassword);
        contentValues.put("name", name);
        contentValues.put("surname", surname);
        contentValues.put("phone", phone);

        // Attempt to insert into the database
        long result = db.insert("users", null, contentValues);

        // Log the result of the insert operation
        if (result == -1) {
            Log.e(TAG, "Error inserting user: " + email);
            return false;  // Insertion failed
        } else {
            Log.d(TAG, "User inserted successfully: " + email);
            return true;   // Insertion successful
        }
    }

    public boolean updateUserPassword(String email, String newPassword) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        String hashedPassword = BCrypt.hashpw(newPassword, BCrypt.gensalt()); // Hash the password
        values.put("password", hashedPassword);

        // Log the email being updated
        Log.d("DB", "Attempting to update password for email: " + email);

        int rows = db.update("users", values, "email = ?", new String[]{email});
        if (rows > 0) {
            Log.d("DB", "Password updated successfully for email: " + email);
            return true;
        } else {
            Log.d("DB", "Failed to update password. Email not found: " + email);
            return false;
        }
    }



}
