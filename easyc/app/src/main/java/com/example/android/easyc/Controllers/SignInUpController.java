package com.example.android.easyc.Controllers;

import android.widget.Toast;

import com.example.android.easyc.Interfaces.OnTaskListeners;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * Created by KhALeD SaBrY on 12-Mar-18.
 */

public class SignInUpController extends Controller {

    //be aware that it must be the return value to the view is void so you have to send the view with it
    public void signIn(String name, String password, final OnTaskListeners.Bool listener) {
// this '@' to check if the user accessed by username or email
        if (name.indexOf('@') == -1) {
            databaseAdapter().selectUserName(name, password, new OnTaskListeners.Result() {
                @Override
                public void onSuccess(ResultSet data) {
                    if (checkIfFound(data))
                        dataModel().setUserId((Integer) getOneValue(data));
                    //next here we take an action
                    listener.onSuccess(checkIfFound(data));
                }
            });
        } else {
            databaseAdapter().selectEmail(name, password, new OnTaskListeners.Result() {
                        @Override
                        public void onSuccess(ResultSet data) {
                            if (checkIfFound(data))
                                dataModel().setUserId((Integer) getOneValue(data));
                            //next here we take an action

                            listener.onSuccess(checkIfFound(data));

                        }
                    }


            );
        }
    }

    ///////////////////////////
    public void signUp(String name, String password, String type, String age, String email, final OnTaskListeners.Bool listener) {
// this '@' to check if the user accessed by username or email
        databaseAdapter().insertNewUser(name, password, type.charAt(0), age, email, new OnTaskListeners.Bool() {
            @Override
            public void onSuccess(Boolean data) {
                listener.onSuccess(data);
            }
        });


    }
}
