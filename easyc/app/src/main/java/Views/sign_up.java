package Views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import Controllers.SignInUpController;
import Interfaces.OnTaskListeners;
import com.example.android.easyc.R;

public class sign_up extends AppCompatActivity {


    SignInUpController signInUpController;
    Button signup;
    EditText username;
    EditText pass;
    Spinner type;
    EditText age;
    EditText email;

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
                signUp();
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
            signInUpController.signUp(username.getText().toString(), pass.getText().toString(), (String) type.getSelectedItem(), age.getText().toString(), email.getText().toString(), new OnTaskListeners.Bool() {
                @Override
                public void onSuccess(Boolean result) {
                    if (result) {
                        signInUpController.toast("sign up successful", getApplicationContext());
                        goToSignInActivity();
                    } else
                        signInUpController.toast("False", getApplicationContext());
                }
            });
        }
    }

    boolean check() {
        if (username.getText().toString().matches("") || pass.getText().toString().matches("") || age.getText().toString().matches("") || email.getText().toString().matches("")) {
            signInUpController.toast("You did not enter a username or a password", getApplicationContext());
            return false;
        }
        return true;
    }


    void goToSignInActivity() {
        Intent i = new Intent(sign_up.this, sign_in_activity.class);
        startActivity(i);
    }


}
