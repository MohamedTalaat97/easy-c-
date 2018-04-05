package com.example.android.easyc.Controllers;

import android.content.Context;
import android.widget.Toast;

import com.example.android.easyc.Connections.ConnectionDb;
import com.example.android.easyc.Models.UserData;
import com.example.android.easyc.Models.DatabaseAdapter;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class Controller {

    private static DatabaseAdapter databaseAdapter = null;
    //static so the data don't be deleted every time the controller made by object
<<<<<<< HEAD
    private Data dataModel = null;
=======
   private static UserData userData = null;
>>>>>>> 34698dfb9ae71db7e915f42b06a798f3ffb30845

    public Controller() {
        if (databaseAdapter == null)
            databaseAdapter = new DatabaseAdapter();
<<<<<<< HEAD

            dataModel = Data.getInstance();
=======
        if (userData == null)
            userData = new UserData();

>>>>>>> 34698dfb9ae71db7e915f42b06a798f3ffb30845
    }

    protected DatabaseAdapter databaseAdapter() {
        return databaseAdapter;
    }

    protected UserData userData() {
        return userData;
    }


    //check if the app is connected to the internet
    public boolean checkConnection(Context context) {
        if (!ConnectionDb.getInstance().checkConnection())
            toast("check your internet connection", context);
        return ConnectionDb.getInstance().checkConnection();
    }


    //get the data from database in array
    protected ArrayList<Object> resultToArray(ResultSet data, String Column_Name) {
        ArrayList<Object> list = new ArrayList<Object>();
        try {
            if (data == null)
                list = null;
            while (data.next()) {
                //Retrieve by column name

                list.add((Object) data.getObject(Column_Name));

            }
            data.beforeFirst();
        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        }
        return list;
    }

    //get the data from database in array
    protected ArrayList<Object> resultToArray(ResultSet data, int Column_Number) {
        ArrayList<Object> list = new ArrayList<Object>();
        try {
            if (data == null)
                list = null;
            while (data.next()) {
                //Retrieve by column number
                list.add((Object) data.getObject(Column_Number));
            }
            data.beforeFirst();
        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        }
        return list;
    }

    //get the data from database in array
    protected ArrayList<Object> resultToArray(ResultSet data) {
        ArrayList<Object> list = new ArrayList<Object>();
        try {
            if (data == null)
                list = null;
            while (data.next()) {
                //Retrieve by column number
                list.add((Object) data.getObject(1));
            }
            data.beforeFirst();
        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        }
        return list;
    }


    //get one value from the data
    protected Object resultToValue(ResultSet data, int Column_Number) {
        Object value = null;
        try {
            if (data == null)
                value = null;
            else {
                data.next();
                value = data.getObject(Column_Number);
                data.beforeFirst();
            }
        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        }
        return value;
    }


    //get one value from the data
    protected Object resultToValue(ResultSet data, String Column_Name) {
        Object value = null;
        try {
            if (data == null)
                value = null;
            else {
                data.next();
                value = data.getObject(Column_Name);
                data.beforeFirst();
            }
        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        }
        return value;
    }


    //get one value from the data
    protected Object resultToValue(ResultSet data) {
        Object value = null;
        try {
            if (data == null)
                value = null;
            else {
                data.next();
                value = data.getObject(1);
                data.beforeFirst();
            }
        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        }
        return value;
    }

    //check if the value found in database or not
    protected Boolean checkIfFound(ResultSet data) {
        try {
            boolean found = data.next();
            data.beforeFirst();
            if (found)
                return true;
            else
                return false;

        } catch (SQLException e) {
            return false;
        }

    }

    //make a toast
    public void toast(String msg, Context w) {
        Toast.makeText(w.getApplicationContext(), msg, Toast.LENGTH_SHORT).show();

    }

}