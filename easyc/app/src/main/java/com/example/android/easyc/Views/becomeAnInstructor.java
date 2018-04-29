package com.example.android.easyc.Views;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.android.easyc.Controllers.SignInUpController;
import com.example.android.easyc.Interfaces.OnTaskListeners;
import com.example.android.easyc.R;

public class becomeAnInstructor extends AppCompatActivity {
    SignInUpController signInUpController;
    EditText requestText;
    Button requestButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_become_an_instructor);

        signInUpController = new SignInUpController();
        requestText = findViewById(R.id.requestInstructorid);
        requestButton = findViewById(R.id.requestButton);


        requestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!check())
                    return;
                signInUpController.putRequestToBecomInstructor(requestText.getText().toString(), new OnTaskListeners.Bool() {
                    @Override
                    public void onSuccess(Boolean result) {
                        if (result) {
                            signInUpController.toast("successful task", getApplicationContext());
                            finish();

                        } else if (!signInUpController.checkConnection(getApplicationContext()))
                            return;
                        else
                            signInUpController.toast("unsuccessful task", getApplicationContext());
                    }

                });

            }
        });
    }

    boolean check()
    {
        if(requestText.getText().toString().matches(""))
        {
            signInUpController.toast("please put your skills",getApplicationContext());
            return false;
        }

        return true;
    }
}

