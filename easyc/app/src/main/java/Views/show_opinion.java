package Views;

/**
 * Created by KhALeD SaBrY on 15-Mar-18.
 */


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.android.easyc.R;

import Controllers.OpinionController;
import Interfaces.OnTaskListeners;

public class show_opinion extends AppCompatActivity {
    OpinionController opinionController;
    int id;
    TextView title;
    TextView description;
    Button read;
    Button back;
    Button favourite;
    boolean favouriteTopic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_opinion);
        opinionController = new OpinionController();

        title = findViewById(R.id.title);
        description = findViewById(R.id.description);
        read = findViewById(R.id.read);
        back = findViewById(R.id.backtoopinions);
        favourite = findViewById(R.id.favourite);
        id = getIntent().getIntExtra(show_opinions.EXTRA_DATA_ID, 0);

        read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                markAsRead();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backToOpinions();
            }
        });

        favourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeFavourite();
            }
        });

        refreshData();
    }


    void refreshData() {
        opinionController.makeItSeen(id);
        title.setText(getIntent().getStringExtra(show_opinions.EXTRA_DATA_TITLE));
        opinionController.returnDescription(id, new OnTaskListeners.Word() {
            @Override
            public void onSuccess(String result) {
                description.setText(result);

            }
        });
        opinionController.checkFavourite(id, new OnTaskListeners.Bool() {
            @Override
            public void onSuccess(Boolean result) {
                if (result) {
                    favouriteTopic = true;
                } else {
                    favouriteTopic = false;
                }
                updateButtonFavouriteText();
            }
        });
    }


    void markAsRead() {
        opinionController.updateRead(id, true, new OnTaskListeners.Bool() {
            @Override
            public void onSuccess(Boolean result) {
                if (result)
                    opinionController.toast("True", getApplicationContext());
                else
                    opinionController.toast("False", getApplicationContext());

            }
        });
    }

    void changeFavourite() {
        opinionController.updateFavourite(id, favouriteTopic, new OnTaskListeners.Bool() {
            @Override
            public void onSuccess(Boolean result) {
                if (result) {
                    opinionController.toast("True", getApplicationContext());
                    favouriteTopic = !favouriteTopic;
                    updateButtonFavouriteText();
                } else
                    opinionController.toast("False", getApplicationContext());

            }
        });
    }

    void updateButtonFavouriteText() {
        if (favouriteTopic)
            favourite.setText("remove favourite");
        else
            favourite.setText("mark as favourite");

    }


    void backToOpinions() {
        Intent intent = new Intent(this, show_opinions.class);
        startActivity(intent);
    }


}
