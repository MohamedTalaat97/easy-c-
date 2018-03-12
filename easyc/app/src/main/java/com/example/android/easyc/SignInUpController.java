package com.example.android.easyc;

import android.view.View;

import java.sql.ResultSet;

/**
 * Created by KhALeD SaBrY on 12-Mar-18.
 */

public class SignInUpController extends Controller {
    Controller controller = new Controller();

//be aware that it must be the return value to the view is void so you have to send the view with it
    public void signIn(String name, String password, View view)
    {
// this '@' to check if the user accessed by username or email
        if(name.indexOf('@') == -1) {
            controller.databaseAdapter().selectUserName(name, password, new OnTaskListeners.Result() {
                @Override
                public void onSuccess(ResultSet data) {
                    if (controller.checkIfFound(data))
                        controller.databaseLegacy().setUserId((Integer) getOneValue(data));
                    //next here we take an action
                }
            });
        }
        else {
            controller.databaseAdapter().selectEmail(name, password, new OnTaskListeners.Result() {
                @Override
                public void onSuccess(ResultSet data) {
                    if (controller.checkIfFound(data))
                        controller.databaseLegacy().setUserId((Integer) getOneValue(data));
                    //next here we take an action
                }
            });
        }
    }
}
