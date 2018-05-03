package com.example.android.easyc.Controllers;

import com.example.android.easyc.Interfaces.OnTaskListeners;

import java.sql.ResultSet;

/**
 * Created by KhALeD SaBrY on 19-Apr-18.
 */

public class ReportController extends Controller {
    public static String REPORTID = "REPORTID";
    public static String COMMENTID = "COMMENTID";
    public static String REPLYID = "REPLYID";
    public static String DISCRIPTION = "Discription";
    public static String Fininshed = "Fininshed";


    public void putReport(Integer questionId, Integer replyId, String discription, String type, final OnTaskListeners.Bool listener) {
        databaseAdapter().insertReport(userData().getUserId(), questionId, replyId, discription, type, new OnTaskListeners.Bool() {
            @Override
            public void onSuccess(Boolean result) {
                listener.onSuccess(result);
            }
        });
    }

    public void getReport(Integer reportId, final OnTaskListeners.Map listener) {
        databaseAdapter().selectReportCommentidReplyidDiscription(reportId, new OnTaskListeners.Result() {
            @Override
            public void onSuccess(ResultSet data) {
                if (!checkIfFound(data))
                    return;
                listener.onSuccess(COMMENTID, resultToArray(data, 1));
                listener.onSuccess(REPLYID, resultToArray(data, 2));
                listener.onSuccess(DISCRIPTION, resultToArray(data, 3));
                listener.onSuccess(Fininshed, resultToArray(data));
            }
        });
    }


    public void suspendUser(Integer replyId, final OnTaskListeners.Bool listener) {
        databaseAdapter().updateUserSuspendedByReplyid(replyId, true, new OnTaskListeners.Bool() {
            @Override
            public void onSuccess(Boolean result) {
                listener.onSuccess(result);
            }
        });

    }

    public void deleteReply(Integer replyId, final OnTaskListeners.Bool listener) {
        databaseAdapter().deleteReply(replyId, new OnTaskListeners.Bool() {
            @Override
            public void onSuccess(Boolean result) {
                listener.onSuccess(result);
            }
        });
    }

    public void deleteComment(Integer commentId, final OnTaskListeners.Bool listener) {
        databaseAdapter().deleteComment(commentId, new OnTaskListeners.Bool() {
            @Override
            public void onSuccess(Boolean result) {
                listener.onSuccess(result);
            }
        });

    }

    public void changeBestAnswer(Integer replyId, final OnTaskListeners.Bool listener) {
        databaseAdapter().updateReplyBestAnswer(replyId, false, new OnTaskListeners.Bool() {
            @Override
            public void onSuccess(Boolean result) {
                if(result)
                listener.onSuccess(result);
            }
        });

    }


    public void solveReport(int reportId, final OnTaskListeners.Bool listener)
    {
        databaseAdapter().updateReportSolved(reportId, true, new OnTaskListeners.Bool() {
            @Override
            public void onSuccess(Boolean result) {
                listener.onSuccess(result);
            }
        });
    }

    public void getquestion(Integer questionId, final OnTaskListeners.Word listener) {
        databaseAdapter().selectCommentTitleDescription(questionId, new OnTaskListeners.Result() {
            @Override
            public void onSuccess(ResultSet data) {
                if (!checkIfFound(data))
                    return;
                String title = resultToValue(data, 1).toString();
                String discription = resultToValue(data, 2).toString();
                listener.onSuccess(title + " \n " + discription);
            }
        });
    }

    public void getAnswer(Integer replyId, final OnTaskListeners.Word listener) {
        databaseAdapter().selectReplyContent(replyId, new OnTaskListeners.Result() {
            @Override
            public void onSuccess(ResultSet data) {
                if (!checkIfFound(data))
                    return;
                listener.onSuccess(resultToValue(data).toString());
            }
        });
    }


    //get all the reports under specific kind
    public  void getReports(String kind , final OnTaskListeners.Map listener)
    {
        databaseAdapter().selectReportIdDiscriptionByType(kind, new OnTaskListeners.Result() {
            @Override
            public void onSuccess(ResultSet data) {
                if(!checkIfFound(data))
                    return;;
                    listener.onSuccess(ReportController.REPORTID,resultToArray(data,1));
                    listener.onSuccess(ReportController.DISCRIPTION,resultToArray(data,2));
            }
        });
    }
}

