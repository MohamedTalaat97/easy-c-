package com.example.android.easyc.Views;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.android.easyc.R;

import java.util.ArrayList;

public class Quizzes extends AppCompatActivity {

    ArrayList<Quiz> QuizList;
    int CategoryID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quizzes);
    }
}
