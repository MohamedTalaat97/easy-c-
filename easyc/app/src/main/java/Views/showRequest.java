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
        accept = findViewById(R.id.acceptid);
        deny = findViewById(R.id.denyid);
        requestText = findViewById(R.id.requestid);
        id = getIntent().getIntExtra(show_Requests.ID, 0);
        signInUpController = new SignInUpController();

        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAccept();
            }
        });

        deny.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDeny();
            }
        });

        fillData();

    }


    @Override
    public void onBackPressed(){
        super.onBackPressed();
        //Add the OnBackPressed into Other activity when the BackPressed
        overridePendingTransition(R.anim.godown, R.anim.godown);
    }

    //accept the request for signing up
    void setAccept() {
        signInUpController.acceptRequest(id, new OnTaskListeners.Bool() {
            @Override
            public void onSuccess(Boolean result) {
                if (result) {
                    signInUpController.toast("Accepted", getApplicationContext());
                    goToRequests();
                } else
                    signInUpController.toast("False", getApplicationContext());
            }
        });
    }

    //deny the request for signing up
    void setDeny() {
        signInUpController.denyRequest(id, new OnTaskListeners.Bool() {
            @Override
            public void onSuccess(Boolean result) {
                if (result) {
                    signInUpController.toast("Denied", getApplicationContext());
                    goToRequests();
                } else
                    signInUpController.toast("False", getApplicationContext());
            }
        });
    }

    //fill the text view to show the request
    void fillData() {
        signInUpController.getOneRequest(id, new OnTaskListeners.Word() {
            @Override
            public void onSuccess(String result) {
                requestText.setText(result);
            }
        });
    }


    //get back to requests
    void goToRequests() {
        Intent intent = new Intent(getApplicationContext(), show_Requests.class);
        startActivity(intent);
    }
}
