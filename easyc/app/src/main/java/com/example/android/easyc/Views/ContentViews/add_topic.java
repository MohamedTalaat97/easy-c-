package com.example.android.easyc.Views.ContentViews;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.android.easyc.Controllers.CourseController;
import com.example.android.easyc.Interfaces.OnTaskListeners;
import com.example.android.easyc.R;

import java.util.ArrayList;

public class add_topic extends AppCompatActivity {

    CourseController courseController;
    EditText code;
    EditText output;
    EditText des;
    EditText title;
    Spinner categories;
    Button add_topic;
    ArrayList<Integer> catIds;
    ArrayList<String> catNames;
    int id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_topic);
        catIds = new ArrayList<Integer>();
        catNames = new ArrayList<String>();

        code = findViewById(R.id.add_code);
        output = findViewById(R.id.add_output);
        categories = findViewById(R.id.add_spinner_topic);
        des = findViewById(R.id.add_des);
        title = findViewById(R.id.add_title);
        add_topic = findViewById(R.id.add_topic);
        courseController = new CourseController();
        courseController.getCategoriesTitles(new OnTaskListeners.Map() {
            @Override
            public void onSuccess(String key, ArrayList<Object> list) {
                if (key.matches(CourseController.ids)) {
                    catIds = (ArrayList<Integer>) (Object) list;
                    id = catIds.get(0);
                } else {
                    catNames = (ArrayList<String>) (Object) list;
                    ArrayAdapter<String> adp = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, catNames);
                    adp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    categories.setAdapter(adp);
                }
            }
        });


        add_topic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (check())
                    addTopic();
                else courseController.toast("please enter all fields", getApplicationContext());

            }
        });


    }

    //add topic
    void addTopic() {
        id = catIds.get(categories.getSelectedItemPosition());

        courseController.addTopic(id, code.getText().toString(), output.getText().toString(), des.getText().toString(), title.getText().toString(), new OnTaskListeners.Bool() {
            @Override
            public void onSuccess(Boolean result) {
                if (result) {
                    courseController.toast("finished successfully", getApplicationContext());
                    finish();
                } else
                    courseController.toast("something went wrong please try again", getApplicationContext());
            }
        });
    }

    //check before add
    boolean check() {
        if (code.getText().toString().matches("") && output.getText().toString().matches("") && des.getText().toString().matches("") && title.getText().toString().matches(""))
            return false;
        else return true;
    }
}
