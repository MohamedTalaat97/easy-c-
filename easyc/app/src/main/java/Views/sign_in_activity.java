package Views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_activity);
        connectionDb = ConnectionDb.getInstance();
        connectionDb.TalaatDb();
        signInUpController = new SignInUpController();
        singInButton = findViewById(R.id.BT_sign_in);
        username = (EditText) findViewById(R.id.ET_user_name);
        pass = (EditText) findViewById(R.id.ET_Password);
        forgetButton = findViewById(R.id.BT_forget);
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

    }

    // this is a function that relates to the button when the user click

    public void signIn() {

        if (check()) {

            //from the controller call signin function that you made and after it finish the function will call back to this function
            signInUpController.signIn(username.getText().toString(), pass.getText().toString(), new OnTaskListeners.Word() {
                @Override
                public void onSuccess(String result) {
                    signInUpController.toast(result,getApplicationContext());
                    if(result.matches("true"))
signInUpController.goTo(new OnTaskListeners.classes() {
    @Override
    public void onSuccess(Class classe) {
        goTo(classe);
    }
});
                }
            });
        }
    }


    boolean check() {
        if (username.getText().toString().matches("") || pass.getText().toString().matches("")) {
            signInUpController.toast("You did not enter a username or a password", getApplicationContext());
            return false;
        }
        return true;
    }

    public void open_sign_up(View view) {
        Intent i = new Intent(this, sign_up.class);
        startActivity(i);
    }


    public void goTo(Class s) {

        Intent intent = new Intent(this, s);
        startActivity(intent);
    }


    void goToStudentMenu() {
        Intent i = new Intent(sign_in_activity.this, student_menu.class);
        startActivity(i);
    }

    void goToForgetView() {
        Intent i = new Intent(getApplicationContext(), forget_password_username.class);
        startActivity(i);
    }


}
