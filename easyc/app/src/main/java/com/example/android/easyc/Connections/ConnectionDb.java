package com.example.android.easyc.Connections;

import android.content.Context;
import android.os.AsyncTask;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by KhALeD SaBrY on 03-Mar-18.
 */


public class ConnectionDb {

    public enum connection {
        khaled,talaat,ahmed,kareem
    }
    //Variables
    private static ConnectionDb instance = null;
    public Connection c = null;
    private String url;
    private String username;
    private String password;
    private String dbName = "c++";
    private String host;
    private static boolean dynamic = false;
    private static connection server;

    //to set host ip
    public void setHost(String host) {
        this.host = host;
    }

    //to make the connection dynamic
    public static void setConnectionDynamic(Context context)
    {
        dynamic = true;
        NetworkWifiConnection connection = new NetworkWifiConnection(context);
        connection.execute();
    }
    //connect with th database
    private void registerInBackground() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                connect();
                return null;
            }

            @Override
            protected void onPostExecute(Void data) {
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
    //for static Connection
    public static ConnectionDb getInstance() {
        if (instance == null) {
            instance = new ConnectionDb();
        }
        return instance;
    }

    //for dynamic Connection
    public static ConnectionDb getInstance(Context contextId,connection serverId)
    {
        if (instance == null) {
            instance = new ConnectionDb();
            server = serverId;
            setConnectionDynamic(contextId);
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
        url = "jdbc:mysql://" + host + ":3306/" + dbName + "?autoReconnect=true&useSSL=false";
        username = "khaled";
        password = "11121997K";
        if(dynamic)
        connect();
        else
            registerInBackground();

        // registerInBackground();
    }




    public void talaatDb() {
        url = "jdbc:mysql://" + host + ":3306/" + dbName + "?autoReconnect=true&useSSL=false";
        username = "medo";
        password = "01115598525";
        if(dynamic)
            connect();
        else
            registerInBackground();
    }

    public void ahmedDb() {
        url = "jdbc:mysql://" + host + ":3306/" + dbName + "?autoReconnect=true&useSSL=false";
        username = "khaled";
        password = "11121997K";
        if(dynamic)
            connect();
        else
            registerInBackground();
    }

    public void kareemDb() {
        url = "jdbc:mysql://" + host + ":3306/" + dbName + "?autoReconnect=true&useSSL=false";
        username = "khaled";
        password = "11121997K";
        if(dynamic)
            connect();
        else
            registerInBackground();
    }





    void connect() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            c = DriverManager.getConnection(url, username, password);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void ServerConnect()
    {
        if(server.equals(connection.khaled))
            khaledDb();
        else if(server.equals(connection.ahmed))
            ahmedDb();
        else if(server.equals(connection.kareem))
            kareemDb();
        else if(server.equals(connection.talaat))
            talaatDb();
    }
}
