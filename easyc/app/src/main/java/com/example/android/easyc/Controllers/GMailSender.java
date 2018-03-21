package com.example.android.easyc.Controllers;
import android.os.AsyncTask;

import com.example.android.easyc.Interfaces.OnTaskListeners;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.Security;
import java.util.Properties;

public class GMailSender extends javax.mail.Authenticator {
    private String mailhost = "smtp.gmail.com";
    private String sender;
    private String password;
    private Session session;

    static {
        Security.addProvider(new com.example.android.easyc.Controllers.JSSEProvider());
    }

    public GMailSender() {
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

   public void sendEmail(final String subject, final String body, final String recipient, final OnTaskListeners.Word listener) {

        new AsyncTask<Void, Void, String>() {
            public String state = null;

            @Override
            protected String doInBackground(Void... params) {
                try {
                    state = sendMail(subject,
                            body,
                            recipient,listener);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return state;
            }

            @Override
            protected void onPostExecute(String data) {
                listener.onSuccess(data);
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }
        }.execute();
    }




    protected synchronized String sendMail(String subject, String body, String recipients,OnTaskListeners.Word listener) throws Exception {
        try{
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
        }catch(Exception e){
            return "Failed to send the email";
        }
        return "please check your email inbox";
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