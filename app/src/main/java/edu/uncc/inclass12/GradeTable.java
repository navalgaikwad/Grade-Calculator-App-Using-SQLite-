package edu.uncc.inclass12;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class GradeTable {
    public static final String TABLE_NAME = "grades";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_COURSE_ID = "courseId";
    public static final String COLUMN_COURSE_NO = "courseNo";
    public static final String COLUMN_COURSE_NAME = "courseName";
    public static final String COLUMN_GRADE = "grade";
    public static final String COLUMN_CREDIT_HR = "creditHr";
    public static final String COLUMN_CREATED_BY_UID = "created_by_uid";
    public static final String COLUMN_CREATED_AT = "created_at"; // timestamp
    public static final String COLUMN_GRADE_POINTS = "gradePoints"; // timestamp
    public static final String COLUMN_GRADE_GPA = "gpa"; // timestamp
    public static final String COLUMN_GRADE_POINT_TOTAL = "gradePointTotal"; // timestamp
    public static final String COLUMN_TOTAL_HR = "totalhr"; // timestamp

   // public static final String COLUMN_UPDATED_AT = "updated";

    public static final String SQL_CREATE = "CREATE TABLE " + TABLE_NAME + " (" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_COURSE_ID + " TEXT, " +
            COLUMN_COURSE_NO + " TEXT, " +
            COLUMN_COURSE_NAME + " TEXT, " +
            COLUMN_GRADE + " TEXT, " +
            COLUMN_CREDIT_HR + " TEXT, " +
            COLUMN_CREATED_BY_UID + " TEXT, " +
            COLUMN_CREATED_AT + " TEXT, " +
            COLUMN_GRADE_POINTS + " TEXT, " +
            COLUMN_GRADE_GPA + " TEXT, " +
            COLUMN_GRADE_POINT_TOTAL + " TEXT, " +
            COLUMN_TOTAL_HR + " TEXT " +
            ")";

    static public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(SQL_CREATE);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    static public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            GradeTable.onCreate(db);
        }catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
