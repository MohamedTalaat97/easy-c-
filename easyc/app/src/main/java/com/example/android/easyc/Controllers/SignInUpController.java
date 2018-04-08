package com.example.android.easyc.Controllers;

import com.example.android.easyc.Interfaces.OnTaskListeners;

import java.sql.ResultSet;
import java.util.ArrayList;

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

        databaseAdapter().selectUserIdTypeSuspended(name, password, new OnTaskListeners.Result() {
            @Override
            public void onSuccess(ResultSet data) {
                if (!checkIfFound(data)) {
                    if (checkConnection())
                        //next here we take an action
                        listener.onSuccess("check your username/password");
                    else
                        listener.onSuccess("check your connection");
                    return;
                }

                if (resultToValue(data, 2).toString().matches("I")) {
                    if ((boolean) resultToValue(data, 3) == true) {
                        listener.onSuccess("wait untill the admin approve on your request");
                        return;
                    }

                   /* userData().setUserId((Integer) resultToValue(data,1));
                    databaseAdapter().selectUserLevel(userData().getUserId(), new OnTaskListeners.Result() {
                        @Override
                        public void onSuccess(ResultSet data) {
                            if (!checkIfFound(data))
                                return;
                            userData().setUserLevel((Integer) resultToValue(data));

                            databaseAdapter().selectUsername(userData().getUserId(), new OnTaskListeners.Result() {
                                @Override
                                public void onSuccess(ResultSet data) {
                                    if (!checkIfFound(data))
                                        return;
                                    userData().setUserName((String) resultToValue(data));
                                    listener.onSuccess("true");
                                }
                            });

                    userData().setUserType('I');*/
                } else if (resultToValue(data, 2).toString().matches("S")) {
                    if ((boolean) resultToValue(data, 3) == true) {
                        listener.onSuccess("you have been suspended for something you did and it was wrong");
                        return;
                    }
                    userData().setUserType('S');
                } else {
                    userData().setUserType('A');

                }
                userData().setUserId((Integer) resultToValue(data, 1));
                databaseAdapter().selectUserLevel(userData().getUserId(), new OnTaskListeners.Result() {
                    @Override
                    public void onSuccess(ResultSet data) {
                        if (!checkIfFound(data))
                            return;
                        userData().setUserLevel((Integer) resultToValue(data));

                        databaseAdapter().selectUserUsername(userData().getUserId(), new OnTaskListeners.Result() {
                            @Override
                            public void onSuccess(ResultSet data) {
                                if (!checkIfFound(data))
                                    return;
                                userData().setUserName((String) resultToValue(data));
                                listener.onSuccess("true");
                            }
                        });


                    }
                });


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


    public void updateUsername(String username, final OnTaskListeners.Bool listener) {
        databaseAdapter().updateUserUsername(userData().getUserId(), username, new OnTaskListeners.Bool() {
            @Override
            public void onSuccess(Boolean result) {
                listener.onSuccess(result);
            }
        });
    }

    public void updateEmail(String email, final OnTaskListeners.Bool listener) {
        databaseAdapter().updateUserEmail(userData().getUserId(), email, new OnTaskListeners.Bool() {
            @Override
            public void onSuccess(Boolean result) {
                listener.onSuccess(result);
            }
        });
    }


    public void updatePassword(String password, final OnTaskListeners.Bool listener) {
        databaseAdapter().updateUserPassword(userData().getUserId(), password, new OnTaskListeners.Bool() {
            @Override
            public void onSuccess(Boolean result) {
                listener.onSuccess(result);
            }
        });
    }


    //Admin

    //get all requests
    public void getRequests(final OnTaskListeners.IdAndList listener) {
        databaseAdapter().selectUserIdUserName(new OnTaskListeners.Result() {
            @Override
            public void onSuccess(ResultSet data) {
                if (!checkIfFound(data))
                    return;
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
                if (!checkIfFound(data))
                    return;
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
