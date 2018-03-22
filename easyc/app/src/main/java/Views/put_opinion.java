package Views;

/**
 * Created by KhALeD SaBrY on 15-Mar-18.
 */


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import Controllers.OpinionController;
import Interfaces.OnTaskListeners;
import com.example.android.easyc.R;

public class put_opinion extends AppCompatActivity {
    OpinionController opinionController;

    EditText title;
    EditText description;
    Button backToMenu;
    Button putOpinion;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_put_opinion_activity);
        opinionController = new OpinionController();
        title = findViewById(R.id.puttitle);
        description = findViewById(R.id.putdescription);
        backToMenu = findViewById(R.id.backwithoutsave);
        putOpinion = findViewById(R.id.insertOpinion);

        backToMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backToMenu();
            }
        });

        putOpinion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertOpinion();
            }
        });


    }


  public   void insertOpinion() {
        if (check()) {
            opinionController.putOpinion(title.getText().toString(), description.getText().toString(), new OnTaskListeners.Bool() {
                @Override
                public void onSuccess(Boolean result) {
                    if (result) {
                        opinionController.toast("successfully added",getApplicationContext());
                        title.setText("");
                        description.setText("");

                    } else
                        opinionController.toast("unsuccessfully added",getApplicationContext());
                }
            });
        }


    }

    boolean check() {
        if (title.getText().toString().matches("")) {
            opinionController.toast("there is no name for the title of Opinion",getApplicationContext());
            return false;
        }
        if (description.getText().toString().matches("")) {
            opinionController.toast("please add your opinion",getApplicationContext());
            return false;
        }
        return true;
    }


    void backToMenu() {
        Intent intent = new Intent(this, student_menu.class);
        startActivity(intent);
    }
}
