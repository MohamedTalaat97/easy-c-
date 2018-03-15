package com.example.android.easyc.Views;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.android.easyc.Controllers.SignInUpController;
import com.example.android.easyc.Interfaces.OnTaskListeners;
import com.example.android.easyc.R;

public class sign_up extends AppCompatActivity {


    SignInUpController signInUpController;
    Button signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        signInUpController = new SignInUpController();
        signup = (Button) findViewById(R.id.BT_sign_up);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp();
            }
        });


        Spinner spinner = (Spinner) findViewById(R.id.sp_type);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.userType, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
    }


    public void signUp() {
        EditText username = (EditText) findViewById(R.id.ET_user_name);
        EditText pass = (EditText) findViewById(R.id.ET_Password);
        Spinner type = (Spinner) findViewById(R.id.sp_type);
        EditText age = (EditText) findViewById(R.id.ET_age);
        EditText email = (EditText) findViewById(R.id.ET_email);

        if (username.getText().toString().matches("") || pass.getText().toString().matches("") || age.getText().toString().matches("") || email.getText().toString().matches("")) {
            Toast.makeText(this, "You did not enter a username or a password", Toast.LENGTH_SHORT).show();
            return;
        }
        //from the controller call signup function that you made and after it finish the function will call back to this function
        signInUpController.signUp(username.getText().toString(), pass.getText().toString(), (String) type.getSelectedItem(), age.getText().toString(), email.getText().toString(), new OnTaskListeners.Bool() {
            @Override
            public void onSuccess(Boolean result) {
                if (result)
                    Toast.makeText(sign_up.this, "True", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(sign_up.this, "False", Toast.LENGTH_LONG).show();
            }
        });
    }

}
