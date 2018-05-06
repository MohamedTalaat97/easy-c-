package com.example.android.easyc.Views.ExamViews;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.android.easyc.Controllers.QuizController;
import com.example.android.easyc.R;

public class quiz_options extends AppCompatActivity {

    QuizController quizController;

    Button Qcategory;
    Button Qlevel;
    Button Qup;
    Button Qgrades;
    int user_level;

    public static String opener = "lev";


    //  i.putExtra(CAT_ID, cat_id);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_options);
        quizController = new QuizController();
        Qcategory = findViewById(R.id.quiz_cat);
        Qlevel = findViewById(R.id.quiz_level);
        Qup = findViewById(R.id.quiz_up);
        Qgrades = findViewById(R.id.quiz_grades);

        if (user_level == 5) {
            Qup.setVisibility(View.INVISIBLE);

        }
        user_level = quizController.getUserLevel();

        Qcategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                byCategory();


            }
        });

        Qgrades.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                quizGrades();

            }
        });

        Qlevel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                quiz();


            }
        });

        Qup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                upLevel();

            }
        });
    }


    void byCategory()

    {
        opener = "cat";
        Intent intent = new Intent(getApplicationContext(), quiz_categories.class);
        startActivity(intent);
    }

    void quizGrades() {
        opener = "cat";
        Intent intent = new Intent(getApplicationContext(), see_grades.class);
        startActivity(intent);

    }

    void upLevel() {
        opener = "lev_up";
        Intent intent = new Intent(getApplicationContext(), quizzes.class);
        intent.putExtra(opener, user_level);
        startActivity(intent);
    }

    void quiz() {
        // adelo el level
        opener = "lev";
        Intent intent = new Intent(getApplicationContext(), quizzes.class);
        intent.putExtra(opener, user_level);
        startActivity(intent);
    }

    void goTo(Class c) {
        Intent intent = new Intent(getApplicationContext(), c);
        startActivity(intent);
        // overridePendingTransition(R.anim.goup, R.anim.godown);
    }
}
