package com.example.android.easyc.Connections;

import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds;
import android.provider.ContactsContract.CommonDataKinds.Email;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.Security;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.example.android.easyc.Interfaces.OnTaskListeners;

public class MailSender extends javax.mail.Authenticator {
    private String mailhost = "smtp.gmail.com";
    private String sender;
    private String password;
    private Session session;
    private String subject;
    private String body;
    private String recipientEmail;
    public String getCompanyEmail()
    {
        return sender;
    }

    private String resultStateText;
    static {
        Security.addProvider(new JSSEProvider());
    }

    public MailSender() {
        this.sender = "CairoAvengers@gmail.com";
        this.password = "CairoAvengers2020";

        Properties props = new Properties();
        props.setProperty("mail.transport.protocol", "smtp");
        props.setProperty("mail.host", mailhost);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.socketFactory.fallback", "false");
        props.setProperty("mail.smtp.quitwait", "false");

        session = Session.getDefaultInstance(props, this);
    }


    protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(sender, password);
    }

    private Boolean checkBeforeSend() {
        if (subject.matches("") || body.matches("") || recipientEmail.matches("")) {
            return false;
        }

        return true;

    }

    public void sendEmail(final OnTaskListeners.Word listener) {
        if (!checkBeforeSend())
        {
            listener.onSuccess("there is some error in your code");
            return;
        }

            new AsyncTask<Void, Void, String>() {
                public String state = "";

                @Override
                protected String doInBackground(Void... params) {
                    try {
                        sendMail(subject,
                                body,
                                recipientEmail, listener);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return state;
                }

                @Override
                protected void onPostExecute(String data) {
                    listener.onSuccess(resultStateText);
                }

                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                }
            }.execute();
    }


    //send the email
    private  void sendMail(String subject, String body, String recipients, OnTaskListeners.Word listener) throws Exception {
        try {
            MimeMessage message = new MimeMessage(session);
            DataHandler handler = new DataHandler(new ByteArrayDataSource(body.getBytes(), "text/plain"));
            message.setSender(new InternetAddress(sender));
            message.setSubject(subject);
            message.setDataHandler(handler);

            if (recipients.indexOf(',') > 0)
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipients));
            else
                message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipients));
            Transport.send(message);
        } catch (Exception e) {
           resultStateText = "Failed to send the email";
            return;
        }
        resultStateText = "check your email inbox";
    }

    public void setRecipientEmail(String recipientEmail) {
        this.recipientEmail = recipientEmail;
    }

    //set the content of email
    public void setBody(String body) {
        this.body = body;
    }

    //set the subject of email
    public void setSubject(String subject) {
        this.subject = subject;
    }

    protected class ByteArrayDataSource implements DataSource {
        private byte[] data;
        private String type;

        protected ByteArrayDataSource(byte[] data, String type) {
            super();
            this.data = data;
            this.type = type;
        }

        protected ByteArrayDataSource(byte[] data) {
            super();
            this.data = data;
        }

        protected void setType(String type) {
            this.type = type;
        }

        public String getContentType() {
            if (type == null)
                return "application/octet-stream";
            else
                return type;
        }

        public InputStream getInputStream() throws IOException {
            return new ByteArrayInputStream(data);
        }

        public String getName() {
            return "ByteArrayDataSource";
        }

        public OutputStream getOutputStream() throws IOException {
            throw new IOException("Not Supported");
        }
    }
}