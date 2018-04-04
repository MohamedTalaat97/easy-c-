package com.example.android.easyc.Views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.easyc.R;

public class student_menu extends AppCompatActivity {

    ImageView categories;
    TextView name;
    String user_name;
    int user_Level;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_menu);
//        RotateAnimation anim = new RotateAnimation(0f, 350f, 15f, 15f);
//        anim.setInterpolator(new LinearInterpolator());
//        anim.setRepeatCount(Animation.INFINITE);
//        anim.setDuration(700);
        categories = findViewById(R.id.categories);
        name = findViewById(R.id.username);
//        categories.startAnimation(anim);
        categories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goTo(categories.class);

            }
        });

    }


    //go to any class
  void  goTo(Class c)
    {
        Intent i = new Intent(getApplicationContext(), c);
        startActivity(i);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.godown, R.anim.godown);
    }
}