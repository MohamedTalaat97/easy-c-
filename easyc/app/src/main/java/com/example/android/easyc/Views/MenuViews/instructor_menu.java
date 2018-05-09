package com.example.android.easyc.Views.MenuViews;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.easyc.Models.UserData;
import com.example.android.easyc.R;
import com.example.android.easyc.Views.ContentViews.add_options;
import com.example.android.easyc.Views.OpinionViews.put_opinion;
import com.example.android.easyc.Views.ExamViews.add_Question_Quiz;

public class instructor_menu extends AppCompatActivity {


    ImageView course;
    ImageView options;
    ImageView quiz;
    ImageView opinion;
    TextView name;
    UserData userData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructor_menu);
        name = findViewById(R.id.username);


        course = findViewById(R.id.to_edit_course);
        opinion = findViewById(R.id.to_put_opinion);

        options = findViewById(R.id.to_options1);
        quiz = findViewById(R.id.to_add_quiz);
        course.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goTo(add_options.class);
            }
        });
        opinion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goTo(put_opinion.class);
            }
        });
        options.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goTo(com.example.android.easyc.Views.options.class);
            }
        });
        quiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goTo(add_Question_Quiz.class);
            }
        });
        userData = UserData.getInstance();
        name.setText(userData.getUserName());
    }


    void goTo(Class c) {
        Intent intent = new Intent(getApplicationContext(), c);
        startActivity(intent);
         overridePendingTransition(R.anim.goup, R.anim.godown);
    }
}
