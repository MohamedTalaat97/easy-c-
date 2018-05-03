package com.example.android.easyc.Views.LoggingViews;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.android.easyc.Controllers.SignInUpController;
import com.example.android.easyc.Interfaces.OnTaskListeners;
import com.example.android.easyc.R;
import com.example.android.easyc.Views.options;

public class change_email extends AppCompatActivity {

    EditText emailText;
    Button changeButton;
    SignInUpController signInUpController;


    boolean emailChecked;
    boolean uniqueEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_email);

        signInUpController = new SignInUpController();
        emailText = findViewById(R.id.emailchangetext);
        changeButton = findViewById(R.id.changeemailbutton);

        emailChecked = false;
        uniqueEmail = false;


        emailText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkEmail();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        changeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeEmail();
            }
        });
    }

    void changeEmail() {
        if (!check())
            return;

        signInUpController.updateEmail(emailText.getText().toString(), new OnTaskListeners.Bool() {
            @Override
            public void onSuccess(Boolean result) {
                if (result) {
                    signInUpController.toast("successfully changed", getApplicationContext());
                    goToOptions();
                } else
                    signInUpController.toast("unsuccessful operation", getApplicationContext());
            }
        });
    }



    //check before change
    boolean check() {
        if (emailText.getText().toString().matches("")) {
            signInUpController.toast("please put a email", getApplicationContext());
            return false;

        }

        if (emailText.getText().toString().indexOf('@') < 0) {
            signInUpController.toast("please enter real email", getApplicationContext());
            return false;
        }

        if (!emailChecked) {
            signInUpController.toast("wait until we check email", getApplicationContext());
            return false;
        }

        if (!uniqueEmail) {
            signInUpController.toast("there is another account have the same email please change your email", getApplicationContext());
            return false;
        }

        return true;

    }

    //check from the database if the email is available
    void checkEmail() {
        emailChecked = false;
        signInUpController.checkEmail(emailText.getText().toString(), new OnTaskListeners.Bool() {
            @Override
            public void onSuccess(Boolean result) {
                emailChecked = true;
                uniqueEmail = !result;


            }
        });
    }

    void goToOptions() {
        Intent intent = new Intent(getApplicationContext(), options.class);
        startActivity(intent);
    }
}
