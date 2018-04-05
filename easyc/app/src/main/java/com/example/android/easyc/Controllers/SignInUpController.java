package com.example.android.easyc.Controllers;

import java.sql.ResultSet;
import java.util.ArrayList;

import com.example.android.easyc.Interfaces.OnTaskListeners;

/**
 * Created by KhALeD SaBrY on 12-Mar-18.
 */

public class SignInUpController extends Controller {

    //get user Type
    public char getType() {
        return userData().getUserType();
    }

    //be aware that it must be the return value to the view is void so you have to send the view with it
    public void signIn(String name, String password, final OnTaskListeners.Word listener) {

        databaseAdapter().selectUserIdTypeSuspendedLevelName(name, password, new OnTaskListeners.Result() {
            @Override
            public void onSuccess(ResultSet data) {
                if (checkIfFound(data)) {
                    if (resultToValue(data, 2).toString().matches("I")) {
                        if ((boolean) resultToValue(data, 3) == true) {
                            listener.onSuccess("wait untill the admin approve on your request");
                            return;
                        }
                        userData().setUserType('I');
                    } else if (resultToValue(data, 2).toString().matches("S")) {
                        if ((boolean) resultToValue(data, 3) == true) {
                            listener.onSuccess("you have been suspended for something you did and it was wrong");
                            return;
                        }
                        userData().setUserType('S');
                        userData().setUserLevel((Integer) resultToValue(data, 4));
                    } else {
                        userData().setUserType('A');

                    }
                    userData().setUserId((Integer) resultToValue(data));
                    listener.onSuccess("true");
                } else
                    //next here we take an action
                    listener.onSuccess("check your username/password");
            }
        });
    }

    //when you sign up
    public void signUp(final String username, String password, String type, String age, final String email, String requestText, final OnTaskListeners.Bool listener) {

        boolean suspended;
        if (type.charAt(0) == 'S')
            suspended = false;
        else
            suspended = true;
        databaseAdapter().insertUser(username, password, type.charAt(0), age, email, suspended, requestText, new OnTaskListeners.Bool() {
            @Override
            public void onSuccess(Boolean data) {
                listener.onSuccess(data);
            }
        });

    }

    //check if the username is in database
    public void checkUserName(String username, final OnTaskListeners.Bool listener) {
        databaseAdapter().selectUserUsername(username, new OnTaskListeners.Result() {
            @Override
            public void onSuccess(ResultSet data) {
                listener.onSuccess(checkIfFound(data));
            }
        });
    }

    //check if the email is in the database
    public void checkEmail(String email, final OnTaskListeners.Bool listener) {
        databaseAdapter().selectUserEmail(email, new OnTaskListeners.Result() {
            @Override
            public void onSuccess(ResultSet data) {
                listener.onSuccess(checkIfFound(data));
            }
        });
    }


    //Admin

    //get all requests
    public void getRequests(final OnTaskListeners.IdAndList listener) {
        databaseAdapter().selectUserIdUserName(new OnTaskListeners.Result() {
            @Override
            public void onSuccess(ResultSet data) {
                ArrayList<Integer> ids = (ArrayList<Integer>) (Object) resultToArray(data, 1);
                ArrayList<Object> usernames = resultToArray(data, 2);
                listener.onSuccess(ids, usernames);


            }
        });
    }


    //get a request to show
    public void getOneRequest(Integer id, final OnTaskListeners.Word listener) {
        databaseAdapter().selectUserRequest(id, new OnTaskListeners.Result() {
            @Override
            public void onSuccess(ResultSet data) {
                listener.onSuccess((String) resultToValue(data));
            }
        });
    }


    //accept request using id
    public void acceptRequest(Integer id, final OnTaskListeners.Bool listener) {
        databaseAdapter().updateUserSuspendedRequest(id, false, new OnTaskListeners.Bool() {
            @Override
            public void onSuccess(Boolean result) {
                listener.onSuccess(result);
            }
        });
    }


    //deny specific reuqest using id
    public void denyRequest(Integer id, final OnTaskListeners.Bool listener) {
        databaseAdapter().updateUserSuspendedRequest(id, true, new OnTaskListeners.Bool() {
            @Override
            public void onSuccess(Boolean result) {
                listener.onSuccess(result);
            }
        });
    }
}
