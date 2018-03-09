package com.example.android.easyc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.sql.ResultSet;
import java.util.ArrayList;

public class sign_in_activity extends AppCompatActivity {

    Button b;
    DatabaseAdapter db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_activity);

    }







    public void open_student_menu(View view)
    {
        Intent i = new Intent(this,student_menu.class);
        startActivity(i);
    }


    public void open_sign_up(View view)
    {
        Intent i = new Intent(this,sign_up.class);
        startActivity(i);
    }
}
