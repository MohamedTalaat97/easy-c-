package com.example.android.easyc.Views;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.example.android.easyc.Controllers.QuizController;
import com.example.android.easyc.Interfaces.OnTaskListeners;
import com.example.android.easyc.R;

import java.util.ArrayList;

public class add_quiz extends AppCompatActivity {

QuizController quizController;
    ArrayList<Integer> catIds;
    RadioGroup radioGroup;
    EditText question;
    Spinner chooseLevel;
    ArrayList<String> list;
    RadioButton trueButton;
    RadioButton falseButton;
    String Question;
    int answer;
    Button submit;



    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_quiz);
        quizController = new QuizController();
        catIds = new ArrayList<Integer>();

        quizController.getCategoriesIds(new OnTaskListeners.List() {
            @Override
            public void onSuccess(ArrayList<Object> result) {
                catIds = (ArrayList<Integer>) (Object) result;

            }
        });
    }





    void initData()
    {

        radioGroup = findViewById(R.id.addTrueFalseGroup);
        question = findViewById(R.id.addquestion);
        chooseLevel = findViewById(R.id.addspinner);
        trueButton=findViewById(R.id.addtrueButton);
        falseButton=findViewById(R.id.addfalseButton);
        submit = findViewById(R.id.submitbutton);


        ArrayAdapter<Integer> adp = new ArrayAdapter<Integer>(this,android.R.layout.simple_spinner_item, catIds);
        adp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        chooseLevel.setAdapter(adp);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (check())
                    add();

            }
        });
    }


    void add()
    {
        quizController.addQuestion((Integer)chooseLevel.getSelectedItem(), question.getText().toString(), getAnswer(), new OnTaskListeners.Bool() {
            @Override
            public void onSuccess(Boolean result) {
                quizController.toast("added succefully",getApplicationContext());

            }
        });



    }

    Integer getAnswer()
    {
        if (trueButton.isChecked())
            return 1;
        else return 0;

    }

    boolean check ()
    {



        if (question.getText().toString().matches("") && (trueButton.isChecked() || falseButton.isChecked()) )
        {

            quizController.toast("fill all fields",getApplicationContext());
            return false;
        }
        return true;
    }
}
