package com.example.android.easyc.Controllers;


import com.example.android.easyc.Interfaces.OnTaskListeners;

import java.sql.ResultSet;

public class QuizController extends Controller {


    public int getUserLevel()

    {

        return userData().getUserLevel();
    }
    public void setUserLevel(int u)

    {
         userData().setUserLevel(u);
    }

    public void getCategories(final OnTaskListeners.List listener) {
        databaseAdapter().selectCategories(new OnTaskListeners.Result() {
            @Override
            public void onSuccess(ResultSet data) {
                if (!checkIfFound(data))
                    return;
                listener.onSuccess(resultToArray(data));
            }
        });
    }
    public void getCategoriesIds(final OnTaskListeners.List listener) {
        databaseAdapter().selectCategoriesIds(new OnTaskListeners.Result() {
            @Override
            public void onSuccess(ResultSet data) {
                if (!checkIfFound(data))
                    return;
                listener.onSuccess(resultToArray(data));
            }
        });
    }



    public void getQuestions(int cat_id,final OnTaskListeners.List listener) {
        databaseAdapter().selectQuestions(cat_id,new OnTaskListeners.Result() {
            @Override
            public void onSuccess(ResultSet data) {
                if (!checkIfFound(data))
                    return;
                listener.onSuccess(resultToArray(data));
            }
        });
    }
    public void getCategoriedIds(final OnTaskListeners.List listener) {
        databaseAdapter().selectcategoryIds(new OnTaskListeners.Result() {
            @Override
            public void onSuccess(ResultSet data) {
                if (!checkIfFound(data))
                    return;
                listener.onSuccess(resultToArray(data));
            }
        });
    }


    public void getAnswers(int cat_id,final OnTaskListeners.List listener) {
        databaseAdapter().selectAnswers(cat_id,new OnTaskListeners.Result() {
            @Override
            public void onSuccess(ResultSet data) {
                if (!checkIfFound(data))
                    return;
                listener.onSuccess(resultToArray(data));
            }
        });
    }

    public void getGrades( final OnTaskListeners.twoLists listener) {

        int user_id = userData().getUserId();
        databaseAdapter().selectExamGradeDate(user_id,new OnTaskListeners.Result() {
            @Override
            public void onSuccess(ResultSet data) {
                if (!checkIfFound(data))
                    return;
                listener.onSuccess(resultToArray(data,1),resultToArray(data,2));

            }
        });
    }
    public void getQuizByCategoryId(int cat_id, final OnTaskListeners.threeLists listener) {
        databaseAdapter().selectQuizIdQuestionAnswerByCat(cat_id,new OnTaskListeners.Result() {
            @Override
            public void onSuccess(ResultSet data) {
                if (!checkIfFound(data))
                    return;
                listener.onSuccess(resultToArray(data,1),resultToArray(data,2),resultToArray(data,3));

            }
        });
    }
    public void getQuizByLevelId(int level, final OnTaskListeners.threeLists listener) {
        databaseAdapter().selectQuizIdQuestionAnswerByLevel(level,new OnTaskListeners.Result() {
            @Override
            public void onSuccess(ResultSet data) {
                if (!checkIfFound(data))
                    return;
                listener.onSuccess(resultToArray(data,1),resultToArray(data,2),resultToArray(data,3));

            }
        });
    }
    public void getQuizByLevelUp(int level, final OnTaskListeners.threeLists listener) {
        databaseAdapter().selectQuizIdQuestionAnswerToUp(level,new OnTaskListeners.Result() {
            @Override
            public void onSuccess(ResultSet data) {
                if (!checkIfFound(data))
                    return;
                listener.onSuccess(resultToArray(data,1),resultToArray(data,2),resultToArray(data,3));

            }
        });
    }
    public void getIds(int cat_id,final OnTaskListeners.List listener) {
        databaseAdapter().selectQuestionsIds(cat_id,new OnTaskListeners.Result() {
            @Override
            public void onSuccess(ResultSet data) {
                if (!checkIfFound(data))
                    return;
                listener.onSuccess(resultToArray(data));
            }
        });
    }

    public void addQuestion (Integer catId,String Question, Integer Answer,final OnTaskListeners.Bool listener)

    {
        int user_id = userData().getUserId();
        int user_level = userData().getUserLevel();

        databaseAdapter().insertQuestion(user_level, catId,user_id,Question,Answer ,new OnTaskListeners.Bool() {
            @Override
            public void onSuccess(Boolean result) {
                listener.onSuccess(result);
            }
        });
    }

    public void addScore(int score ,int  catId,String date,final OnTaskListeners.Bool listener)

    {
        int user_id = userData().getUserId();
        int user_level = userData().getUserLevel();

        databaseAdapter().insertExam(user_level, catId,user_id,score,date ,new OnTaskListeners.Bool() {
            @Override
            public void onSuccess(Boolean result) {
                listener.onSuccess(result);
            }
        });
    }


    public void upLevel(final OnTaskListeners.Bool listener)

    {
        int user_id = userData().getUserId();
        int user_level = userData().getUserLevel();


        databaseAdapter().updateUserLevel(user_id,user_level+1,new OnTaskListeners.Bool() {
            @Override
            public void onSuccess(Boolean result) {
                listener.onSuccess(result);
            }
        });
    }
}
