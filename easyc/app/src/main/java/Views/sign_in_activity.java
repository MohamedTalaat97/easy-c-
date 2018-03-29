package Views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.android.easyc.R;

import Connections.ConnectionDb;
import Controllers.SignInUpController;
import Interfaces.OnTaskListeners;

public class sign_in_activity extends AppCompatActivity {

    //first identify the controller
    SignInUpController signInUpController;
    ConnectionDb connectionDb;
    Button singInButton;
    Button forgetButton;
    EditText username;
    EditText pass;
    TextView signUpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_activity);
        //connect with the database
        connectionDb = ConnectionDb.getInstance();
        connectionDb.khaledDb();
        signInUpController = new SignInUpController();
        singInButton = findViewById(R.id.BT_sign_in);
        username = findViewById(R.id.ET_user_name);
        pass = findViewById(R.id.ET_Password);
        forgetButton = findViewById(R.id.BT_forget);
        signUpButton = findViewById(R.id.dont_have_account);


        singInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });

        forgetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToForgetView();
            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp();

            }
        });

    }

    //check before sign in
    boolean check() {
        if (username.getText().toString().matches("") || pass.getText().toString().matches("")) {
            signInUpController.toast("You did not enter a username or a password", getApplicationContext());
            return false;
        }
        return true;
    }

    //sign in
    public void signIn() {

        //for testing
        goTo(show_opinions.class);

        if (check()) {
            //from the controller call signin function that you made and after it finish the function will call back to this function
            signInUpController.signIn(username.getText().toString(), pass.getText().toString(), new OnTaskListeners.Word() {
                @Override
                public void onSuccess(String result) {
                    signInUpController.toast(result, getApplicationContext());
                    if (result.matches("true"))
                        if (signInUpController.getType() == 'I') {
                            goTo(student_menu.class);
                        } else if (signInUpController.getType() == 'S') {
                            goTo(student_menu.class);
                        } else {
                            goTo(student_menu.class);
                        }
                }
            });
        }
    }

    //if you don't have an account
    public void signUp() {
        Intent i = new Intent(this, sign_up.class);
        startActivity(i);
    }

    //go to any class
    public void goTo(Class s) {

        Intent intent = new Intent(this, s);
        startActivity(intent);
    }

    //go to restore your account class
    void goToForgetView() {
        Intent i = new Intent(getApplicationContext(), forget_password_username.class);
        startActivity(i);
    }


}
