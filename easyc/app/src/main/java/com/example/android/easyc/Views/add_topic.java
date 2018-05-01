package com.example.android.easyc.Views;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.android.easyc.R;

import java.util.ArrayList;

public class add_topic extends AppCompatActivity {


    EditText code;
    EditText output;
    EditText des;
    EditText title;
    Spinner categories;
    ArrayList<Integer> catIds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_topic);
        catIds = new ArrayList<Integer>();



        ArrayAdapter<Integer> adp = new ArrayAdapter<Integer>(this,android.R.layout.simple_spinner_item, catIds);
        adp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categories.setAdapter(adp);
    }
}
