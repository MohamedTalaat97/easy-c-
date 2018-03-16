package com.example.android.easyc.Controllers;

import android.view.View;

import com.example.android.easyc.Interfaces.OnTaskListeners;

import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * Created by KhALeD SaBrY on 15-Mar-18.
 */

public class OpinionController extends Controller {

   //return all title of the opinions which are not read
   public void returnTitle(final OnTaskListeners.IdAndList listener)
    {
        databaseAdapter().selectOpinionTitle(new OnTaskListeners.Result() {
            @Override
            public void onSuccess(ResultSet data) {
                ArrayList<Integer> ids = (ArrayList<Integer>)(Object) resultToArray(data,1);
                ArrayList<Object>  title = resultToArray(data,2);
                listener.onSuccess(ids,title);
            }
        });
    }

//return description for a specific selected item in opinions
    public  void returnDescription(int  id, final OnTaskListeners.Word listener)
    {
        databaseAdapter().selectOpinionDescription(id, new OnTaskListeners.Result() {
            @Override
            public void onSuccess(ResultSet data) {
                listener.onSuccess((String) getOneValue(data));
            }
        });
    }

//update a topic to make it Favourite
    public  void updateFavourite(int id,boolean favourite, final OnTaskListeners.Bool listener)

    {
        int favour;
        if(favourite)
            favour = 1;
        else
            favour = 0;

        databaseAdapter().updateOpinionFavourite(id, favour, new OnTaskListeners.Bool() {
            @Override
            public void onSuccess(Boolean result) {
                listener.onSuccess(result);
            }
        });
    }

    //mark a topic as readed
    public  void updateRead(int id,boolean read, final OnTaskListeners.Bool listener)

    {
        int r;
        if(read)
            r = 1;
        else
            r = 0;

        databaseAdapter().updateOpinionReaded(id, r, new OnTaskListeners.Bool() {
            @Override
            public void onSuccess(Boolean result) {
                listener.onSuccess(result);
            }
        });
    }


    public  void putOpinion(String title, String description, final OnTaskListeners.Bool listener)
    {
        int user_id = dataModel().getUserId();
        databaseAdapter().insertOpinion(user_id, title, description, new OnTaskListeners.Bool() {
            @Override
            public void onSuccess(Boolean result) {
                listener.onSuccess(result);
            }
        });
    }



}
