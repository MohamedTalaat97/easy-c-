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
    String titleText;

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
        titleText = getIntent().getStringExtra(show_opinions.EXTRA_DATA_TITLE);
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
                goToOpinions();
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



    @Override
    public void onBackPressed(){
        super.onBackPressed();
        //Add the OnBackPressed into Other activity when the BackPressed
        overridePendingTransition(R.anim.godown, R.anim.godown);
    }
    void refreshData() {
        //update the state and make this opinion seen
        opinionController.makeItSeen(id);
        //fill the title text
        title.setText(titleText);
        //fill the description
        opinionController.getDescription(id, new OnTaskListeners.Word() {
            @Override
            public void onSuccess(String result) {
                description.setText(result);

            }
        });

        //check if the opinion is favourite to update the button favourite text
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


    //mark the topic as readed
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

    //update the favourite if it's favourite and the button clicked then make it unfavourite
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

    //update the favourite button text
    void updateButtonFavouriteText() {
        if (favouriteTopic)
            favourite.setText("remove favourite");
        else
            favourite.setText("mark as favourite");
    }

    void goToOpinions() {
        Intent intent = new Intent(getApplicationContext(), show_opinions.class);
        startActivity(intent);
    }


}
