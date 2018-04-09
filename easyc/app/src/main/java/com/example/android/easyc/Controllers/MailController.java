package com.example.android.easyc.Controllers;

import com.example.android.easyc.Connections.MailSender;
import com.example.android.easyc.Interfaces.OnTaskListeners;

import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * Created by KhALeD SaBrY on 22-Mar-18.
 */

public class MailController extends Controller {
    private MailSender sender;
    private ArrayList<String> emailMessage = new ArrayList<String>();

    public MailController() {
        sender = new MailSender();
    }


    //get the company email
    public String getCompanyEmail() {
        return sender.getCompanyEmail();
    }


    //message if you forgot your password
    public void forgetUserNameOrPassword(final String email, final OnTaskListeners.Word listener) {

        databaseAdapter().selectUserUsernamePassword(email, new OnTaskListeners.Result() {
            @Override
            public void onSuccess(ResultSet data) {
                if (!checkIfFound(data))
                    return;
                String username = (String) resultToValue(data, 1);
                String password = (String) resultToValue(data, 2);

                sender.setRecipientEmail(email);
                sender.setSubject("Restore your username/password");
                sender.setBody("thanks for using our app we are hoping that you have a great time while using our app \n be careful next time you forget your password or username \n \n username :" + username + "\n password : " + password);
                sender.sendEmail(listener);

            }
        });

    }

    // when you sign up this message sent for you
    public void sendWelcomeMessage(String email) {
        sender.setRecipientEmail(email);
        sender.setSubject("Welcome to our app");
        sender.setBody("Easy C++ is an educational android app used for explaining programming concepts \n" +
                "in C++ language. In it you can take lessons at your own pace, test your knowledge in its quizzes, interact with fellow learners and ask instructors for help, there is discussion room where you could put your question or see other’s questions and see the replies or you can put your reply if you know the answer.\n" +
                "After you get to the last level you can apply to become an instructor.\n");
        sender.sendEmail(new OnTaskListeners.Word() {
            @Override
            public void onSuccess(String result) {

            }
        });
    }



    public void sendWarningChangeInUsername() {
        databaseAdapter().selectUserEmail(userData().getUserId(), new OnTaskListeners.Result() {
            @Override
            public void onSuccess(ResultSet data) {
                if(!checkIfFound(data))
                    return;
                sender.setRecipientEmail((String) resultToValue(data));
                sender.setSubject("you have recently changed your username");
                sender.setBody("you have recently changed your username");
                sender.sendEmail(new OnTaskListeners.Word() {
                    @Override
                    public void onSuccess(String result) {

                    }
                });

            }
        });

    }

    public void sendWarningChangeInPassword() {
        databaseAdapter().selectUserEmail(userData().getUserId(), new OnTaskListeners.Result() {
            @Override
            public void onSuccess(ResultSet data) {
                if(!checkIfFound(data))
                    return;
                sender.setRecipientEmail((String) resultToValue(data));
                sender.setSubject("you have recently changed your password");
                sender.setBody("you have recently changed your password");
                sender.sendEmail(new OnTaskListeners.Word() {
                    @Override
                    public void onSuccess(String result) {

                    }
                });

            }
        });
    }





}
