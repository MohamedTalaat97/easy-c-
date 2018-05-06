package com.example.android.easyc.Views.ExamViews;

import android.content.Intent;
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

    ArrayList<Integer> categoriesIds;
    ArrayList<String> categoriesTitle;
    public static String CATID = "CAT_ID";
    public static String opener= "cat";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_categories);
        categoriesList = (ListView) findViewById(R.id.quiz_categories_ListView);
        quizController = new QuizController();
        categoriesIds = new ArrayList<Integer>();
        categoriesTitle = new ArrayList<String>();
        fillListCategories();
        fillListCategoriesIds();

        categoriesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                openQuizzes(categoriesIds.get(i));
            }
        });

    }


    void fillListCategories() {

        quizController.getCategories(new OnTaskListeners.List() {
            @Override
            public void onSuccess(ArrayList<Object> result) {

                categoriesTitle = (ArrayList<String>) (Object) result;
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(quiz_categories.this, android.R.layout.simple_selectable_list_item, categoriesTitle);
                categoriesList.setAdapter(adapter);
            }
        });


    }

    void fillListCategoriesIds() {

        quizController.getCategoriesIds(new OnTaskListeners.List() {
            @Override
            public void onSuccess(ArrayList<Object> result) {

                categoriesIds = (ArrayList<Integer>) (Object) result;

            }
        });

    }

    void openQuizzes(Integer id) {

        Intent i = new Intent(getApplicationContext(), quizzes.class);
        i.putExtra(CATID, id);
        startActivity(i);


    }

}
