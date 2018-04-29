package com.example.android.easyc.Views;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.android.easyc.Controllers.QuizController;
import com.example.android.easyc.Interfaces.OnTaskListeners;
import com.example.android.easyc.R;

import java.util.ArrayList;

public class quizzes extends AppCompatActivity {

    QuizController quizController;
    Integer catId;
    Button submit;
    ListView quizListView;
    ArrayList<quiz> QuizList;
    ArrayList<String> questions;
    ArrayList<Integer> answers;
    ArrayList<Integer> ids;
    quiz_adapter qadapter;
    int rightAnswers = 0;
    boolean questionDone = false;
    boolean answersDone = false;
    boolean idsDone = false;
    public static String opener;
    int level;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quizzes);
        QuizList = new ArrayList<quiz>();
        questions = new ArrayList<String>();
        answers = new ArrayList<Integer>();
        ids = new ArrayList<Integer>();
        opener = quiz_options.opener;
        quizController = new QuizController();
        catId = getIntent().getIntExtra(quiz_categories.CATID, 1);
        level = getIntent().getIntExtra(quiz_options.opener, 1);
        quizListView = (ListView) findViewById(R.id.quiz_ListView);
        submit = findViewById(R.id.submitbutton);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!checkToSubmit())
                    quizController.toast("please answer all questions", getApplicationContext());
                else {
                    getAnswers();
                    quizController.toast("you got " + rightAnswers + " right & " + (answers.size() - rightAnswers) + " wrong", getApplicationContext());

                }


            }
        });


        fillQuiz();


    }


    void fillQuiz() {

        if (opener == "cat") {
            quizController.getQuizByCategoryId(catId, new OnTaskListeners.threeLists() {
                @Override
                public void onSuccess(ArrayList<Object> result1, ArrayList<Object> result2, ArrayList<Object> result3) {


                    questions = (ArrayList<String>) (Object) result2;
                    ids = (ArrayList<Integer>) (Object) result1;
                    answers = (ArrayList<Integer>) (Object) result3;

                    if (questions != null) {
                        for (int i = 0; i < questions.size(); i++) {
                            quiz q = new quiz();
                            try {

                                q.setQuestion(questions.get(i));
                                q.setAnswer(answers.get(i));
                                q.setId(ids.get(i));
                                QuizList.add(q);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }

                        qadapter = new quiz_adapter(quizzes.this, QuizList);


                        quizListView.setAdapter(qadapter);

                    }
                }
            });

        } else if (opener == "lev") {

            // same but with level
        } else if (opener == "lev_up") {


            //same but the the level after
            //check 3la max level
        }


    }

    void getAnswers() {
        View v;
        for (int i = 0; i < quizListView.getCount(); i++) {
            v = quizListView.getChildAt(i);
            RadioButton rb = v.findViewById(R.id.trueButton);
            RadioButton fb = v.findViewById(R.id.falseButton);
            if (answers.get(i) == 1) {
                if (rb.isChecked())
                    rightAnswers++;

            } else {
                if (fb.isChecked())
                    rightAnswers++;

            }

        }

    }

    void initQuestions() {
        quizController.getQuestions(catId, new OnTaskListeners.List() {
            @Override
            public void onSuccess(ArrayList<Object> result) {
                questions = (ArrayList<String>) (Object) result;
                questionDone = true;

            }
        });

    }

    void initAnswers() {
        quizController.getAnswers(catId, new OnTaskListeners.List() {
            @Override
            public void onSuccess(ArrayList<Object> result) {
                answers = (ArrayList<Integer>) (Object) result;
                answersDone = true;


            }
        });
    }


    boolean done() {
        if (answersDone && questionDone && idsDone)
            return true;
        else return false;


    }

    void initIds() {
        quizController.getIds(catId, new OnTaskListeners.List() {
            @Override
            public void onSuccess(ArrayList<Object> result) {

                ids = (ArrayList<Integer>) (Object) result;
                idsDone = true;


            }
        });
    }


    boolean checkToSubmit() {

        View v;

        for (int i = 0; i < quizListView.getCount(); i++) {
            v = quizListView.getChildAt(i);
            RadioGroup r = v.findViewById(R.id.addTrueFalseGroup);
            if (r.getCheckedRadioButtonId() == -1) {
                return false;
            }

        }
        return true;


    }

}
