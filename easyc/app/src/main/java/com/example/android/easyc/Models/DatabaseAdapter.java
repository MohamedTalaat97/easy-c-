package com.example.android.easyc.Models;

import com.example.android.easyc.Interfaces.OnTaskListeners;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DatabaseAdapter {

    private DatabaseLegacy databaseLegacy;
    private String query;

    public DatabaseAdapter()
    {
        databaseLegacy =  new DatabaseLegacy();
    }

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


    public void selectEmployeeId(OnTaskListeners.Result listeners)
    {
         query = "select * from employee";
        databaseLegacy.Select(query,listeners);

    }

    public void selectEmployeeName(OnTaskListeners.Result listeners)
    {
         query = "select name from employee";
        databaseLegacy.Select(query,listeners);

    }


    public void insertEmployeeName(String Name,OnTaskListeners.Bool listener)
    {
        query = "insert into employee (Name) values('"+Name+"')";
        databaseLegacy.iud(query,listener);

    }

    public  void selectUserName(String username,String password,OnTaskListeners.Result listener)
    {
        query = "select id from Log where username = '" + username +"' and password = '"+ password +"'";
        databaseLegacy.Select(query,listener);
    }

    public  void selectEmail(String Email,String password,OnTaskListeners.Result listener)
    {
        query = "select id from Log where Email = '" + Email +"' and password = '"+ password +"'";
        databaseLegacy.Select(query,listener);
    }

}
