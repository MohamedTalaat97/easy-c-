package com.example.android.easyc.Views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.android.easyc.Controllers.MailController;
import com.example.android.easyc.Interfaces.OnTaskListeners;
import com.example.android.easyc.R;

public class forget_password_username extends AppCompatActivity {
    Button forget;
    EditText forgetText;
    MailController mailController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password_username);
        forget = findViewById(R.id.forgetTextButton);
        forgetText = findViewById(R.id.forgetText);
        mailController = new MailController();

        forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMail();
            }
        });
    }


    void sendMail() {
        if (!check())
            return;

        mailController.forgetUserNameOrPassword(forgetText.getText().toString(), new OnTaskListeners.Word() {
            @Override
            public void onSuccess(String result) {
                mailController.toast(result, getApplicationContext());
                if(result.indexOf("inbox")>-1)
                    goToSignIn();
            }
        });

    }


    boolean check() {
        if (forgetText.getText().toString().matches("")) {
            mailController.toast("please write your email", getApplicationContext());
            return false;
        }

        if (forgetText.getText().toString().indexOf('@') < 0) {
            mailController.toast("please enter real email", getApplicationContext());
            return false;
        }
        return true;

    }


    void goToSignIn()
    {
        Intent i = new Intent(getApplicationContext(), sign_in_activity.class);
        startActivity(i);
    }
}
