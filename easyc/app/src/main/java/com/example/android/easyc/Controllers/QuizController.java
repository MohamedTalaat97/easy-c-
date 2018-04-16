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




}
