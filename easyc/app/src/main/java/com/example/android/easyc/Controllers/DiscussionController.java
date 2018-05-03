package com.example.android.easyc.Controllers;

import com.example.android.easyc.Interfaces.OnTaskListeners;

import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * Created by KhALeD SaBrY on 29-Mar-18.
 */

public class DiscussionController extends Controller {

    public static String REPLYID = "REPLTID";
    public static String USERNAME = "USERNAME";
    public static String USERID = "USERID";
    public static String CONTENT = "CONTENT";
    public static String NOTFOUND = "NOTFOUND";
    public static String FINISHED = "FINISHED";
    public static String TITLE = "TITLE";
    public static String BEST_ANSWER = "BEST_ANSWER";


    //get back the questions with order by name
    public void getQuestionsOrderByName(boolean isMyQuestions, boolean isAnswered, boolean isAsec, String limitNumber, final OnTaskListeners.IdAndList listener) {
        databaseAdapter().selectCommentIdTitleOrderByTitle(userData().getUserId(), isMyQuestions, isAnswered, isAsec, limitNumber, new OnTaskListeners.Result() {
            @Override
            public void onSuccess(ResultSet data) {
                if (checkIfFound(data)) {
                    ArrayList<Integer> ids = (ArrayList<Integer>) (Object) resultToArray(data, 1);
                    ArrayList<Object> nameOfTheQuestion = resultToArray(data, 2);
                    listener.onSuccess(ids, nameOfTheQuestion);
                } else
                    listener.onSuccess(new ArrayList<Integer>(), new ArrayList<Object>());
            }
        });
    }

    //get back the questions with order by date
    public void getQuestionsOrderByDate(boolean isMyQuestions, boolean isAnswered, boolean isAsec, String limitNumber, final OnTaskListeners.IdAndList listener) {
        databaseAdapter().selectCommentIdTitleOrderByDate(userData().getUserId(), isMyQuestions, isAnswered, isAsec, limitNumber, new OnTaskListeners.Result() {
            @Override
            public void onSuccess(ResultSet data) {
                if (checkIfFound(data)) {
                    ArrayList<Integer> ids = (ArrayList<Integer>) (Object) resultToArray(data, 1);
                    ArrayList<Object> nameOfTheQuestion = resultToArray(data, 2);
                    listener.onSuccess(ids, nameOfTheQuestion);
                } else
                    listener.onSuccess(new ArrayList<Integer>(), new ArrayList<Object>());

            }
        });
    }


    //put question in discussion room
    public void insertQuestion(String title, String description, final OnTaskListeners.Bool listener) {
        databaseAdapter().insertComment(userData().getUserId(), title, description, new OnTaskListeners.Bool() {
            @Override
            public void onSuccess(Boolean result) {
                listener.onSuccess(result);
            }
        });
    }

    //search about a specific topic in questions and order them by date
    public void searchTitleOrderByDate(String searchTitle, boolean isMyQuestions, boolean isAnswered, boolean isAsec, String limitNumber, final OnTaskListeners.IdAndList listener) {
        databaseAdapter().selectCommentIdTitleByTitleOrderByDate(
                userData().getUserId(), searchTitle, isMyQuestions, isAnswered, isAsec, limitNumber, new OnTaskListeners.Result() {
                    @Override
                    public void onSuccess(ResultSet data) {
                        if (checkIfFound(data)) {
                            ArrayList<Integer> ids = (ArrayList<Integer>) (Object) resultToArray(data, 1);
                            ArrayList<Object> nameOfTheQuestion = resultToArray(data, 2);
                            listener.onSuccess(ids, nameOfTheQuestion);
                        } else
                            listener.onSuccess(new ArrayList<Integer>(), new ArrayList<Object>());
                    }
                });
    }

