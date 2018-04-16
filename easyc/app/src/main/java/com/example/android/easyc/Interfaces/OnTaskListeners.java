package com.example.android.easyc.Interfaces;

import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * Created by KhALeD SaBrY on 07-Mar-18.
 */

public interface OnTaskListeners {

    //return boolean
    public interface Bool
    {
        void onSuccess(Boolean result);
    }

    //return resultdata
    public interface Result {
        void onSuccess(ResultSet data);
    }

    //return number integar
    public interface Number
    {
        void onSuccess(int result);
    }


    //return string
    public interface Word
    {
        void onSuccess(String result);
    }

    //return object arraylist
    public interface List
    {
        void onSuccess(ArrayList<Object> result);
    }

    //return integar arraylist and object arraylist
    public  interface IdAndList
    {
        void onSuccess(ArrayList<Integer> id, ArrayList<Object> result);
    }

    //return two object arraylist
    public  interface ListAndList
    {
        void onSuccess(ArrayList<Object> list1,ArrayList<Object> list2);
    }

    //return three object arraylist
    public  interface Map
    {
        void onSuccess(String key,ArrayList<Object> list);
    }





}
