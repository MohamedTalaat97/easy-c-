package com.example.android.easyc.Views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.easyc.Controllers.StudentController;
import com.example.android.easyc.Interfaces.OnTaskListeners;
import com.example.android.easyc.R;
import com.example.android.easyc.Views.DiscussionViews.discussion_room_questions;
import com.example.android.easyc.Views.DiscussionViews.put_question_discussion;
import com.example.android.easyc.Views.OpinionViews.put_opinion;

public class student_menu extends AppCompatActivity {

    ImageView course;
    ImageView options;
    ImageView quiz;
    ImageView opinion;
    ImageView discusion;
    ImageView ask;
    GridView menu;
    TextView name;
    ProgressBar level;
    int userId;
    String username;
    int userLevel;
    StudentController studentController;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_menu);
        studentController = new StudentController();
        name = findViewById(R.id.username);
        level = findViewById(R.id.level_Bar);
        course = findViewById(R.id.to_course);
        opinion = findViewById(R.id.to_opinion);
        options = findViewById(R.id.to_options);
        quiz = findViewById(R.id.to_quiz);
        discusion = findViewById(R.id.to_dis);
        ask = findViewById(R.id.to_ask);

        course.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goTo(categories.class);
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
                goTo(quiz_options.class);
            }
        });
       discusion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goTo(discussion_room_questions.class);
            }
        });
        ask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goTo(put_question_discussion.class);
            }
        });

        userId = studentController.getUserId();
        username = studentController.getUsername();
        studentController.getUserName(userId, new OnTaskListeners.Word() {
            @Override
            public void onSuccess(String result) {
                username = result;
            }
        });
        userLevel = studentController.getUserLevel();
        name.setText(username);


    }


    void initMenu() {


/*
        menu = findViewById(R.id.gridview);
        GridAdapter gridAdapter = new GridAdapter(this, menu_icons);
        menu.setAdapter(gridAdapter);
        // menu.setNumColumns(2);*/
        // menu.setVerticalSpacing(20);
      /*  menu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {

                switch(position)
                {
                    case(0):goTo(categories.class);
                    break;

                }

            }
        });*/

/*
        private Integer[] menu_icons = {
                R.drawable.opinion,
                R.drawable.opinion,
                R.drawable.setting,
                R.drawable.opinion





        };


*/
    }


    void initProgress() {

        level.setMax(100);
        level.setProgress(userLevel * 10);
    }

    //go to any class
    void goTo(Class c) {
        Intent intent = new Intent(getApplicationContext(), c);
        startActivity(intent);
        // overridePendingTransition(R.anim.goup, R.anim.godown);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.godown, R.anim.godown);
    }
}