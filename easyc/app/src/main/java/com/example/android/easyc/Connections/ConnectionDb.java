package com.example.android.easyc.Connections;

import android.content.Context;

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
    private String username = "Team";
    private String password = "Team";
    private String dbName = "c++";
    private String host;
    private Context context;
    private NetworkWifiConnection connection;
    // private static connection server;

    //to set host ip
    public void setHost(String host) {
        this.host = host;
    }

    // Exists only to defeat instantiation.
    private ConnectionDb() {

    }

    //this is singletom pattern
    public static ConnectionDb getInstance() {
        if (instance == null) {
            instance = new ConnectionDb();
        }
        return instance;
    }


    //to check if there is a connection
    public boolean checkConnection() {
        try {
            if (instance.c != null)
                return true;
        } catch (Exception e) {
            e.printStackTrace();
        }


        return false;
    }

    //use this function if you want to connect to database
    public void connect(Context context) {

        this.context = context;
        connection = new NetworkWifiConnection(context);
        connection.execute();

    }


    //connect to the server
    public void serverConnect() {
        //if you want to put the host static for AVD uncomment the next line
       host = "10.168.1.21";

        url = "jdbc:mysql://" + host + ":3306/" + dbName + "?autoReconnect=true&useSSL=false";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            c = DriverManager.getConnection(url, username, password);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        catch (Exception e)
        {

        }
    }

    //reconnect the connection if
    public void reconnect() {
        if(instance.c != null)
            try {
                if(instance.c.isValid(50))
                    return;
            } catch (SQLException e) {
                e.printStackTrace();
            }

        instance.c = null;
        connection.wifiConnect();
    }
}
