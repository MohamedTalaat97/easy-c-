package com.example.android.easyc.Interfaces;

import java.util.ArrayList;

/**
 * Created by KhALeD SaBrY on 13-Mar-18.
 */

//These interfaces are used in View-Controller Connection
public interface ViewListener {

    public interface Bool
    {
        void OnSuccess(boolean result);
    }

    public interface Number
    {
        void OnSuccess(int result);
    }


    public interface Word
    {
        void OnSuccess(String result);
    }

    public interface Array
    {
        void OnSuccess(ArrayList<Object> result);
    }
}
