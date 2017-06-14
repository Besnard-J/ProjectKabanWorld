package com.example.shiro.projectkabanworld_v2.Dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.shiro.projectkabanworld_v2.Project;

import java.util.LinkedList;
import java.util.List;

import static com.example.shiro.projectkabanworld_v2.Dao.TaskDAO.COLUMN_NAME_IDPROJECT;
import static com.example.shiro.projectkabanworld_v2.Dao.TaskDAO.TABLE_TASK;


public class ProjectDAO {

    public static final String TABLE_PROJECT = "Project";
    private static final String COLUMN_NAME_ID = "ID";
    private static final String COLUMN_NAME_NAME = "Name";
    private static final String COLUMN_NAME_RESUME = "Resume";
    private static final String COLUMN_NAME_ETAT = "Etat";

    public static void create(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_PROJECT + "("
                + COLUMN_NAME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_NAME_NAME + " TEXT NOT NULL, "
                + COLUMN_NAME_RESUME + " TEXT NOT NULL, "
                + COLUMN_NAME_ETAT + " TEXT NOT NULL )");

    }


    public static void dropProject(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROJECT);
    }

    public static void insertProject(SQLiteDatabase db, String n, String r, String e) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME_NAME, n);
        values.put(COLUMN_NAME_RESUME, r);
        values.put(COLUMN_NAME_ETAT, e);
        db.insert(TABLE_PROJECT, null, values);
    }


    public static void deleteProject(SQLiteDatabase db, int id) {

        String myQuery = "SELECT * FROM " + TABLE_PROJECT + " INNER JOIN " + TABLE_TASK + " ON (" + TABLE_PROJECT + "." + COLUMN_NAME_ID + "=" + TABLE_TASK + "." + COLUMN_NAME_IDPROJECT + ") " +
                "WHERE " + COLUMN_NAME_IDPROJECT + " = '" + id + "'";

        Cursor curs = db.rawQuery(myQuery, null);
        curs.moveToFirst();

        if (curs.getCount() == 0) {
            db.execSQL("DELETE FROM " + TABLE_PROJECT + " WHERE " + COLUMN_NAME_ID + " = '" + id + "'");
        } else {
            db.execSQL("DELETE FROM " + TABLE_TASK + " WHERE " + COLUMN_NAME_IDPROJECT + " = '" + id + "'");
            db.execSQL("DELETE FROM " + TABLE_PROJECT + " WHERE " + COLUMN_NAME_ID + " = '" + id + "'");
        }
    }


    public static void modifTable(SQLiteDatabase db, String n1, String r1, String e1, int id) {
        ContentValues modif = new ContentValues();
        modif.put(COLUMN_NAME_NAME, n1);
        modif.put(COLUMN_NAME_RESUME, r1);
        modif.put(COLUMN_NAME_ETAT, e1);
        db.update(TABLE_PROJECT, modif, "ID = '" + id + "'", null);
    }

    public static int getID(SQLiteDatabase db, String Name) {
        String myQuery = "SELECT " + COLUMN_NAME_ID + " FROM " + TABLE_PROJECT + " WHERE " + COLUMN_NAME_NAME + "= '" + Name + "'";
        Cursor curs = db.rawQuery(myQuery, null);
        curs.moveToFirst();

        return curs.getInt(0);
    }

    public static List<Project> getAll(SQLiteDatabase db) {
        List<Project> lstProjetDB = new LinkedList<Project>();

        String query = "SELECT  * FROM " + TABLE_PROJECT;

        Cursor cursor = db.rawQuery(query, null);

        Project projet = null;
        if (cursor.moveToFirst()) {
            do {
                projet = new Project();
                projet.setID(cursor.getInt(0));
                projet.setNameProject(cursor.getString(1));
                projet.setTxtProject(cursor.getString(2));
                projet.setProjectEtat(cursor.getString(3));

                lstProjetDB.add(projet);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return lstProjetDB;
    }
}
