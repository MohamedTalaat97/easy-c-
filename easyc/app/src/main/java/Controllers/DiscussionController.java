package Controllers;

import java.sql.ResultSet;
import java.util.ArrayList;

import Interfaces.OnTaskListeners;

/**
 * Created by KhALeD SaBrY on 29-Mar-18.
 */

public class DiscussionController extends Controller {


    public void getQuestionsOrderByName(boolean isMyQuestions, boolean isAnswered, boolean isAsec, String limitNumber, final OnTaskListeners.IdAndList listener)
    {
        databaseAdapter().selectCommentIdTitleOrderByTitle(dataModel().getUserId(), isMyQuestions, isAnswered, isAsec, limitNumber, new OnTaskListeners.Result() {
            @Override
            public void onSuccess(ResultSet data) {
                if(checkIfFound(data))
                {
                    ArrayList<Integer> ids = (ArrayList<Integer>)(Object) resultToArray(data,1);
                    ArrayList<Object> nameOfTheQuestion = resultToArray(data,2);
                    listener.onSuccess(ids,nameOfTheQuestion);
                }
                else
                    listener.onSuccess(new ArrayList<Integer>(),new ArrayList<Object>());
            }
        });
    }

    public void getQuestionsOrderByDate(boolean isMyQuestions, boolean isAnswered, boolean isAsec, String limitNumber, final OnTaskListeners.IdAndList listener)
    {
        databaseAdapter().selectCommentIdTitleOrderByDate(dataModel().getUserId(), isMyQuestions, isAnswered, isAsec, limitNumber, new OnTaskListeners.Result() {
            @Override
            public void onSuccess(ResultSet data) {
                if(checkIfFound(data))
                {
                    ArrayList<Integer> ids = (ArrayList<Integer>)(Object) resultToArray(data,1);
                    ArrayList<Object> nameOfTheQuestion = resultToArray(data,2);
                    listener.onSuccess(ids,nameOfTheQuestion);
                }
                else
                    listener.onSuccess(new ArrayList<Integer>(),new ArrayList<Object>());

            }
        });
    }


    public  void insertQuestion(String title, String description, final OnTaskListeners.Bool listener)
    {
        databaseAdapter().insertComment(dataModel().getUserId(), title, description, new OnTaskListeners.Bool() {
            @Override
            public void onSuccess(Boolean result) {
                listener.onSuccess(result);
            }
        });
    }

    public  void searchTitleOrderByDate(String searchtitle,boolean isMyQuestions, boolean isAnswered, boolean isAsec, String limitNumber, final OnTaskListeners.IdAndList listener)
    {
        databaseAdapter().selectCommentIdTitleByTitleOrderByDate(
                dataModel().getUserId(), searchtitle, isMyQuestions, isAnswered, isAsec, limitNumber, new OnTaskListeners.Result() {
            @Override
            public void onSuccess(ResultSet data) {
                if(checkIfFound(data))
                {
                    ArrayList<Integer> ids = (ArrayList<Integer>)(Object) resultToArray(data,1);
                    ArrayList<Object> nameOfTheQuestion = resultToArray(data,2);
                    listener.onSuccess(ids,nameOfTheQuestion);
                }
                else
                    listener.onSuccess(new ArrayList<Integer>(),new ArrayList<Object>());
            }
        });
    }

    public  void searchTitleOrderByName(String searchtitle,boolean isMyQuestions, boolean isAnswered, boolean isAsec, String limitNumber, final OnTaskListeners.IdAndList listener)
    {
        databaseAdapter().selectCommentIdTitleByTitleOrderByTitle(dataModel().getUserId(), searchtitle, isMyQuestions, isAnswered, isAsec, limitNumber, new OnTaskListeners.Result() {
            @Override
            public void onSuccess(ResultSet data) {
                if(checkIfFound(data))
                {
                    ArrayList<Integer> ids = (ArrayList<Integer>)(Object) resultToArray(data,1);
                    ArrayList<Object> nameOfTheQuestion = resultToArray(data,2);
                    listener.onSuccess(ids,nameOfTheQuestion);
                }
                else
                    listener.onSuccess(new ArrayList<Integer>(),new ArrayList<Object>());
            }
        });
    }
}
