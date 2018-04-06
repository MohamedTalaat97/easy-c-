package com.example.android.easyc.Controllers;

import com.example.android.easyc.Interfaces.OnTaskListeners;

import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * Created by KhALeD SaBrY on 15-Mar-18.
 */

public class OpinionController extends Controller {

    //get unread opinions
   public void getUnReadOpinions(final OnTaskListeners.IdAndList listener)
    {
        databaseAdapter().selectOpinionTitleUnReaded(new OnTaskListeners.Result() {
            @Override
            public void onSuccess(ResultSet data) {
                if(checkIfFound(data)) {
                    ArrayList<Integer> ids = (ArrayList<Integer>) (Object) resultToArray(data, 1);
                    ArrayList<Object> title = resultToArray(data, 2);
                    listener.onSuccess(ids, title);
                }
            }
        });

    }

    //get unseen opinions
    public void getUnSeenOpinions(final OnTaskListeners.IdAndList listener)
    {
        databaseAdapter().selectOpinionTitleUnSeen(new OnTaskListeners.Result() {
            @Override
            public void onSuccess(ResultSet data) {
                if(checkIfFound(data)) {
                    ArrayList<Integer> ids = (ArrayList<Integer>) (Object) resultToArray(data, 1);
                    ArrayList<Object> title = resultToArray(data, 2);
                    listener.onSuccess(ids, title);
                }
            }
        });

    }

    //get favourite opinions
    public void getFavouriteOpinions(final OnTaskListeners.IdAndList listener)
    {
        databaseAdapter().selectOpinionTitleFavourite(new OnTaskListeners.Result() {
            @Override
            public void onSuccess(ResultSet data) {
                if(checkIfFound(data)) {
                    ArrayList<Integer> ids = (ArrayList<Integer>) (Object) resultToArray(data, 1);
                    ArrayList<Object> title = resultToArray(data, 2);
                    listener.onSuccess(ids, title);
                }
            }
        });
    }

    //get all opinions
    public void getAllOpinions(final OnTaskListeners.IdAndList listener)
    {
        databaseAdapter().selectOpinionTitle(new OnTaskListeners.Result() {
            @Override
            public void onSuccess(ResultSet data) {
                if(checkIfFound(data)) {
                    ArrayList<Integer> ids = (ArrayList<Integer>) (Object) resultToArray(data, 1);
                    ArrayList<Object> title = resultToArray(data, 2);
                    listener.onSuccess(ids, title);
                }
            }
        });

    }

    //return description for a specific selected item in opinions
    public void getDescription(int id, final OnTaskListeners.Word listener) {
        databaseAdapter().selectOpinionDescription(id, new OnTaskListeners.Result() {
            @Override
            public void onSuccess(ResultSet data) {
                listener.onSuccess((String) resultToValue(data));
            }
        });
    }

    //update a topic to make it Favourite
    public void updateFavourite(int id, boolean favourite, final OnTaskListeners.Bool listener) {
        databaseAdapter().updateOpinionFavourite(id, !favourite, new OnTaskListeners.Bool() {
            @Override
            public void onSuccess(Boolean result) {
                listener.onSuccess(result);
            }
        });
    }

    //check if the opinion is favourite or not
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
    public void updateRead(int id, boolean read, final OnTaskListeners.Bool listener) {
        databaseAdapter().updateOpinionReaded(id, read, new OnTaskListeners.Bool() {
            @Override
            public void onSuccess(Boolean result) {
                listener.onSuccess(result);
            }
        });
    }

    //insert an opinion in database
    public void putOpinion(String title, String description, final OnTaskListeners.Bool listener) {
        int user_id = userData().getUserId();
        databaseAdapter().insertOpinion(user_id, title, description, new OnTaskListeners.Bool() {
            @Override
            public void onSuccess(Boolean result) {
                listener.onSuccess(result);
            }
        });
    }

    //mark an opinion as seen
    public void makeItSeen(int id) {
        databaseAdapter().updateOpinionSeen(id, true, new OnTaskListeners.Bool() {
            @Override
            public void onSuccess(Boolean result) {

            }
        });
    }
}
