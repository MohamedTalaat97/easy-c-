package Connections;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by KhALeD SaBrY on 03-Mar-18.
 */

public class ConnectionDb {
    //Variables
    private static ConnectionDb instance = null;
    public Connection c;
    private String url;
    private String username;
    private String password;

    private void Connect() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            c = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void registerInBackground() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Connect();
            }
        }).start();
    }

    private ConnectionDb() {

        // Exists only to defeat instantiation.
    }

    public static ConnectionDb getInstance() {
        if (instance == null) {
            instance = new ConnectionDb();
        }
        return instance;
    }


    public void khaledDb() {
        url = "jdbc:mysql://192.168.0.104:3306/c++?autoReconnect=true&useSSL=false";
        username = "khaled";
        password = "11121997K";
        registerInBackground();
    }

    public void TalaatDb() {
        url = "jdbc:mysql://192.168.1.10:3306/c++?autoReconnect=true&useSSL=false";
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
