package com.example.android.easyc.Views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.android.easyc.R;
import com.example.android.easyc.Views.OpinionViews.put_opinion;

public class instructor_menu extends AppCompatActivity {


    ImageView course;
    ImageView options;
    ImageView quiz;
    ImageView opinion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructor_menu);


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
                goTo(options.class);
            }
        });
        quiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goTo(add_quiz.class);
            }
        });
    }


    void goTo(Class c) {
        Intent intent = new Intent(getApplicationContext(), c);
        startActivity(intent);
         overridePendingTransition(R.anim.goup, R.anim.godown);
    }
}
