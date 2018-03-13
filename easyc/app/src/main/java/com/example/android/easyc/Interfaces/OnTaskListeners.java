package com.example.android.easyc.Interfaces;

import java.sql.ResultSet;

/**
 * Created by KhALeD SaBrY on 07-Mar-18.
 */

public interface OnTaskListeners {

    public interface Bool
    {
        void onSuccess(Boolean data);
    }
    public interface Result {
        void onSuccess(ResultSet data);
    }

  // void onSuccessUpdated(Boolean data);
}
