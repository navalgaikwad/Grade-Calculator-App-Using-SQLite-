package edu.uncc.inclass12;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class DataBaseManager {
    Context context;
    SQLiteDatabase db;
    SQLHelper helper;
    GradeDAO gradeDAO;

    public DataBaseManager(Context context) {
        this.context = context;
        helper = new SQLHelper(context);
        db = helper.getWritableDatabase();
        gradeDAO = new GradeDAO(db);
    }

    public GradeDAO getGradeDAO() {
        return gradeDAO;
    }
}

