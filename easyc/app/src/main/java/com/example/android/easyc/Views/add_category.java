package com.example.android.easyc.Views;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.android.easyc.Controllers.CourseController;
import com.example.android.easyc.Interfaces.OnTaskListeners;
import com.example.android.easyc.R;

import java.util.ArrayList;

public class add_category extends AppCompatActivity {

    CourseController courseController;
    EditText catName;
    Spinner levels;
    Button done;
    ArrayList<Integer> level;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);
        catName = findViewById(R.id.catName);
        levels = findViewById(R.id.add_spinner);
        done = findViewById(R.id.addCategory);
        courseController = new CourseController();
        level = new ArrayList<Integer>();
        courseController.getlevels(new OnTaskListeners.List() {
            @Override
            public void onSuccess(ArrayList<Object> result) {
                level = (ArrayList<Integer>) (Object) result;
            }
        });

        ArrayAdapter<Integer> adp = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item, level);
        adp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        levels.setAdapter(adp);


        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addCategory();
            }
        });


    }



    public void addCategory()
    {

///take data and insert fe el adapter  w el oontroller


    }
}
