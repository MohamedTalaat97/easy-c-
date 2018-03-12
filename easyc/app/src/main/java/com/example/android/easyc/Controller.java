package com.example.android.easyc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class Controller {
    private DatabaseAdapter databaseAdapter;
    private DatabaseLegacy databaseLegacy;

    public  Controller ()
    {
        databaseAdapter = new DatabaseAdapter();
        databaseLegacy = new DatabaseLegacy();
    }
    public DatabaseAdapter databaseAdapter()
    {
        return  databaseAdapter;
    }

    public  DatabaseLegacy databaseLegacy()
    {
        return  databaseLegacy;
    }



    //get the data from database in array
    public ArrayList<Object> resultToArray(ResultSet data,String Column_Name) {
        ArrayList<Object> list = new ArrayList<Object>();
        try {
            if (data == null)
                list = null;
            while (data.next()) {
                //Retrieve by column name

                list.add((Object)data.getObject(Column_Name));

            }
        }
        catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        }
        return list;
    }

    //get the data from database in array
    public ArrayList<Object> resultToArray(ResultSet data,int Column_Number) {
        ArrayList<Object> list = new ArrayList<Object>();
        try {
            if (data == null)
                list = null;
            while (data.next()) {
                //Retrieve by column number
                list.add((Object)data.getObject(Column_Number));
            }
        }
        catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        }
        return list;
    }

    //get the data from database in array
    public ArrayList<Object> resultToArray(ResultSet data) {
        ArrayList<Object> list = new ArrayList<Object>();
        try {
            if (data == null)
                list = null;
            while (data.next()) {
                //Retrieve by column number
                list.add((Object)data.getObject(1));
            }
        }
        catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        }
        return list;
    }


    //get one value from the data
    public Object getOneValue(ResultSet data,int Column_Number) {
        Object value = null;
        try {
            if (data == null)
                value = null;
            data.next();
            value = data.getObject(1);
        }
        catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        }
        return value;
    }


    //get one value from the data
    public Object getOneValue(ResultSet data,String Column_Name) {
        Object value = null;
        try {
            if (data == null)
                value = null;
            data.next();
            value = data.getObject(1);
        }
        catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        }
        return value;
    }


    //get one value from the data
    public Object getOneValue(ResultSet data) {
        Object value = null;
        try {
            if (data == null)
                value = null;
            data.next();
            value = data.getObject(1);
        }
        catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        }
        return value;
    }

    //check if the value found in database or not
    public  Boolean checkIfFound(ResultSet data)
    {
        if(data == null)
            return false;
        return true;
    }







}
