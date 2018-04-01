package Interfaces;

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

    public interface Number
    {
        void onSuccess(int result);
    }


    public interface Word
    {
        void onSuccess(String result);
    }

    public interface List
    {
        void onSuccess(ArrayList<Object> result);
    }

    public  interface IdAndList
    {
        void onSuccess(ArrayList<Integer> id, ArrayList<Object> result);
    }

    public  interface ListAndList
    {
        void onSuccess(ArrayList<Object> list1,ArrayList<Object> list2);
    }

    public  interface ThreeList
    {
        void onSuccess(ArrayList<Object> list1,ArrayList<Object> list2,ArrayList<Object> list3);
    }


}
