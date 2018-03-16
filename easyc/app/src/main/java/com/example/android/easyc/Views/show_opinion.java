package com.example.android.easyc.Views;

/**
 * Created by KhALeD SaBrY on 15-Mar-18.
 */


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.easyc.Controllers.OpinionController;
import com.example.android.easyc.Interfaces.OnTaskListeners;
import com.example.android.easyc.R;

import java.util.ArrayList;

public class show_opinion extends AppCompatActivity {
    OpinionController opinionController;
    int id;
    TextView title;
    TextView description;
    Button read;
    Button back;
    Button favourite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_opinion);
        opinionController = new OpinionController();

        title = findViewById(R.id.title);
        description =  findViewById(R.id.description);
        read = findViewById(R.id.read);
        back = findViewById(R.id.backtoopinions);
        favourite = findViewById(R.id.favourite);
        id = getIntent().getIntExtra(show_opinions.EXTRA_DATA_ID, 0);

        read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                markAsRead();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backToOpinions();
            }
        });

        favourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                markAsFavourite();
            }
        });

        refreshData();
    }


    void refreshData() {
        title.setText(getIntent().getStringExtra(show_opinions.EXTRA_DATA_TITLE));
        opinionController.returnDescription(id, new OnTaskListeners.Word() {
            @Override
            public void onSuccess(String result) {
                description.setText(result);

            }
        });
    }



    void markAsRead() {
        opinionController.updateRead(id, true, new OnTaskListeners.Bool() {
            @Override
            public void onSuccess(Boolean result) {
                if (result)
                    Toast.makeText(show_opinion.this, "True", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(show_opinion.this, "False", Toast.LENGTH_SHORT).show();

            }
        });
    }


    void markAsFavourite() {
        opinionController.updateFavourite(id, true, new OnTaskListeners.Bool() {
            @Override
            public void onSuccess(Boolean result) {
                if (result)
                    Toast.makeText(show_opinion.this, "True", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(show_opinion.this, "False", Toast.LENGTH_SHORT).show();

            }
        });
    }



    void backToOpinions() {
        Intent intent = new Intent(this, show_opinions.class);
        startActivity(intent);
    }


}
