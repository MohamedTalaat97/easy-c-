package com.example.android.easyc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DatabaseAdapter {
    public DatabaseLegacy db;
    private final Map<String,ArrayList<String>> mappings = new HashMap<String, ArrayList<String>>();

    public ArrayList<String> getValues(String key)
    {
        return mappings.get(key);
    }

    public Boolean putValue(String key, String value)
    {
        ArrayList<String> target = mappings.get(key);

        if(target == null)
        {
            target = new ArrayList<>();
            mappings.put(key,target);
        }

        return target.add(value);
    }

    public DatabaseAdapter()
    {
        db =  new DatabaseLegacy();
    }

  /*  public int insert(String Table,String Value)
    {
        String query = "insert into "+Table+" values("+Value+")";
        return db.iud(query);
    }

    public  int update(String Table,String Values)
    {
        String query = "update "+Table + " set "+Values;
        return  db.iud(query);
    }

    public  int update(String Table,String Values,String Condition)
    {
        String query = "update "+Table + " set "+Values + "where " + Condition;
        return  db.iud(query);
    }

    public  int delete(String Table,String Condition)
    {
        String query = "delete from "+Table + " where  "+Condition;
        return  db.iud(query);
    }

*/
    public  ArrayList<Object> ResultToSelect(ResultSet data) {
        ArrayList<Object> list = new ArrayList<Object>();

        try {
            if (data == null)
                list = null;
            while (data.next()) {
                //Retrieve by column name

                list.add((Object)data.getObject(1));

            }
        }
        catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        }
        return list;

    }



    public void selectEmployeesId(OnTaskListeners.Result listeners)
    {
        String query = "select * from employee";
        db.Select(query,listeners);

    }

    public void selectEmployeesNames(OnTaskListeners.Result listeners)
    {
        String query = "select name from employee";
        db.Select(query,listeners);

    }


    public void insertEmployeeName(String Name,OnTaskListeners.Bool listener)
    {
        String query = "insert into employee (Name) values('"+Name+"')";
        db.iud(query,listener);

    }

}
