package com.example.android.easyc.Controllers;

/**
 * Created by MAN CENTER on 05-Apr-18.
 */

public class StudentController extends Controller {


    /*
    public void getUserName(int id, final OnTaskListeners.Word listener) {
        databaseAdapter().selectUsername(id, new OnTaskListeners.Result() {
            @Override
            public void onSuccess(ResultSet data) {
                listener.onSuccess((String) resultToValue(data));
            }
        });
    }

    public void getUserLevel(int id, final OnTaskListeners.Number listener) {
        databaseAdapter().selectUserLevel(id, new OnTaskListeners.Result() {
            @Override
            public void onSuccess(ResultSet data) {
                listener.onSuccess((int) resultToValue(data));
            }
        });
    }
*/
    public int getUserId() {


        return userData().getUserId();
    }

    public String getUsername()
    {
        return  userData().getUserName();
    }

    public int getUserLevel()
    {
        return userData().getUserLevel();
    }


}