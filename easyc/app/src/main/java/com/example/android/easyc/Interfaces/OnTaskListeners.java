package com.example.android.easyc.Interfaces;

import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * Created by KhALeD SaBrY on 07-Mar-18.
 */

public interface OnTaskListeners {

    public interface Bool
    {
        void onSuccess(Boolean result);
    }
    public interface Result {
        void onSuccess(ResultSet data);
    }



    public  interface IdAndList
    {
        void OnSuccess(ArrayList<Integer> id, ArrayList<Object> result);
    }

}