    //search about a specific topic in questions and order them by name
    public void searchTitleOrderByName(String searchTitle, boolean isMyQuestions, boolean isAnswered, boolean isAsec, String limitNumber, final OnTaskListeners.IdAndList listener) {
        databaseAdapter().selectCommentIdTitleByTitleOrderByTitle(userData().getUserId(), searchTitle, isMyQuestions, isAnswered, isAsec, limitNumber, new OnTaskListeners.Result() {
            @Override
            public void onSuccess(ResultSet data) {
                if (checkIfFound(data)) {
                    ArrayList<Integer> ids = (ArrayList<Integer>) (Object) resultToArray(data, 1);
                    ArrayList<Object> nameOfTheQuestion = resultToArray(data, 2);
                    listener.onSuccess(ids, nameOfTheQuestion);
                } else
                    listener.onSuccess(new ArrayList<Integer>(), new ArrayList<Object>());
            }
        });
    }


    //return the replies for a specific question identified by id
    public void getReplies(final int questionId, final OnTaskListeners.Map listener) {
        databaseAdapter().selectReplyIdUserNameUserIdContentBest_answer(questionId, true, new OnTaskListeners.Result() {
                    @Override
                    public void onSuccess(ResultSet data) {
                        if (checkIfFound(data)) {
                            listener.onSuccess(REPLYID, resultToArray(data, 1));
                            listener.onSuccess(USERNAME, resultToArray(data, 2));
                            listener.onSuccess(USERID, resultToArray(data, 3));
                            listener.onSuccess(CONTENT, resultToArray(data, 4));
                            listener.onSuccess(BEST_ANSWER, resultToArray(data, 5));

                        }
                        //      else
                        //     listener.onSuccess(NOTFOUND,new ArrayList<Object>());


                        databaseAdapter().selectReplyIdUserNameUserIdContentBest_answer(questionId, false, new OnTaskListeners.Result() {
                            @Override
                            public void onSuccess(ResultSet data) {

                                if (checkIfFound(data)) {
                                    listener.onSuccess(REPLYID, resultToArray(data, 1));
                                    listener.onSuccess(USERNAME, resultToArray(data, 2));
                                    listener.onSuccess(USERID, resultToArray(data, 3));
                                    listener.onSuccess(CONTENT, resultToArray(data, 4));
                                    listener.onSuccess(BEST_ANSWER, resultToArray(data, 5));


                                }
                                //   else
                                //  listener.onSuccess(NOTFOUND,new ArrayList<Object>());
                                listener.onSuccess(FINISHED, new ArrayList<Object>());

                            }
                        });
                    }
                }
        );
    }


    //get question details by id
    public void getQuestion(int questionId, final OnTaskListeners.Map listener) {
        databaseAdapter().selectCommentUserIdUsernameTitleDescription(questionId, new OnTaskListeners.Result() {
            @Override
            public void onSuccess(ResultSet data) {
                if (checkIfFound(data)) {
                    listener.onSuccess(USERID, resultToArray(data, 1));
                    listener.onSuccess(USERNAME, resultToArray(data, 2));
                    listener.onSuccess(TITLE, resultToArray(data, 3));
                    listener.onSuccess(CONTENT, resultToArray(data, 4));
                    listener.onSuccess(FINISHED, new ArrayList<Object>());

                } else
                    listener.onSuccess(NOTFOUND, new ArrayList<Object>());

            }
        });

    }

    //update reply to set it as best answer or
    public void updateReplyBestAnswer(final int replyid, final boolean isBestAnswer, final OnTaskListeners.Bool listener) {
        databaseAdapter().updateReplyBestAnswerByAll(false, new OnTaskListeners.Bool() {
            @Override
            public void onSuccess(Boolean result) {
                if (result)
                    databaseAdapter().updateReplyBestAnswer(replyid, isBestAnswer, new OnTaskListeners.Bool() {
                        @Override
                        public void onSuccess(Boolean result) {
                            listener.onSuccess(result);
                        }
                    });
            }
        });
    }


    //insert reply in the database on a question
    public void putReply(int questionId, String reply, final OnTaskListeners.Bool listener) {
        databaseAdapter().insertReply(userData().getUserId(), questionId, reply, new OnTaskListeners.Bool() {
            @Override
            public void onSuccess(Boolean result) {
                listener.onSuccess(result);
            }
        });
    }
}
