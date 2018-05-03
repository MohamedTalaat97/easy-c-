package com.example.android.easyc.Views.LoggingViews;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.android.easyc.Controllers.MailController;
import com.example.android.easyc.Controllers.SignInUpController;
import com.example.android.easyc.Interfaces.OnTaskListeners;
import com.example.android.easyc.R;
import com.example.android.easyc.Views.options;

public class change_password extends AppCompatActivity {
    EditText passwordText;
    Button changeButton;
    SignInUpController signInUpController;
    MailController mailController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        signInUpController = new SignInUpController();
        mailController = new MailController();
        passwordText = findViewById(R.id.passwordchangetext);
        changeButton = findViewById(R.id.changepasswordbutton);


        changeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePassword();
            }
        });
    }

    //change the password
    void changePassword() {
        if (!check())
            return;

        signInUpController.updatePassword(passwordText.getText().toString(), new OnTaskListeners.Bool() {
            @Override
            public void onSuccess(Boolean result) {
                if (result) {
                    signInUpController.toast("successfully changed", getApplicationContext());
                    mailController.sendWarningChangeInPassword();
                    goToOptions();
                }
                else
                    signInUpController.toast("unsuccessful operation", getApplicationContext());
            }
        });
    }

    // check before change
    boolean check() {
        if (passwordText.getText().toString().matches("")) {
            signInUpController.toast("please add password", getApplicationContext());
            return false;
        }
        if (passwordText.getText().toString().length() < 4) {
            signInUpController.toast("please add more secure password", getApplicationContext());
            return false;
        }
        return true;
    }


  void  goToOptions()
  {
      Intent intent = new Intent(getApplicationContext(),options.class);
      startActivity(intent);
  }


}
