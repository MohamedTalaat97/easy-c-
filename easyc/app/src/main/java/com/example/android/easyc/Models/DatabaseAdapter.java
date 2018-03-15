package com.example.android.easyc.Models;

import com.example.android.easyc.Interfaces.OnTaskListeners;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DatabaseAdapter {

    private DatabaseLegacy databaseLegacy = null;
    private String query;

    public DatabaseAdapter()
    {
        databaseLegacy =  new DatabaseLegacy();
    }

    public  DatabaseLegacy getDatabaseLegacy()
    {
        return  databaseLegacy;
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

    public void insertNewUser(String userName,String Pass,char Type,String age,String email ,OnTaskListeners.Bool listeners)
    {
        query = "insert into user(username,password,type,age,email) values('"+userName+"','"+Pass+"','"+Type+"',"+age+",'"+email+"')";
        databaseLegacy.iud(query,listeners);

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


    public  void selectOpinionTitle(OnTaskListeners.Result listener)
    {
        //still waiting to make opinion table in database
        query = "select id,title from opinion where read = false";
        databaseLegacy.Select(query,listener);
    }

    public  void selectOpinionDescription(int id, OnTaskListeners.Result listener)
    {
        //still waiting to make opinion table in database
        query = "select description from opinion where id = "+id;
        databaseLegacy.Select(query,listener);
    }
    public void updateOpinionRead(int id ,char read,OnTaskListeners.Bool listener)
    {
        query = "update opinion set read = '"+read+"' where id = "+ id;
        databaseLegacy.iud(query,listener);
    }

    public void updateOpinionFavourite(int id ,char favourite,OnTaskListeners.Bool listener)
    {
        query = "update opinion set favourite = '"+favourite+"' where id = "+ id;
        databaseLegacy.iud(query,listener);
    }


}
