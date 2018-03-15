package com.example.android.easyc.Views;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.android.easyc.Controllers.Student_Controller;
import com.example.android.easyc.Interfaces.OnTaskListeners;
import com.example.android.easyc.R;

import java.util.ArrayList;

public class categories extends AppCompatActivity {

    Student_Controller  student_controller;

    ArrayList<String>  categories;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
        categories = new ArrayList<String>();
        student_controller=new Student_Controller();
        student_controller.getCategories(new OnTaskListeners.List() {
            @Override
            public void onSuccess(ArrayList<Object> result) {

                categories = (ArrayList<String>)(Object)result;
            }
        });


        ArrayAdapter<String>  cat_adapter =new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,categories);

        ListView categoriesList = (ListView) findViewById(R.id.categories_ListView);
        categoriesList.setAdapter(cat_adapter);
    }}


