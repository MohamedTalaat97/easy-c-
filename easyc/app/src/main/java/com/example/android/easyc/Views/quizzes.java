package com.example.android.easyc.Views;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.example.android.easyc.R;

import java.util.ArrayList;

public class quizzes extends AppCompatActivity {



    ListView quizListView;
    ArrayList<quiz> QuizList;
    int CategoryID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quizzes);



        quizListView=(ListView) findViewById(R.id.quiz_ListView);

        quiz_adapter a=new quiz_adapter(this,QuizList);

    }
}
