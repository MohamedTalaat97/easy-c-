package com.example.android.easyc.Controllers;

import android.content.Context;
import android.view.View;

import com.example.android.easyc.Interfaces.OnTaskListeners;
import com.example.android.easyc.Views.*;

import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * Created by KhALeD SaBrY on 15-Mar-18.
 */

public class OpinionController extends Controller {


    //return all title of the opinions which are not read
    public void returnTitle(String kind, final OnTaskListeners.IdAndList listener) {
        if (kind.matches("UnSeen")) {
            databaseAdapter().selectOpinionTitleUnSeen(new OnTaskListeners.Result() {
                @Override
                public void onSuccess(ResultSet data) {
                    ArrayList<Integer> ids = (ArrayList<Integer>) (Object) resultToArray(data, 1);
                    ArrayList<Object> title = resultToArray(data, 2);
                    listener.onSuccess(ids, title);
                }
            });

        } else if (kind.matches("UnRead")) {
            databaseAdapter().selectOpinionTitleUnReaded(new OnTaskListeners.Result() {
                @Override
                public void onSuccess(ResultSet data) {
                    ArrayList<Integer> ids = (ArrayList<Integer>) (Object) resultToArray(data, 1);
                    ArrayList<Object> title = resultToArray(data, 2);
                    listener.onSuccess(ids, title);
                }
            });
        } else if (kind.matches("Favourite")) {

            databaseAdapter().selectOpinionTitleFavourite(new OnTaskListeners.Result() {
                @Override
                public void onSuccess(ResultSet data) {
                    ArrayList<Integer> ids = (ArrayList<Integer>) (Object) resultToArray(data, 1);
                    ArrayList<Object> title = resultToArray(data, 2);
                    listener.onSuccess(ids, title);
                }
            });
        } else {
            databaseAdapter().selectOpinionTitle(new OnTaskListeners.Result() {
                @Override
                public void onSuccess(ResultSet data) {
                    ArrayList<Integer> ids = (ArrayList<Integer>) (Object) resultToArray(data, 1);
                    ArrayList<Object> title = resultToArray(data, 2);
                    listener.onSuccess(ids, title);
                }
            });
        }

    }

    //return description for a specific selected item in opinions
    public void returnDescription(int id, final OnTaskListeners.Word listener) {
        databaseAdapter().selectOpinionDescription(id, new OnTaskListeners.Result() {
            @Override
            public void onSuccess(ResultSet data) {
                listener.onSuccess((String) resultToValue(data));
            }
        });
    }

    //update a topic to make it Favourite
    public void updateFavourite(int id, boolean favourite, final OnTaskListeners.Bool listener)

    {
        int favour;
        if (!favourite)
            favour = 1;
        else
            favour = 0;

        databaseAdapter().updateOpinionFavourite(id, !favourite, new OnTaskListeners.Bool() {
            @Override
            public void onSuccess(Boolean result) {
                listener.onSuccess(result);
            }
        });
    }

    public void checkFavourite(int id, final OnTaskListeners.Bool listener) {
        databaseAdapter().selectOpinionFavourite(id, new OnTaskListeners.Result() {
            @Override
            public void onSuccess(ResultSet data) {
                if ((boolean) resultToValue(data)) {
                    listener.onSuccess(true);
                } else
                    listener.onSuccess(false);
            }
        });
    }

    //mark a topic as readed
    public void updateRead(int id, boolean read, final OnTaskListeners.Bool listener)

    {
        databaseAdapter().updateOpinionReaded(id, read, new OnTaskListeners.Bool() {
            @Override
            public void onSuccess(Boolean result) {
                listener.onSuccess(result);
            }
        });
    }


    public void putOpinion(String title, String description, final OnTaskListeners.Bool listener) {
        int user_id = dataModel().getUserId();
        databaseAdapter().insertOpinion(user_id, title, description, new OnTaskListeners.Bool() {
            @Override
            public void onSuccess(Boolean result) {
                listener.onSuccess(result);
            }
        });
    }


    public void makeItSeen(int id) {
        databaseAdapter().updateOpinionSeen(id, true, new OnTaskListeners.Bool() {
            @Override
            public void onSuccess(Boolean result) {

            }
        });
    }
}
