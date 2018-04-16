package com.example.android.easyc.Views;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.android.easyc.Controllers.QuizController;
import com.example.android.easyc.Interfaces.OnTaskListeners;
import com.example.android.easyc.R;

import java.util.ArrayList;

public class quiz_categories extends AppCompatActivity {

QuizController quizController;
    ListView categoriesList;

    ArrayList<Integer> categories;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_categories);
        categoriesList = (ListView) findViewById(R.id.quiz_categories_ListView);
        quizController = new QuizController();
        categories = new ArrayList<Integer>();


        categoriesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                                  @Override
                                                  public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                                                      openQuizzes(categories.get(i));
                                                  }
                                              });



        fillList();
    }





    // aquire the ids
    //open quizzes using the clicked id
    //construct adapter for makin an array of the class quiz
    //set the adapter on it


    void fillList() {
        quizController.getCategories(new OnTaskListeners.List() {
            @Override
            public void onSuccess(ArrayList<Object> result) {

                //categories = (ArrayList<String>) (Object) result;
               // ArrayAdapter<Integer> adapter = new ArrayAdapter<String>(quiz_categories.this, android.R.layout.simple_selectable_list_item, categories);
                //categoriesList.setAdapter(adapter);
            }
        });
    }


    void openQuizzes(Integer id)
    {


    }

}
