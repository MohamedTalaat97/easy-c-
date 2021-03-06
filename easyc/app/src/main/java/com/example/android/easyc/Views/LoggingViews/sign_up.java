package com.example.android.easyc.Views.LoggingViews;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.android.easyc.R;

import com.example.android.easyc.Controllers.MailController;
import com.example.android.easyc.Controllers.SignInUpController;
import com.example.android.easyc.Interfaces.OnTaskListeners;

public class sign_up extends AppCompatActivity {

    MailController mailController;
    SignInUpController signInUpController;
    Button signup;
    EditText username;
    EditText pass;
    Spinner type;
    EditText age;
    EditText email;
    EditText requestText;

    boolean usernameChecked;
    boolean emailChecked;
    boolean uniqueUsername;
    boolean uniqueEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        signInUpController = new SignInUpController();
        mailController = new MailController();

        signup = findViewById(R.id.BT_sign_up);
        username = findViewById(R.id.ET_user_name);
        pass = findViewById(R.id.ET_Password);
        type = findViewById(R.id.sp_type);
        age = findViewById(R.id.ET_age);
        email = findViewById(R.id.ET_email);
        requestText = findViewById(R.id.ET_motivation);

        usernameChecked = false;
        emailChecked = false;
        uniqueEmail = false;
        uniqueUsername = false;


        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.userType, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        type.setAdapter(adapter);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp();
            }
        });

        //check the database if username is available
        username.addTextChangedListener(new TextWatcher() {
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
        //check the database if the email is available
        email.addTextChangedListener(new TextWatcher() {
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

        type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (type.getSelectedItem().toString()
                        .matches("Student"))
                    requestText.setVisibility(View.INVISIBLE);
                else
                    requestText.setVisibility(View.VISIBLE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    //sign up button
    void signUp() {
        if (check()) {
            //from the controller call signup function that you made and after it finish the function will call back to this function
            signInUpController.signUp(username.getText().toString(), pass.getText().toString(), (String) type.getSelectedItem(), age.getText().toString(), email.getText().toString(), requestText.getText().toString(), new OnTaskListeners.Bool() {
                @Override
                public void onSuccess(Boolean result) {
                    if (result) {
                        signInUpController.toast("Successfully Finished", getApplicationContext());
                        mailController.sendWelcomeMessage(email.getText().toString());
                        goToSignInActivity();
                    } else {
                        if (!signInUpController.checkConnection(getApplicationContext()))
                            return;

                        checkUserName();
                        checkEmail();
                        if (check())
                            signInUpController.toast("unsuccessfull operation", getApplicationContext());

                    }

                }
            });
        }
    }

    //check before sign Up
    boolean check() {
        if (username.getText().toString().matches("") || pass.getText().toString().matches("") || age.getText().toString().matches("") || email.getText().toString().matches("")) {
            signInUpController.toast("Please fill the required fields", getApplicationContext());
            return false;
        }

        if(username.getText().toString().indexOf('@')>=0)
        {
            signInUpController.toast("please remove the @ sign",getApplicationContext());
            return false;
        }

        if (email.getText().toString().indexOf('@') < 0) {
            signInUpController.toast("please enter real email", getApplicationContext());
            return false;
        }


        if (pass.getText().length() < 4) {
            signInUpController.toast("please add more secure password", getApplicationContext());
            return false;
        }

        if (!usernameChecked) {
            signInUpController.toast("wait until we check username", getApplicationContext());
            return false;
        }

        if (!emailChecked) {
            signInUpController.toast("wait until we check email", getApplicationContext());
            return false;
        }

        if (!uniqueUsername) {
            signInUpController.toast("there is another account have the same username please change your username", getApplicationContext());
            return false;
        }

        if (!uniqueEmail) {
            signInUpController.toast("there is another account have the same email please change your email", getApplicationContext());
            return false;
        }

        if (email.getText().toString().matches(mailController.getCompanyEmail())) {
            signInUpController.toast("this is the email of the company \n please put real email", getApplicationContext());
            return false;
        }
        if(!signInUpController.checkConnection(getApplicationContext()))
            return false;


        if(requestText.getVisibility() == View.VISIBLE )
        {
            if(requestText.getText().toString().matches(""))
            {
                signInUpController.toast("please put your skills",getApplicationContext());
                return false;
            }
        }
        try {
            int a = Integer.parseInt(age.getText().toString());
        } catch (NumberFormatException e) {
            signInUpController.toast("please enter real age", getApplicationContext());
            return false;
        }


        return true;
    }

    //check from the database if the username  is available
    void checkUserName() {
        usernameChecked = false;
        signInUpController.checkUserName(username.getText().toString(), new OnTaskListeners.Bool() {
            @Override
            public void onSuccess(Boolean result) {
                usernameChecked = true;
                uniqueUsername = !result;

            }
        });
    }

    //check from the database if the email is available
    void checkEmail() {
        emailChecked = false;
        signInUpController.checkEmail(email.getText().toString(), new OnTaskListeners.Bool() {
            @Override
            public void onSuccess(Boolean result) {
                emailChecked = true;
                uniqueEmail = !result;


            }
        });
    }

    //return back to sign In activity
    void goToSignInActivity() {
        Intent i = new Intent(sign_up.this, sign_in.class);
        startActivity(i);
        finish();
    }


}
