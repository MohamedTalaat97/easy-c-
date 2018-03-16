package com.example.android.easyc.Controllers;

import com.example.android.easyc.Interfaces.OnTaskListeners;

import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * Created by MAN CENTER on 15-Mar-18.
 */

public class CourseController extends Controller {


    public void getCategories(final OnTaskListeners.List listener) {
        databaseAdapter().selectCategories(new OnTaskListeners.Result() {
            @Override
            public void onSuccess(ResultSet data) {
                listener.onSuccess(resultToArray(data));
            }
        });
    }



    public void getCatagoryId(String name,final OnTaskListeners.Number listener)
    {
        databaseAdapter().selectCatagoryIdByName(name, new OnTaskListeners.Result() {
            @Override
            public void onSuccess(ResultSet data) {
                listener.onSuccess((int)resultToValue(data));
            }
        });






    }
}