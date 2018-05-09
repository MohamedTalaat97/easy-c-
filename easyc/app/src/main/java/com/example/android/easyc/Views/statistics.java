package com.example.android.easyc.Views;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.android.easyc.Controllers.StatisticsController;
import com.example.android.easyc.Interfaces.OnTaskListeners;
import com.example.android.easyc.R;

import java.util.ArrayList;

public class statistics extends AppCompatActivity {

    Spinner spinner;
    StatisticsController statisticsController;
    TextView textView;

    String numberOfStudents = "Number of student";
    String numberOfInstructors = "Number of instructors";
    String numberOfReports = "Number of reports";
    String numberOfOpinions = "Number of opinions";
    String avgGradeOfStudents = "Get The average grade for student in exams";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        spinner = findViewById(R.id.statisticsOption);
        textView = findViewById(R.id.statisticsId);
        statisticsController = new StatisticsController();

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                updateText();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


 setSpinner();
    }

    void setSpinner()
    {
        ArrayList<String> list =new ArrayList<String>();
        if(statisticsController.getUserData().getUserType() == 'I')
        {
            list.add(avgGradeOfStudents);
            list.add(numberOfStudents);

        }
        else
        {
            list.add(numberOfStudents);
            list.add(numberOfInstructors);
            list.add(numberOfReports);
            list.add(numberOfOpinions);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,list);
                spinner.setAdapter(adapter);
    }


    void updateText()
    {
        String option = spinner.getSelectedItem().toString();
        if(option.matches(avgGradeOfStudents))
        {
            statisticsController.getTheAverageGradeForStudents(new OnTaskListeners.Number() {
                @Override
                public void onSuccess(int result) {
                    textView.setText(String.valueOf(result));
                }
            });
        }
        else if(option.matches(numberOfStudents))
        {
            statisticsController.getNumberOfStudent(new OnTaskListeners.Number() {
                @Override
                public void onSuccess(int result) {
                    textView.setText(String.valueOf(result));
                }
            });
        }
        else if(option.matches(numberOfInstructors))
        {
            statisticsController.getNumberOfInstructors(new OnTaskListeners.Number() {
                @Override
                public void onSuccess(int result) {
                    textView.setText(String.valueOf(result));
                }
            });
        }

        else if(option.matches(numberOfReports))
        {
            statisticsController.getNumberOfReports(new OnTaskListeners.Number() {
                @Override
                public void onSuccess(int result) {
                    textView.setText(String.valueOf(result));
                }
            });
        }

        else if(option.matches(numberOfOpinions))
        {
            statisticsController.getNumberOfOpinions(new OnTaskListeners.Number() {
                @Override
                public void onSuccess(int result) {
                    textView.setText(String.valueOf(result));
                }
            });
        }
    }
}
