package com.example.android.easyc.Controllers;

import android.view.View;

import com.example.android.easyc.Interfaces.OnTaskListeners;
import com.example.android.easyc.Interfaces.ViewListener;

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
                listener.OnSuccess(ids,title);
            }
        });
    }

//return description for a specific selected item in opinions
    public  void returnDescription(int  id, final ViewListener.Word listener)
    {
        databaseAdapter().selectOpinionDescription(id, new OnTaskListeners.Result() {
            @Override
            public void onSuccess(ResultSet data) {
                listener.OnSuccess((String) getOneValue(data));
            }
        });
    }

//update a topic to make it Favourite
    public  void updateFavourite(int id,boolean favourite, final OnTaskListeners.Bool listener)

    {
        char favour;
        if(favourite)
            favour = 'T';
        else
            favour = 'F';

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
        char r;
        if(read)
            r = 'T';
        else
            r = 'F';

        databaseAdapter().updateOpinionRead(id, r, new OnTaskListeners.Bool() {
            @Override
            public void onSuccess(Boolean result) {
                listener.onSuccess(result);
            }
        });
    }



}