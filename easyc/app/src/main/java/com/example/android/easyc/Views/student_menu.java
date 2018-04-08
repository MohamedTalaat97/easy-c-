package com.example.android.easyc.Views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
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
        menu = (GridView) findViewById(R.id.gridview);

        userId =studentController.getUserId();
        username = studentController.getUsername();
        userLevel = studentController.getUserLevel();
        name.setText(username);

        initMenu();
        initProgress();


        studentController.getUserName(userId, new OnTaskListeners.Word() {
            @Override
            public void onSuccess(String result) {
                username=result;
            }
        });



    }


    void initMenu() {

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

        level.setMax(100);
        level.setProgress(userLevel*10);
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