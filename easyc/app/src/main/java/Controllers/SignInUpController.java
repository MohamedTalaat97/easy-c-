package Controllers;

import java.sql.ResultSet;

import Interfaces.OnTaskListeners;

/**
 * Created by KhALeD SaBrY on 12-Mar-18.
 */

public class SignInUpController extends Controller {

    //be aware that it must be the return value to the view is void so you have to send the view with it
    public void signIn(String name, String password, final OnTaskListeners.Bool listener) {
// this '@' to check if the user accessed by username or email
        if (name.indexOf('@') < -1) {
            databaseAdapter().selectUserIdByUserName(name, password, new OnTaskListeners.Result() {
                @Override
                public void onSuccess(ResultSet data) {
                    if (checkIfFound(data))
                        dataModel().setUserId((Integer) resultToValue(data));
                    //next here we take an action
                    listener.onSuccess(checkIfFound(data));
                }
            });
        } else {
            databaseAdapter().selectEmail(name, password, new OnTaskListeners.Result() {
                        @Override
                        public void onSuccess(ResultSet data) {
                            if (checkIfFound(data))
                                dataModel().setUserId((Integer) resultToValue(data));
                            //next here we take an action

                            listener.onSuccess(checkIfFound(data));

                        }
                    }


            );
        }
    }

    ///////////////////////////
    public void signUp(final String username, String password, String type, String age, final String email, final OnTaskListeners.Word listener) {

        databaseAdapter().insertNewUser(username, password, type.charAt(0), age, email, new OnTaskListeners.Bool() {
            @Override
            public void onSuccess(Boolean data) {
                if (data)
                    listener.onSuccess("Successfully Finished");
                else
                    listener.onSuccess("Failed");
            }
        });

    }


    public void checkUserName(String username, final OnTaskListeners.Bool listener) {
        databaseAdapter().selectUserUsername(username, new OnTaskListeners.Result() {
            @Override
            public void onSuccess(ResultSet data) {
                String sv = (String) resultToValue(data);
                listener.onSuccess(checkIfFound(data));
            }
        });
    }

    public void checkEmail(String email, final OnTaskListeners.Bool listener) {
        databaseAdapter().selectUserEmail(email, new OnTaskListeners.Result() {
            @Override
            public void onSuccess(ResultSet data) {
                listener.onSuccess(checkIfFound(data));
            }
        });
    }
}
