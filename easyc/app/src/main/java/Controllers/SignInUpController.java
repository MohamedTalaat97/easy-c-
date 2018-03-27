package Controllers;

import java.sql.ResultSet;
import java.util.ArrayList;

import Interfaces.OnTaskListeners;
import Views.categories;
import Views.put_opinion;
import Views.show_opinions;
import Views.topic;

/**
 * Created by KhALeD SaBrY on 12-Mar-18.
 */

public class SignInUpController extends Controller {


    //this function is related to SignIn Function
    private void accessSignIn(ResultSet data, OnTaskListeners.Word listener) {

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
                        dataModel().setUserType('I');
                    } else if (resultToValue(data, 2).toString().matches("S")) {
                        if ((boolean) resultToValue(data, 3) == true) {
                            listener.onSuccess("you have been suspended for something you did and it was wrong");
                            return;
                        }
                        dataModel().setUserType('S');
                        dataModel().setUserLevel((Integer) resultToValue(data, 4));
                    } else {
                        dataModel().setUserType('A');

                    }
//                    dataModel().setUserName(resultToValue(data, 5).toString());
                    dataModel().setUserId((Integer) resultToValue(data));
                    listener.onSuccess("true");
                } else
                    //next here we take an action
                    listener.onSuccess("check your username/password");
            }
        });
    }

    ///////////////////////////
    public void signUp(final String username, String password, String type, String age, final String email,String requestText, final OnTaskListeners.Bool listener) {

        boolean suspended;
        if (type.charAt(0) == 'S')
            suspended = false;
        else
            suspended = true;
        databaseAdapter().insertUser(username, password, type.charAt(0), age, email, suspended,requestText, new OnTaskListeners.Bool() {
            @Override
            public void onSuccess(Boolean data) {
                listener.onSuccess(data);
            }
        });

    }


    public void checkUserName(String username, final OnTaskListeners.Bool listener) {
        databaseAdapter().selectUserUsername(username, new OnTaskListeners.Result() {
            @Override
            public void onSuccess(ResultSet data) {
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


    public  void goTo(OnTaskListeners.classes listener)
    {
       /* if(dataModel().getUserType() == 'A')
        {
            listener.onSuccess(show_opinions.class);
        }
        else if(dataModel().getUserType() == 'I')
        {
            listener.onSuccess(show_opinions.class);
        }
        else
            listener.onSuccess(put_opinion.class);*/
        listener.onSuccess(categories.class);
    }






    //Admin

    public void getRequests(final OnTaskListeners.IdAndList listener)
    {
        databaseAdapter().selectUserIdUserName(new OnTaskListeners.Result() {
            @Override
            public void onSuccess(ResultSet data) {
                ArrayList<Integer> ids = (ArrayList<Integer>)(Object) resultToArray(data,1);
                ArrayList<Object> usernames = resultToArray(data,2);
                listener.onSuccess(ids,usernames);


            }
        });
    }




    public void getOneRequest(Integer id, final OnTaskListeners.Word listener)
    {
        databaseAdapter().selectUserRequest(id, new OnTaskListeners.Result() {
            @Override
            public void onSuccess(ResultSet data) {
                listener.onSuccess((String) resultToValue(data));
            }
        });
    }

    public  void handleRequest(Integer id, boolean state, final OnTaskListeners.Bool listener)
    {
        databaseAdapter().updateUserSuspendedRequest(id, state, new OnTaskListeners.Bool() {
            @Override
            public void onSuccess(Boolean result) {
                listener.onSuccess(result);
            }
        });

    }
}
