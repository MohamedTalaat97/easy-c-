package com.example.android.easyc.Views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android.easyc.Controllers.SignInUpController;
import com.example.android.easyc.Interfaces.OnTaskListeners;
import com.example.android.easyc.Models.ConnectionDb;
import com.example.android.easyc.R;

public class sign_in_activity extends AppCompatActivity {

    //first identify the controller
    SignInUpController signInUpController;
    ConnectionDb connectionDb;
    Button singInButton;
    EditText username;
    EditText pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_activity);
        connectionDb = ConnectionDb.getInstance();
        connectionDb.khaledDb();
        signInUpController = new SignInUpController();
    singInButton  = findViewById(R.id.BT_sign_in);
        username =(EditText) findViewById(R.id.ET_user_name) ;
        pass =(EditText) findViewById(R.id.ET_Password) ;
    singInButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            signIn();
        }
    });

    }

    // this is a function that relates to the button when the user click
public void signIn()
{


    if (username.getText().toString().matches("") || pass.getText().toString().matches("")) {
        Toast.makeText(this, "You did not enter a username or a password", Toast.LENGTH_SHORT).show();
        return;

    }
    //from the controller call signin function that you made and after it finish the function will call back to this function
    signInUpController.signIn(username.getText().toString(), pass.getText().toString(), new OnTaskListeners.Bool() {
        @Override
        public void onSuccess(Boolean result) {
            if(result) {
                Toast.makeText(sign_in_activity.this, "True", Toast.LENGTH_LONG).show();
                Intent i = new Intent(sign_in_activity.this,student_menu.class);
                startActivity(i);

            }
            else
                Toast.makeText(sign_in_activity.this,"False",Toast.LENGTH_LONG).show();
        }
    });
}



    public void open_sign_up(View view)
    {
        Intent i = new Intent(this,sign_up.class);
        startActivity(i);
    }


    public  void gotoputopinion()
    {
        Intent intent = new Intent(this,show_opinions.class);
        startActivity(intent);
    }
}
