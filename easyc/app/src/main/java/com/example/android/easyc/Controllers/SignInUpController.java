package com.example.android.easyc.Controllers;

import com.example.android.easyc.Interfaces.OnTaskListeners;
import com.example.android.easyc.Interfaces.ViewListener;

import java.sql.ResultSet;

/**
 * Created by KhALeD SaBrY on 12-Mar-18.
 */

public class SignInUpController extends Controller {

//be aware that it must be the return value to the view is void so you have to send the view with it
    public void signIn(String name, String password, final ViewListener.Bool listener)
    {
// this '@' to check if the user accessed by username or email
        if(name.indexOf('@') == -1) {
            databaseAdapter().selectUserName(name, password, new OnTaskListeners.Result() {
                @Override
                public void onSuccess(ResultSet data) {
                    if (checkIfFound(data))
                        databaseLegacy().setUserId((Integer) getOneValue(data));
                    //next here we take an action
                    listener.OnSuccess(checkIfFound(data));

                }
            });
        }
        else {
            databaseAdapter().selectEmail(name, password, new OnTaskListeners.Result() {
                @Override
                public void onSuccess(ResultSet data) {
                    if (checkIfFound(data))
                        databaseLegacy().setUserId((Integer) getOneValue(data));
                    //next here we take an action

                    listener.OnSuccess(checkIfFound(data));

                }
            }



            );
        }
    }
}
