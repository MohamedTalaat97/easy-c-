package com.example.android.easyc.Views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.android.easyc.Controllers.SignInUpController;
import com.example.android.easyc.R;

import java.util.ArrayList;

public class options extends AppCompatActivity {
    ListView listView;
    SignInUpController signInUpController;
    ArrayList<String> list;
    private String changeUsername = "change Username";
    private String changePassword = "change Password";
    private String changeEmail = "change Email";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);
        listView = findViewById(R.id.optionlistview);
        signInUpController = new SignInUpController();
        list = new ArrayList<String>();
        list.add(changeUsername);
        list.add(changePassword);
        list.add(changeEmail);


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_selectable_list_item, list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (listView.getItemAtPosition(position).toString().matches(changeUsername)) {
                    goTo(change_username.class);
                } else if (listView.getItemAtPosition(position).toString().matches(changePassword)) {
                    goTo(change_password.class);
                }
                else if(listView.getItemAtPosition(position).toString().matches(changeEmail))
                {
                    goTo(change_email.class);
                }
            }
        });


    }

    void goTo(Class c) {
        Intent intent = new Intent(getApplicationContext(), c);
        startActivity(intent);
    }
}
