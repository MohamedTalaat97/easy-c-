package Controllers;

import java.sql.ResultSet;

import Interfaces.OnTaskListeners;

/**
 * Created by medotalaat on 15-Mar-18.
 */



public class CourseController extends Controller {

    //get categories title
    public void getCategories(final OnTaskListeners.List listener) {
        databaseAdapter().selectCategories(new OnTaskListeners.Result() {
            @Override
            public void onSuccess(ResultSet data) {
                listener.onSuccess(resultToArray(data));
            }
        });
    }

    //get specific category id
    public void getCatagoryId(String name, final OnTaskListeners.Number listener) {
        databaseAdapter().selectCatagoryIdByName(name, new OnTaskListeners.Result() {
            @Override
            public void onSuccess(ResultSet data) {
                listener.onSuccess((int) resultToValue(data));
            }
        });
    }

    //get TopicId
    public void getTopicId(String name, final OnTaskListeners.Number listener) {
        databaseAdapter().selectTopicIdByName(name, new OnTaskListeners.Result() {
            @Override
            public void onSuccess(ResultSet data) {
                listener.onSuccess((int) resultToValue(data));
            }
        });
    }

    //get the topics titles
    public void getTopics(int catId, final OnTaskListeners.List listener) {
        databaseAdapter().selectTopics(catId, new OnTaskListeners.Result() {
            @Override
            public void onSuccess(ResultSet data) {
                listener.onSuccess(resultToArray(data));
            }
        });


    }

    //get the code for specific title
    public void getCode(int topicId, final OnTaskListeners.Word listener) {
        databaseAdapter().selectCode(topicId, new OnTaskListeners.Result() {
            @Override
            public void onSuccess(ResultSet data) {
                listener.onSuccess((String) resultToValue(data));
            }
        });
    }

    //get the description for specific title
    public void getDescription(int topicId, final OnTaskListeners.Word listener) {
        databaseAdapter().selectDescription(topicId, new OnTaskListeners.Result() {
            @Override
            public void onSuccess(ResultSet data) {
                listener.onSuccess((String) resultToValue(data));
            }
        });
    }

    //get the output for specific title
    public void getOutput(int topicId, final OnTaskListeners.Word listener) {
        databaseAdapter().selectOutput(topicId, new OnTaskListeners.Result() {
            @Override
            public void onSuccess(ResultSet data) {
                listener.onSuccess((String) resultToValue(data));
            }
        });
    }
}