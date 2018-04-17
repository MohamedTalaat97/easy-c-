package com.example.android.easyc.Views;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.example.android.easyc.Controllers.QuizController;
import com.example.android.easyc.Interfaces.OnTaskListeners;
import com.example.android.easyc.R;

import java.util.ArrayList;

public class quizzes extends AppCompatActivity {

    QuizController quizController;
    Integer catId;
    ListView quizListView;
    ArrayList<quiz> QuizList;
    ArrayList<String> questions;
    ArrayList<Integer> answers;
    ArrayList<Integer> ids;
    quiz_adapter qadapter;
    boolean questionDone =false;
    boolean answersDone= false;
    boolean idsDone=false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quizzes);
        QuizList = new ArrayList<quiz>();
        questions = new ArrayList<String>();
        answers = new ArrayList<Integer>();
        ids=new ArrayList<Integer>();
        quizController = new QuizController();
        catId = getIntent().getIntExtra(quiz_categories.CATID, 1);
        quizListView = (ListView) findViewById(R.id.quiz_ListView);
    initQuestions();
        initAnswers();
        initIds();

       // quiz q= new quiz();
      //  q.setId(1);
       // q.setQuestion("what the fuck");
       // q.setAnswer(1);


      //  QuizList.add(q);


    }

    void initQuestions() {
        quizController.getQuestions(catId, new OnTaskListeners.List() {
            @Override
            public void onSuccess(ArrayList<Object> result) {
                questions = (ArrayList<String>) (Object) result;
                questionDone = true;
                if (done())
                    initQuiz();
            }
        });

    }

    void initAnswers() {
        quizController.getAnswers(catId, new OnTaskListeners.List() {
            @Override
            public void onSuccess(ArrayList<Object> result) {
                answers = (ArrayList<Integer>) (Object) result;
                answersDone =true;
                if (done())
                    initQuiz();
            }
        });
    }


    boolean done()
    {
        if (answersDone && questionDone && idsDone)
            return true;
        else return false;


    }
    void initIds() {
        quizController.getIds(catId, new OnTaskListeners.List() {
            @Override
            public void onSuccess(ArrayList<Object> result) {

                ids = (ArrayList<Integer>) (Object) result;
                idsDone =true;
                if (done())
                    initQuiz();
            }
        });
    }

    void initQuiz() {

        if (questions != null) {
            for (int i = 0; i < questions.size(); i++) {
                quiz q = new quiz();

                q.setQuestion(questions.get(i));
     ///           q.setAnswer(answers.get(i));
             //   q.setId(ids.get(i));
                QuizList.add(q);

            }
        }
        qadapter = new quiz_adapter(quizzes.this, QuizList);


        quizListView.setAdapter(qadapter);
    }

}
