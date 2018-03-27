package Views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.android.easyc.R;

import Controllers.SignInUpController;
import Interfaces.OnTaskListeners;

public class showRequest extends AppCompatActivity {
    Button accept;
    Button deny;
    TextView requestText;
    SignInUpController signInUpController;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_request);
        accept  = findViewById(R.id.acceptid);
        deny  = findViewById(R.id.denyid);
        requestText  = findViewById(R.id.requestid);
        id = getIntent().getIntExtra(show_Requests.ID, 0);
        signInUpController = new SignInUpController();

        requestText.setText(getIntent().getStringExtra(show_Requests.REQUEST));
        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signInUpController.handleRequest(id, false, new OnTaskListeners.Bool() {
                    @Override
                    public void onSuccess(Boolean result) {
                        if(result) {
                            signInUpController.toast("Accepted", getApplicationContext());
                            goTo();
                        }
                        else
                            signInUpController.toast("False",getApplicationContext());
                    }
                });

            }
        });

        deny.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signInUpController.handleRequest(id, true, new OnTaskListeners.Bool() {
                    @Override
                    public void onSuccess(Boolean result) {
                        if(result) {
                            signInUpController.toast("Denied", getApplicationContext());
                            goTo();
                        }
                        else
                            signInUpController.toast("False",getApplicationContext());
                    }
                });
            }
        });


    }


    void goTo()
    {
        Intent intent = new Intent(getApplicationContext(),show_Requests.class);
        startActivity(intent);
    }
}
