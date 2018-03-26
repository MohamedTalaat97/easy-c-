package Views;

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
        //accept  = findViewById(R.id.);
        //deny  = findViewById(R.id.);
        //requestText  = findViewById(R.id.);
        id = getIntent().getIntExtra(show_Requests.ID, 0);
        signInUpController = new SignInUpController();

        requestText.setText(getIntent().getStringExtra(show_Requests.REQUEST));
        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signInUpController.handleRequest(id, false, new OnTaskListeners.Bool() {
                    @Override
                    public void onSuccess(Boolean result) {

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

                    }
                });
            }
        });


    }
}
