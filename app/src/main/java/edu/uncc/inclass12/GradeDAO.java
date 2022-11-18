package edu.uncc.inclass12;

import android.app.DownloadManager;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class GradeDAO {
    SQLiteDatabase db;

    public GradeDAO(SQLiteDatabase db) {
        this.db = db;
    }

    public long save(Grade grade) {
        ContentValues values = new ContentValues();
        values.put(GradeTable.COLUMN_COURSE_ID, grade.getCourseId());
        values.put(GradeTable.COLUMN_COURSE_NO, grade.getCourseNo());
        values.put(GradeTable.COLUMN_COURSE_NAME, grade.getCourseName());
        values.put(GradeTable.COLUMN_GRADE, grade.getGrade());
        values.put(GradeTable.COLUMN_CREDIT_HR, grade.getCreditHr());
        values.put(GradeTable.COLUMN_CREATED_BY_UID, grade.getCreated_by_uid());
        values.put(GradeTable.COLUMN_GRADE_GPA, grade.getGpa());
        values.put(GradeTable.COLUMN_GRADE_POINTS, grade.getGradePoints());
       // values.put(GradeTable.COLUMN_GRADE_POINT_TOTAL, grade.getGradePointTotal());
      //  values.put(GradeTable.COLUMN_TOTAL_HR, grade.getTotalhr());

        return db.insert(GradeTable.TABLE_NAME, null, values);
       // return 0;
    }

    public boolean update(Grade grade) {
        ContentValues values = new ContentValues();
        values.put(GradeTable.COLUMN_COURSE_ID, grade.getCourseId());
        values.put(GradeTable.COLUMN_COURSE_NO, grade.getCourseNo());
        values.put(GradeTable.COLUMN_COURSE_NAME, grade.getCourseName());
        values.put(GradeTable.COLUMN_GRADE, grade.getGrade());
        values.put(GradeTable.COLUMN_CREDIT_HR, grade.getCreditHr());
        values.put(GradeTable.COLUMN_CREATED_BY_UID, grade.getCreated_by_uid());
        values.put(GradeTable.COLUMN_GRADE_GPA, grade.getGpa());
        values.put(GradeTable.COLUMN_GRADE_POINTS, grade.getGradePoints());
        values.put(GradeTable.COLUMN_GRADE_POINT_TOTAL, grade.getGradePointTotal());
        values.put(GradeTable.COLUMN_TOTAL_HR, grade.getTotalhr());
        return db.update(GradeTable.TABLE_NAME, values, GradeTable.COLUMN_ID + "=?", new String[]{grade.getCourseId() + ""}) > 0;
      //  db.update(GradeTable.TABLE_NAME, null, null, null);
       // return false;
    }

    public boolean delete(String grade) {
        return db.delete(GradeTable.TABLE_NAME, GradeTable.COLUMN_ID + "=?", new String[]{grade + ""}) > 0;
        //return false;
    }

    public Grade get(String id) {
        Grade grade = null;
       Cursor cursor= db.query(true, GradeTable.TABLE_NAME, new String[]{GradeTable.COLUMN_ID,
                 GradeTable.COLUMN_COURSE_NO, GradeTable.COLUMN_COURSE_NAME,
                GradeTable.COLUMN_GRADE, GradeTable.COLUMN_CREDIT_HR, GradeTable.COLUMN_CREATED_BY_UID,
                GradeTable.COLUMN_GRADE_GPA, GradeTable.COLUMN_GRADE_POINTS, GradeTable.COLUMN_GRADE_POINT_TOTAL,
                GradeTable.COLUMN_TOTAL_HR}, GradeTable.COLUMN_ID + "=?", new String[]{id},
                null, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
           // grade = buildGradeFromCursor(cursor);
            if (!cursor.isClosed()) {
                cursor.close();
            }
        }

        return grade;
    }
    Grade buildGradeFromCursor(Cursor cursor) {
        Grade grade = null;
        if (cursor != null) {
            grade = new Grade();
            grade.setCourseId(cursor.getString(0));
            grade.setCourseNo(cursor.getString(1));
            grade.setCourseName(cursor.getString(2));
            grade.setGrade(cursor.getString(3));
            grade.setCreditHr(cursor.getString(4));
            grade.setCreated_by_uid(cursor.getString(5));
            grade.setGpa(Double.parseDouble(cursor.getString(6)));
            grade.setGradePoints(cursor.getInt(7));
            grade.setGradePointTotal(cursor.getInt(8));
            grade.setTotalhr(cursor.getInt(9));
        }
        return grade;
    }

    public ArrayList<Grade> getAll() {
        ArrayList<Grade> gradeArrayList = new ArrayList<Grade>();

        ;
        Cursor cursor= db.query(true, GradeTable.TABLE_NAME, new String[]{GradeTable.COLUMN_ID,
                         GradeTable.COLUMN_COURSE_NO, GradeTable.COLUMN_COURSE_NAME,
                        GradeTable.COLUMN_GRADE, GradeTable.COLUMN_CREDIT_HR, GradeTable.COLUMN_CREATED_BY_UID,
                        GradeTable.COLUMN_GRADE_GPA, GradeTable.COLUMN_GRADE_POINTS, GradeTable.COLUMN_GRADE_POINT_TOTAL,
                        GradeTable.COLUMN_TOTAL_HR}, null, null,
                null, null, null, null, null);
        while (cursor.moveToNext()) {
            Grade grade=new Grade();
            grade = buildGradeFromCursor(cursor);
            gradeArrayList.add(grade);
        }
        return gradeArrayList;
    }
}
