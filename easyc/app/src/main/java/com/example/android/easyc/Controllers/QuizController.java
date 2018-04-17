package com.example.android.easyc.Controllers;


import com.example.android.easyc.Interfaces.OnTaskListeners;

import java.sql.ResultSet;

public class QuizController extends Controller {



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


}
