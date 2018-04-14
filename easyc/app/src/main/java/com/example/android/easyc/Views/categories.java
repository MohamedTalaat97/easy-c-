package com.example.android.easyc.Views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.android.easyc.Controllers.CourseController;
import com.example.android.easyc.Interfaces.OnTaskListeners;
import com.example.android.easyc.R;

import java.util.ArrayList;

public class categories extends AppCompatActivity {

    CourseController courseController;
    ListView categoriesList;
    ArrayList<String> categories;
    int cat_id;
    public static String CAT_ID = "CATID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
        categories = new ArrayList<String>();
        categoriesList = (ListView) findViewById(R.id.categories_ListView);
        courseController = new CourseController();

        categoriesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                                  @Override
                                                  public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                                                      openTopics(categories.get(i));
                                                  }
                                              }
        );

        fillList();
    }

    //fill the category list
    void fillList() {
        courseController.getCategories(new OnTaskListeners.List() {
            @Override
            public void onSuccess(ArrayList<Object> result) {

                categories = (ArrayList<String>) (Object) result;
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(categories.this, android.R.layout.simple_selectable_list_item, categories);
                categoriesList.setAdapter(adapter);
            }
        });
    }

    //open one topic
     void openTopics(String tilte) {
        courseController.getCatagoryId(tilte, new OnTaskListeners.Number() {
            @Override
            public void onSuccess(int result) {
                cat_id = result;
                goToTopics();
            }

        });


    }


    //open topics for this category list
     void goToTopics() {
        Intent i = new Intent(getApplicationContext(), topic.class);
        i.putExtra(CAT_ID, cat_id);
        startActivity(i);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //Add the OnBackPressed into Other activity when the BackPressed
       // overridePendingTransition(R.anim.godown, R.anim.godown);
    }


}


