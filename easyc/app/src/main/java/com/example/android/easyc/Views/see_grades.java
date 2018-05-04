package com.example.android.easyc.Views;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.android.easyc.Controllers.QuizController;
import com.example.android.easyc.Interfaces.OnTaskListeners;
import com.example.android.easyc.R;

import java.util.ArrayList;

public class see_grades extends AppCompatActivity {


    QuizController quizController;
    ArrayList<String> dates;
    ArrayList<Integer> grades;
    ArrayList<String> scores;
    ListView gradesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.activity_see_grades);
            gradesList = findViewById(R.id.grades_listview);
            dates = new ArrayList<String>();
            grades = new ArrayList<Integer>();
            scores = new ArrayList<String>();
            quizController = new QuizController();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        getGrades();



    }

    void getGrades() {

        quizController.getGrades(new OnTaskListeners.twoLists() {
            @Override
            public void onSuccess(ArrayList<Object> list1, ArrayList<Object> list2) {
                grades = (ArrayList<Integer>) (Object) list1;
                dates = (ArrayList<String>) (Object) list2;

                try {
                    if (grades.size() == dates.size()) {
                        for (int i = 0; i < grades.size(); i++) {
                           scores.add( grades.get(i).toString() + "    " + dates.get(i).toString());
                        }
                    }
                   ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_selectable_list_item, scores);
                    gradesList.setAdapter(adapter);
                }
                catch(Exception e)
                {e.printStackTrace();}


            }
        });

    }

}
