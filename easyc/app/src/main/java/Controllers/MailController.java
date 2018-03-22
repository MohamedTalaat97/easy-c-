package Controllers;

import Interfaces.OnTaskListeners;

import java.sql.ResultSet;

import Connections.MailSender;

/**
 * Created by KhALeD SaBrY on 22-Mar-18.
 */

public class MailController extends Controller {
    MailSender sender;


    public MailController()
    {
        sender = new MailSender();
    }


    public void forgetUserNameOrPassword(final String email, final OnTaskListeners.Word listener)
    {

        databaseAdapter().selectUserUsernamePassword(email, new OnTaskListeners.Result() {
            @Override
            public void onSuccess(ResultSet data) {
                String username = (String) resultToValue(data,1);
                String password = (String) resultToValue(data,2);

                sender.setRecipientEmail(email);
                sender.setSubject("Retore your username/password");
                sender.setBody("thanks for using our app we are hoping that you have a great time while using our app \n our app cost is 200.000$ talaat said that so be careful next time you forget your password or username \n \n username :" + username +"\n password : "+password);
                sender.sendEmail(listener);

            }
        });

    }

}
