package com.example.android.easyc.Views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.android.easyc.R;

public class admin_menu extends AppCompatActivity {



    ImageView showOpinion;
    ImageView options;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_menu);

        showOpinion = findViewById(R.id.to_show_opinion);
        options = findViewById(R.id.to_options2);


        showOpinion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goTo(show_opinions.class);
            }
        });
        options.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goTo(options.class);
            }
        });
    }







        void goTo(Class c) {
            Intent intent = new Intent(getApplicationContext(), c);
            startActivity(intent);
            // overridePendingTransition(R.anim.goup, R.anim.godown);
        }









    }

