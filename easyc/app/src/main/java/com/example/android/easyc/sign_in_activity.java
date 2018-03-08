package com.example.android.easyc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class sign_in_activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_activity);
    }






    public void open_sign_up(View view)
    {
        Intent i = new Intent(this,sign_up.class);
        startActivity(i);
    }
}
