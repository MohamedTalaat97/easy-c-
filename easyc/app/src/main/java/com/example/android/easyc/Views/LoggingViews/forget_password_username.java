package com.example.android.easyc.Views.LoggingViews;

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

public class forget_password_username extends AppCompatActivity {
    Button forget;
    EditText forgetText;
    MailController mailController;
    boolean emailChecked;
    boolean foundEmail;
    SignInUpController signInUpController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password_username);
        forget = findViewById(R.id.forgetTextButton);
        forgetText = findViewById(R.id.forgetText);
        mailController = new MailController();
        signInUpController = new SignInUpController();
        emailChecked = false;
        foundEmail = false;

        forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMail();
            }
        });
        forgetText.addTextChangedListener(new TextWatcher() {
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
    }


    //send email to get back the password/username
    void sendMail() {
        if (!check())
            return;

        mailController.forgetUserNameOrPassword(forgetText.getText().toString(), new OnTaskListeners.Word() {
            @Override
            public void onSuccess(String result) {
                mailController.toast(result, getApplicationContext());
                    goToSignIn();
            }
        });

    }


    @Override
    public void onBackPressed(){
        super.onBackPressed();
        //Add the OnBackPressed into Other activity when the BackPressed
        overridePendingTransition(R.anim.godown, R.anim.godown);
    }

    //check if there is email
    boolean check() {
        if (forgetText.getText().toString().matches("")) {
            mailController.toast("please write your email", getApplicationContext());
            return false;
        }

        if (forgetText.getText().toString().indexOf('@') < 0) {
            mailController.toast("please enter real email", getApplicationContext());
            return false;
        }

        if (!emailChecked) {
            signInUpController.toast("wait until we check email", getApplicationContext());
            return false;
        }

        if (!foundEmail) {
            signInUpController.toast("we didn't found that email in our database", getApplicationContext());
            return false;
        }
        return true;

    }

    //check from the database if the email is available
    void checkEmail() {
        emailChecked = false;
        signInUpController.checkEmail(forgetText.getText().toString(), new OnTaskListeners.Bool() {
            @Override
            public void onSuccess(Boolean result) {
                emailChecked = true;
                foundEmail = result;


            }
        });
    }

    //go back to signIn menu
    void goToSignIn()
    {
        Intent i = new Intent(getApplicationContext(), sign_in.class);
        startActivity(i);
    }
}
