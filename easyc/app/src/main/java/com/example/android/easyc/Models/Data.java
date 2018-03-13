package com.example.android.easyc.Models;

/**
 * Created by KhALeD SaBrY on 13-Mar-18.
 */

// this is the class that we will save all the data we need while he is using the program
public class Data {

    private static int userId;


    public void setUserId(int userid)
    {
        this.userId = userid;
    }

    public int getUserId()
    {
        return userId;
    }
}
