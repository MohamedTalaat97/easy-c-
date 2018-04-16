package com.example.android.easyc.Views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.easyc.Models.UserData;
import com.example.android.easyc.R;

public class admin_menu extends AppCompatActivity {



    ImageView showOpinion;
    ImageView options;
    ImageView request;
    TextView name;
    UserData userData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_menu);
        userData = UserData.getInstance();

        showOpinion = findViewById(R.id.to_show_opinion);
        options = findViewById(R.id.to_options2);
request=findViewById(R.id.to_request);
name = findViewById(R.id.usernameAdmin);

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
        request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goTo(show_Requests.class);
            }
        });


        name.setText(userData.getUserName());
    }







        void goTo(Class c) {
            Intent intent = new Intent(getApplicationContext(), c);
            startActivity(intent);
            // overridePendingTransition(R.anim.goup, R.anim.godown);
        }









    }

