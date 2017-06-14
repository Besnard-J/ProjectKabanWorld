package com.example.shiro.projectkabanworld_v2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.shiro.projectkabanworld_v2.Dao.ProjectDAO;
import com.example.shiro.projectkabanworld_v2.Dao.TaskDAO;


public class MySQLiteHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "myFirstDB.db";
    private static final int DB_VERSION = 8;

    public MySQLiteHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        ProjectDAO.create(db);
        TaskDAO.create(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
