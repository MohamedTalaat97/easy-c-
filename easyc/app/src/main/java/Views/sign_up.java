package Views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.android.easyc.R;

import Controllers.SignInUpController;
import Interfaces.OnTaskListeners;

public class sign_up extends AppCompatActivity {


    SignInUpController signInUpController;
    Button signup;
    EditText username;
    EditText pass;
    Spinner type;
    EditText age;
    EditText email;
    boolean usernameChecked;
    boolean emailChecked;
    boolean uniqueUsername;
    boolean uniqueEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        signInUpController = new SignInUpController();

        signup = findViewById(R.id.BT_sign_up);
        username = findViewById(R.id.ET_user_name);
        pass = findViewById(R.id.ET_Password);
        type = findViewById(R.id.sp_type);
        age = findViewById(R.id.ET_age);
        email = findViewById(R.id.ET_email);
        Spinner spinner = findViewById(R.id.sp_type);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pass.requestFocus();
                signUp();
            }
        });

        usernameChecked = false;
        emailChecked = false;
        uniqueEmail = false;
        uniqueUsername = false;

        username.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                    usernameChecked = false;
                else
                    checkUserName();

            }
        });

        pass.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                    emailChecked = false;
                else
                    checkEmail();
            }
        });


        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.userType, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
    }


    public void signUp() {
        if (check()) {
            //from the controller call signup function that you made and after it finish the function will call back to this function
            signInUpController.signUp(username.getText().toString(), pass.getText().toString(), (String) type.getSelectedItem(), age.getText().toString(), email.getText().toString(), new OnTaskListeners.Word() {
                @Override
                public void onSuccess(String result) {
                    signInUpController.toast(result, getApplicationContext());
                    goToSignInActivity();

                }
            });
        }
    }

    boolean check() {
        if (username.getText().toString().matches("") || pass.getText().toString().matches("") || age.getText().toString().matches("") || email.getText().toString().matches("")) {
            signInUpController.toast("Please fill the required fields", getApplicationContext());
            return false;
        }


        if (email.getText().toString().indexOf('@') < 0) {
            signInUpController.toast("please enter real email", getApplicationContext());
            return false;
        }

        if(pass.getText().length() <4)
        {
            signInUpController.toast("please add more secure password",getApplicationContext());
            return  false;
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


        try {
            int a = Integer.parseInt(age.getText().toString());
        } catch (NumberFormatException e) {
            signInUpController.toast("please enter real age", getApplicationContext());
            return false;
        }


        return true;
    }


    void checkUserName() {
        signInUpController.checkUserName(username.getText().toString(), new OnTaskListeners.Bool() {
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

    void checkEmail() {
        signInUpController.checkEmail(email.getText().toString(), new OnTaskListeners.Bool() {
            @Override
            public void onSuccess(Boolean result) {
                emailChecked = true;
                if (result)
                    uniqueEmail = false;
                else
                    uniqueEmail = true;


            }
        });
    }


    void goToSignInActivity() {
        Intent i = new Intent(sign_up.this, sign_in_activity.class);
        startActivity(i);
    }


}
