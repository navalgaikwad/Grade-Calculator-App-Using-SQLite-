package edu.uncc.inclass12;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements GradesFragment.GradeListener, AddCourseFragment.CreateGradeListener {

    DataBaseManager dm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dm = new DataBaseManager(this);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.rootView, new GradesFragment())
                .commit();
    }

    @Override
    public void goBackToGrade() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, new GradesFragment())
                .commit();
    }

    @Override
    public void addGradeFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, new AddCourseFragment())
                .commit();
    }
}