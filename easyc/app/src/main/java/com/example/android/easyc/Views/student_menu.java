package com.example.android.easyc.Views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.RotateAnimation;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.easyc.Controllers.StudentController;
import com.example.android.easyc.Interfaces.OnTaskListeners;
import com.example.android.easyc.R;

public class student_menu extends AppCompatActivity {

    GridView menu;
    TextView name;
    ProgressBar level;
    int user_id;
    String user_name;
    int user_Level;
    StudentController studentController;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_menu);
        name = findViewById(R.id.username);
        user_id=studentController.getUserId();
        studentController.getUserName(user_id, new OnTaskListeners.Word() {
            @Override
            public void onSuccess(String result) {
                user_name=result;
            }
        });

        initMenu();
        initProgress();
    }


    void initMenu() {

        menu = (GridView) findViewById(R.id.gridview);
        menu.setAdapter(new student_menu_adapter(this));
        menu.setNumColumns(2);
        menu.setVerticalSpacing(20);
        menu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {

                switch(position)
                {
                    case(0):goTo(categories.class);
                    break;

                }

            }
        });
    }


    void initProgress()
    {

        level = findViewById(R.id.level_Bar);
        level.setMax(100);
        studentController.getUserLevel(user_id, new OnTaskListeners.Number() {
            @Override
            public void onSuccess(int result) {
                user_Level=result;
            }
        });
        level.setProgress(user_Level*10);
    }
    //go to any class
    void goTo(Class c) {
        Intent i = new Intent(getApplicationContext(), c);
        startActivity(i);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.godown, R.anim.godown);
    }
}