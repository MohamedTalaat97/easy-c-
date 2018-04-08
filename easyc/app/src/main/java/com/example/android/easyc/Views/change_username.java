package com.example.android.easyc.Views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.android.easyc.Controllers.MailController;
import com.example.android.easyc.Controllers.SignInUpController;
import com.example.android.easyc.Interfaces.OnTaskListeners;
import com.example.android.easyc.R;

public class change_username extends AppCompatActivity {
    EditText userNameText;
    Button changeButton;
    SignInUpController signInUpController;
    MailController mailController;


    boolean usernameChecked;
    boolean uniqueUsername;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_username);
        signInUpController = new SignInUpController();
        mailController = new MailController();
        userNameText = findViewById(R.id.emailchangetext);
        changeButton = findViewById(R.id.changeusernamebutton);

        usernameChecked = false;
        uniqueUsername = false;


        userNameText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkUserName();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        changeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeUsername();
            }
        });
    }



    //change the username
    void changeUsername()
    {
        if(!check())
            return;

        signInUpController.updateUsername(userNameText.getText().toString(), new OnTaskListeners.Bool() {
            @Override
            public void onSuccess(Boolean result) {
                if(result)
                {
                    signInUpController.toast("successfully changed", getApplicationContext());
                    mailController.sendWarningChangeInUsername();
                    goToOptions();
                }
                else
                    signInUpController.toast("unsuccessful operation",getApplicationContext());
            }
        });
    }

    //check before change
    boolean check() {
        if (userNameText.getText().toString().matches("")) {
            signInUpController.toast("please put a username", getApplicationContext());
            return false;

        }

        if (!usernameChecked) {
            signInUpController.toast("wait until we check username", getApplicationContext());
            return false;
        }

        if (!uniqueUsername) {
            signInUpController.toast("there is another account have the same username please change your username", getApplicationContext());
            return false;
        }

        return true;

    }



    //check from the database if the username  is available
    void checkUserName() {
        usernameChecked = false;
        signInUpController.checkUserName(userNameText.getText().toString(), new OnTaskListeners.Bool() {
            @Override
            public void onSuccess(Boolean result) {
                usernameChecked = true;
                if (result)
                    uniqueUsername = false;
                else
                    uniqueUsername = true;


            }
        });
    }


    void  goToOptions()
    {
        Intent intent = new Intent(getApplicationContext(),options.class);
        startActivity(intent);
    }
}
