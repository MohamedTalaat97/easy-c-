package com.example.android.easyc.Connections;

import android.os.AsyncTask;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by KhALeD SaBrY on 03-Mar-18.
 */


public class ConnectionDb {
    //Variables
    private static ConnectionDb instance = null;
    public Connection c = null;
    private String url;
    private String username;
    private String password;
    private String dbName = "c++";

    //connect with th database
    private void registerInBackground() {
        new AsyncTask<Void, Void, Boolean>() {
            @Override
            protected Boolean doInBackground(Void... params) {
                try {
                    Class.forName("com.mysql.jdbc.Driver");
                    c = DriverManager.getConnection(url, username, password);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                    return false;
                } catch (SQLException e) {
                    e.printStackTrace();
                    return false;
                }
                return true;
            }

            @Override
            protected void onPostExecute(Boolean data) {
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }
        }.execute();
    }

    // Exists only to defeat instantiation.
    private ConnectionDb() {

    }

    //to instantiate a coonectiondb
    public static ConnectionDb getInstance() {
        if (instance == null) {
            instance = new ConnectionDb();
        }
        return instance;
    }


    //to check if there is a connection
    public boolean checkConnection() {
        if (c != null)
            return true;
        return false;
    }

    public void khaledDb() {
        url = "jdbc:mysql://192.168.0.103:3306/" + dbName + "?autoReconnect=true&useSSL=false";
        username = "khaled";
        password = "11121997K";
        registerInBackground();
    }

    public void TalaatDb() {
        url = "jdbc:mysql://192.168.1.5:3306/c++?autoReconnect=true&useSSL=false";
        username = "medo";
        password = "01115598525";
        registerInBackground();
    }

    public void AhmedDb() {
        url = "jdbc:mysql://192.168.0.104:3306/run?autoReconnect=true&useSSL=false";
        username = "khaled";
        password = "11121997K";
        registerInBackground();
    }

    public void KareemDb() {
        url = "jdbc:mysql://192.168.0.104:3306/run?autoReconnect=true&useSSL=false";
        username = "khaled";
        password = "11121997K";
        registerInBackground();
    }
}
