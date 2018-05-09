package com.example.android.easyc.Controllers;

import com.example.android.easyc.Interfaces.OnTaskListeners;

import java.sql.ResultSet;

/**
 * Created by MAN CENTER on 05-Apr-18.
 */

public class StudentController extends Controller {


    public void getUserName(int id, final OnTaskListeners.Word listener) {
        databaseAdapter().selectUserUsername(id, new OnTaskListeners.Result() {
            @Override
            public void onSuccess(ResultSet data) {
                if (!checkIfFound(data))
                    return;
                listener.onSuccess((String) resultToValue(data));
            }
        });
    }

    public void getUserLevel(int id, final OnTaskListeners.Number listener) {
//        databaseAdapter().selectUserLevel(id, new OnTaskListeners.Result() {
//            @Override
//            public void onSuccess(ResultSet data) {
//                if (!checkIfFound(data))
//                    return;
//                listener.onSuccess((int) resultToValue(data));
//            }
//        });

    }

    public int getLevel()
    {

        return userData().getUserLevel();
    }

    public int getUserId() {


        return userData().getUserId();
    }

    public String getUsername() {
        return userData().getUserName();
    }

    public int getUserLevel() {
        return userData().getUserLevel();
    }


}
