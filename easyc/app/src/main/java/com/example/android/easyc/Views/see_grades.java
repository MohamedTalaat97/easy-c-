package com.example.android.easyc.Views;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.android.easyc.Controllers.QuizController;
import com.example.android.easyc.Interfaces.OnTaskListeners;
import com.example.android.easyc.R;

import java.util.ArrayList;

public class see_grades extends AppCompatActivity {


QuizController quizController;
    ArrayList<String> dates;
    ArrayList<Integer> grades;
    ListView gradesList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_grades);
        gradesList= findViewById(R.id.grades_listview);
        dates = new ArrayList<String>();
        grades = new ArrayList<Integer>();
        quizController = new QuizController();

        quizController.getGrades(new OnTaskListeners.twoLists() {
            @Override
            public void onSuccess(ArrayList<Object> list1, ArrayList<Object> list2) {
               grades = (ArrayList<Integer>) (Object) list1;
                dates = (ArrayList<String>) (Object) list2;
            }
        });

// see how to display it b2a :"
    }
}
