package com.example.android.easyc.Views;

/**
 * Created by KhALeD SaBrY on 15-Mar-18.
 */


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.easyc.Controllers.OpinionController;
import com.example.android.easyc.Interfaces.OnTaskListeners;
import com.example.android.easyc.Interfaces.ViewListener;
import com.example.android.easyc.R;

import java.util.ArrayList;

public class put_opinion extends AppCompatActivity {
    OpinionController opinionController;

    EditText title;
    EditText description;
    Button backToMenu;
    Button putOpinion;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_opinion);
        opinionController = new OpinionController();
        title = findViewById(R.id.puttitle);
        description = findViewById(R.id.putdescription);
        backToMenu = findViewById(R.id.backwithoutsave);
        putOpinion = findViewById(R.id.insertOpinion);

        backToMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backToMenu();
            }
        });

        putOpinion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertOpinion();
            }
        });


    }


    void insertOpinion() {
        if (check()) {
            opinionController.putOpinion(title.getText().toString(), description.getText().toString(), new OnTaskListeners.Bool() {
                @Override
                public void onSuccess(Boolean result) {
                    if (result) {
                        toast("successfully added");
                        title.setText("");
                        description.setText("");

                    } else
                        toast("unsuccessfully added");
                }
            });
        }


    }

    boolean check() {
        if (title.getText().toString() == "") {
            toast("there is no name for the title of Opinion");
            return false;
        }
        if (description.getText().toString() == "") {
            toast("please add your opinion");
            return false;
        }
        return true;
    }


    void toast(String msg) {
        Toast.makeText(put_opinion.this, msg, Toast.LENGTH_SHORT).show();

    }


    void backToMenu() {
        Intent intent = new Intent(this, student_menu.class);
        startActivity(intent);
    }
}
