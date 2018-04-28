package com.example.android.easyc.Models;

import android.os.AsyncTask;

import com.example.android.easyc.Connections.ConnectionDb;
import com.example.android.easyc.Interfaces.OnTaskListeners;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by KhALeD SaBrY on 03-Mar-18.
 */

public class DatabaseLegacy {

    private ConnectionDb conn = ConnectionDb.getInstance();

    //iud stand for : insert,update and delete
    public void iud(final  String query, final OnTaskListeners.Bool listener) {



        new AsyncTask<Void, Void, Boolean>() {
            public Integer updated = null;

            @Override
            protected Boolean doInBackground(Void... params) {
                try {
                    Statement stmt;
                    if(!conn.checkConnection()) {
                        conn.reconnect();
                        if(!conn.checkConnection())
                            return false;
                    }
                    stmt = conn.c.createStatement();
                    updated = stmt.executeUpdate(query);
                 /*   if(updated == 1)
                        return true;
                    else
                        return false;*/
                 return true;
                } catch (SQLException e) {
                    e.printStackTrace();
                    conn.reconnect();
                }
                catch (Exception e)
                {
                }
                return false;
            }

            @Override
            protected void onPostExecute(Boolean data) {
                if(!conn.checkConnection())
                    return;
                listener.onSuccess(data);
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }
        }.execute();
    }

    public  void select(final  String query,final OnTaskListeners.Result listeners)
    {
        new AsyncTask<Void, Void, ResultSet>() {
            public ResultSet RS;

            @Override
            protected ResultSet doInBackground(Void... params) {
                try {
                    if(!conn.checkConnection()) {
                        conn.reconnect();
                        if(!conn.checkConnection())
                            return null;
                    }
                        Statement stmt;
                        stmt = conn.c.createStatement();
                        RS = stmt.executeQuery(query);
                } catch (SQLException e) {
                    e.printStackTrace();
                    conn.reconnect();
                }
                catch (Exception e)
                {

                }
                return RS;
            }

            @Override
            protected void onPostExecute(ResultSet data) {
                listeners.onSuccess(RS);
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }
        }.execute();
    }

}



