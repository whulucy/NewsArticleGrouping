package edu.scu.xli2.photonotesplus;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by Xiaoyu on 5/10/16.
 */

public class PhotoDbHelper extends SQLiteOpenHelper {
    static private final int VERSION=5;
    static private final String DB_NAME="Photo_CursorAdaptor.db";
    public static final String DATABASE_TABLE = "photo";

    static private final String SQL_CREATE_TABLE =
            "CREATE TABLE " +  DATABASE_TABLE +" (" +
                    "  _id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "  description TEXT," +
                    "  photoPath TEXT," +
                    "  revisedPath TEXT," +
                    "  voiceFile TEXT," +
                    "  location TEXT )";


    static private final String SQL_DROP_TABLE = "DROP TABLE photo";
    Context context;

    public PhotoDbHelper(Context context) {
        super(context, DB_NAME, null, VERSION);     // we use default cursor factory (null, 3rd arg)
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // a simple crude implementation that does not preserve data on upgrade
        db.execSQL(SQL_DROP_TABLE);
        db.execSQL(SQL_CREATE_TABLE);

        Toast.makeText(context, "Upgrading DB and dropping data!!!", Toast.LENGTH_SHORT).show();
    }

    public int getMaxRecID() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT MAX(_id) FROM photo;", null);

        if (cursor.getCount() == 0) {
            return 0;
        } else {
            cursor.moveToFirst();
            return cursor.getInt(0);
        }
    }

    public Cursor fetchAll() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM photo;", null);
    }

    public void add(MyPhoto my_photo) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("description", my_photo.getDescription());
        contentValues.put("photoPath", my_photo.getPath());
        contentValues.put("revisedPath", my_photo.revisedPath);
        contentValues.put("location",  my_photo.mLastLocation.toString());
        contentValues.put("voiceFile", my_photo.voiceFile);

        db.insert(DATABASE_TABLE, null, contentValues);
    }

    public void delete(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        db.delete("photo", "_id=?", new String[]{String.valueOf(id)});
    }


}
