package com.example.shiro.projectkabanworld_v2.Dao;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.shiro.projectkabanworld_v2.Task;

import java.util.LinkedList;
import java.util.List;

public class TaskDAO {

    public static final String TABLE_TASK = "Task";
    public static final String COLUMN_NAME_IDPROJECT = "IdProject";
    private static final String COLUMN_NAME_ID = "ID";
    private static final String COLUMN_NAME_NAME = "Name";
    private static final String COLUMN_NAME_RESUME = "Resume";
    private static final String COLUMN_NAME_ETAT = "Etat";

    public static void create(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_TASK + "("
                + COLUMN_NAME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_NAME_NAME + " TEXT NOT NULL, "
                + COLUMN_NAME_RESUME + " TEXT NOT NULL, "
                + COLUMN_NAME_ETAT + " TEXT NOT NULL, "
                + COLUMN_NAME_IDPROJECT + " integer,"
                + " FOREIGN KEY (" + COLUMN_NAME_IDPROJECT + ") REFERENCES " + ProjectDAO.TABLE_PROJECT + "(" + COLUMN_NAME_ID + "))");
    }

    public static void dropTask(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASK);
    }

    public static void modifTable(SQLiteDatabase db, String n1, String r1, String e1, int id) {
        ContentValues modif = new ContentValues();
        modif.put(COLUMN_NAME_NAME, n1);
        modif.put(COLUMN_NAME_RESUME, r1);
        modif.put(COLUMN_NAME_ETAT, e1);
        db.update(TABLE_TASK, modif, "ID = '" + id + "'", null);
    }

    public static void insertTask(SQLiteDatabase db, String n, String r, String e, int id) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME_NAME, n);
        values.put(COLUMN_NAME_RESUME, r);
        values.put(COLUMN_NAME_ETAT, e);
        values.put(COLUMN_NAME_IDPROJECT, id);
        db.insert(TABLE_TASK, null, values);
    }

    public static void deleteTask(SQLiteDatabase db, int id, String name) {
        db.execSQL("DELETE FROM " + TABLE_TASK + " WHERE " + COLUMN_NAME_IDPROJECT + " = '" + id + "' AND " + COLUMN_NAME_NAME + " = '" + name + "'");
    }

    public static int getIDT(SQLiteDatabase db, String Name) {
        String myQuery = "SELECT " + COLUMN_NAME_ID + " FROM " + TABLE_TASK + " WHERE " + COLUMN_NAME_NAME + "= '" + Name + "'";
        Cursor curs = db.rawQuery(myQuery, null);
        curs.moveToFirst();
        int i = curs.getInt(0);
        curs.close();
        return i;
    }

    public static List<Task> getAll(SQLiteDatabase db, int id) {
        List<Task> lstTacheDB = new LinkedList<Task>();
        String query = "SELECT  * FROM " + TABLE_TASK + " WHERE " + COLUMN_NAME_IDPROJECT + " = " + id;
        Cursor cursor = db.rawQuery(query, null);

        Task tache = null;
        if (cursor.moveToFirst()) {
            do {
                tache = new Task();
                tache.setNameTask(cursor.getString(1));
                tache.setDescription(cursor.getString(2));
                tache.setTaskEtat(cursor.getString(3));

                lstTacheDB.add(tache);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return lstTacheDB;
    }
}
