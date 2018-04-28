package com.example.android.easyc.Models;

import com.example.android.easyc.Interfaces.OnTaskListeners;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


//every function have the same way of identification
//first part is the method : select, insert,delete or update
//second par is the name of table or tables
//third part is the names of the attributes we want to get it : only with select
//fourth part is the condition : it's optional and only exists if there is two functions do the same thing but different conditions

public class DatabaseAdapter {

    private static DatabaseLegacy databaseLegacy = null;
    private String query;

    public DatabaseAdapter() {

        if (databaseLegacy == null)
            databaseLegacy = new DatabaseLegacy();
    }

    public DatabaseLegacy getDatabaseLegacy() {
        return databaseLegacy;
    }


    public void selectCategories(OnTaskListeners.Result listeners) {
        query = "select title from category";
        databaseLegacy.select(query, listeners);

    }

    public void selectCategoriesIds(OnTaskListeners.Result listeners) {
        query = "select id from category";
        databaseLegacy.select(query, listeners);

    }

    public void selectCatagoryIdByName(String name, OnTaskListeners.Result listeners) {
        query = "select id from category where title = '" + name + "'";
        databaseLegacy.select(query, listeners);

    }

    public void selectTopicIdByName(String name, OnTaskListeners.Result listeners) {
        query = "select id from topic where title = '" + name + "'";
        databaseLegacy.select(query, listeners);

    }

    public void selectTopics(int cat_id, OnTaskListeners.Result listeners) {
        query = "select title from topic where cat_id = '" + cat_id + "'";
        databaseLegacy.select(query, listeners);

    }

    public void selectQuestions(int cat_id, OnTaskListeners.Result listeners) {
        query = "select question from question where cat_id = '" + cat_id + "'";
        databaseLegacy.select(query, listeners);

    }

    public void selectQuizIdQuestionAnswer(int cat_id, OnTaskListeners.Result listeners) {
        query = "select id,question,answer from question where cat_id = " + cat_id;
        databaseLegacy.select(query, listeners);

    }

    public void selectQuestionsIds(int cat_id, OnTaskListeners.Result listeners) {
        query = "select id from question where cat_id = '" + cat_id + "'";
        databaseLegacy.select(query, listeners);
    }

    public void selectcategoryIds(OnTaskListeners.Result listeners) {
        query = "select id from category";
        databaseLegacy.select(query, listeners);
    }

    public void selectAnswers(int cat_id, OnTaskListeners.Result listeners) {
        query = "select answer from question where cat_id = '" + cat_id + "'";
        databaseLegacy.select(query, listeners);

    }

    public void selectCode(int topic_id, OnTaskListeners.Result listeners) {
        query = "select code from topic where id = '" + topic_id + "'";
        databaseLegacy.select(query, listeners);

    }

    public void selectDescription(int topic_id, OnTaskListeners.Result listeners) {
        query = "select description from topic where id = '" + topic_id + "'";
        databaseLegacy.select(query, listeners);

    }

    public void selectOutput(int topic_id, OnTaskListeners.Result listeners) {
        query = "select output from topic where id = '" + topic_id + "'";
        databaseLegacy.select(query, listeners);

    }

    public void insertUser(String userName, String Pass, char Type, String age, String email, boolean suspended, String requestText, OnTaskListeners.Bool listeners) {
        query = "insert into user(username,password,type,age,email,suspended,request)" +
                " values('" + userName + "','" + Pass + "','" + Type + "'," + age + ",'" + email + "'," + suspended + ",'" + requestText + "')";
        databaseLegacy.iud(query, listeners);

    }

    public void insertQuestion(Integer catId, Integer userId, String Question, Integer Answer, OnTaskListeners.Bool listeners) {
        query = "insert into " +
                " values('" + catId + "','" + catId + "','" + userId + "'," + Question + ",'" + Answer + "')";
        databaseLegacy.iud(query, listeners);

    }

    public void selectOpinionTitle(OnTaskListeners.Result listener) {
        query = "select id,title from opinion";
        databaseLegacy.select(query, listener);
    }

    public void selectOpinionTitleUnSeen(OnTaskListeners.Result listener) {
        query = "select id,title from opinion where seen = false";
        databaseLegacy.select(query, listener);
    }

    public void selectOpinionTitleUnReaded(OnTaskListeners.Result listener) {
        query = "select id,title from opinion where readed = false";
        databaseLegacy.select(query, listener);
    }

    public void selectOpinionFavourite(int id, OnTaskListeners.Result listener) {
        query = "select favourite from opinion where id = " + id;
        databaseLegacy.select(query, listener);
    }

    public void selectOpinionTitleFavourite(OnTaskListeners.Result listener) {
        query = "select id,title from opinion where favourite = true";
        databaseLegacy.select(query, listener);
    }


    public void selectOpinionDescription(int id, OnTaskListeners.Result listener) {
        query = "select description from opinion where id = " + id;
        databaseLegacy.select(query, listener);
    }

    public void updateOpinionReaded(int id, boolean read, OnTaskListeners.Bool listener) {
        query = "update opinion set readed = " + read + " where id = " + id;
        databaseLegacy.iud(query, listener);
    }

    public void updateOpinionFavourite(int id, Boolean favourite, OnTaskListeners.Bool listener) {
        query = "update opinion set favourite = " + favourite + " where id = " + id;
        databaseLegacy.iud(query, listener);
    }

    public void updateOpinionSeen(int id, boolean isSeen, OnTaskListeners.Bool listener) {
        query = "update opinion set seen = " + isSeen + " where id = " + id;
        databaseLegacy.iud(query, listener);
    }

    public void insertOpinion(int user_id, String title, String description, OnTaskListeners.Bool listener) {
        query = "insert into Opinion (user_id,title,description,readed,seen,favourite) values(" + user_id + ",'" + title + "','" + description + "',false,false,false)";
        databaseLegacy.iud(query, listener);
    }


    public void selectUserUsernamePassword(String email, OnTaskListeners.Result listener) {
        query = "select username,password from user where email = '" + email + "'";
        databaseLegacy.select(query, listener);
    }

    public void selectUserUsername(String userName, OnTaskListeners.Result listener) {
        query = "select username from user where userName = '" + userName + "'";
        databaseLegacy.select(query, listener);
    }


    public void selectUserUsername(int userID, OnTaskListeners.Result listener) {
        query = "select username from user where id = '" + userID + "'";
        databaseLegacy.select(query, listener);
    }

    public void selectUserLevel(int userID, OnTaskListeners.Result listener) {
        query = "select level from user where id = '" + userID + "'";
        databaseLegacy.select(query, listener);
    }


    public void selectUserEmail(String email, OnTaskListeners.Result listener) {
        query = "select email from user where email = '" + email + "'";
        databaseLegacy.select(query, listener);
    }

    public void selectUserIdTypeSuspendedRequest(String name, String password, OnTaskListeners.Result listener) {
        query = "select id,type,suspended,request from user where (userName = '" + name + "' OR email = '" + name + "' )  and password = '" + password + "'";
        databaseLegacy.select(query, listener);
    }


    public void selectUserIdUserName(OnTaskListeners.Result listener) {
        query = "select id,username from user where suspended = true and type = 'I'  and request is not null";
        databaseLegacy.select(query, listener);
    }

    public void selectUserRequest(Integer id, OnTaskListeners.Result listener) {
        query = "select request from user where id = " + id;
        databaseLegacy.select(query, listener);
    }

    public void updateUserSuspendedRequest(Integer id, boolean suspended, OnTaskListeners.Bool listener) {
        query = "update User set suspended = " + suspended + ", request = null  where id = " + id;
        databaseLegacy.iud(query, listener);
    }


    public void selectCommentIdTitleOrderByTitle(int user_id, boolean isMyQuestions, boolean isAnswered, boolean isAsec, String limitNumber, OnTaskListeners.Result listener) {
        query = "select id,title from comment where user_id ";
        if (isMyQuestions)
            query += "= ";
        else
            query += "!= ";
        query += user_id + " and solved = " + isAnswered + " order by title ";
        if (isAsec)
            query += "Asc";
        else
            query += "Desc";

        query += " limit " + limitNumber;
        databaseLegacy.select(query, listener);
    }


    public void selectCommentIdTitleOrderByDate(int user_id, boolean isMyQuestions, boolean isAnswered, boolean isAsec, String limitNumber, OnTaskListeners.Result listener) {
        query = "select id,title from comment where user_id ";
        if (isMyQuestions)
            query += "= ";
        else
            query += "!= ";
        query += user_id + " and solved = " + isAnswered + " order by Date ";
        if (isAsec)
            query += "Asc";
        else
            query += "Desc";

        query += " limit " + limitNumber;
        databaseLegacy.select(query, listener);
    }


    public void selectCommentIdTitleByTitleOrderByTitle(int user_id, String searchtitle, boolean isMyQuestions, boolean isAnswered, boolean isAsec, String limitNumber, OnTaskListeners.Result listener) {
        query = "select id,title from comment where user_id ";
        if (isMyQuestions)
            query += "= ";
        else
            query += "!= ";
        query += user_id + " and solved = " + isAnswered + " and title like '%" + searchtitle + "%' order by title ";
        if (isAsec)
            query += "Asc";
        else
            query += "Desc";

        query += " limit " + limitNumber;
        databaseLegacy.select(query, listener);
    }


    public void selectCommentIdTitleByTitleOrderByDate(int user_id, String searchtitle, boolean isMyQuestions, boolean isAnswered, boolean isAsec, String limitNumber, OnTaskListeners.Result listener) {
        query = "select id,title from comment where user_id ";
        if (isMyQuestions)
            query += "= ";
        else
            query += "!= ";
        query += user_id + " and solved = " + isAnswered + " and title like '%" + searchtitle + "%' order by Date ";
        if (isAsec)
            query += "Asc";
        else
            query += "Desc";

        query += " limit " + limitNumber;
        databaseLegacy.select(query, listener);
    }

    public void insertComment(int user_id, String title, String description, OnTaskListeners.Bool listener) {
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh-mm-ss");
        String formattedDate = df.format(c);
        query = "insert into comment(user_id,title,description,date) values(" + user_id + ",'" + title + "','" + description + "','" + formattedDate + "')";
        databaseLegacy.iud(query, listener);
    }

    public void selectCommentTitleUserName(int question_id, OnTaskListeners.Result listeners) {
        query = "select title,username from user,comment where user.id = user_id and comment.id = " + question_id;
        databaseLegacy.select(query, listeners);
    }

    public void selectReplyIdUserNameUserIdContentBest_answer(int question_id, boolean isBestAnswer, OnTaskListeners.Result listeners) {
        query = "select r.id,username,user_id,content,best_answer from user u,reply r where u.id = user_id and comment_id = " + question_id + " and best_answer = " + isBestAnswer + " order by date";
        databaseLegacy.select(query, listeners);
    }


    public void updateUserUsername(int userId, String username, OnTaskListeners.Bool listener) {
        query = "update User set username = '" + username + "' where id = " + userId;
        databaseLegacy.iud(query, listener);
    }

    public void updateUserPassword(int userId, String password, OnTaskListeners.Bool listener) {
        query = "update User set password = '" + password + "' where id = " + userId;
        databaseLegacy.iud(query, listener);
    }

    public void updateUserEmail(int userId, String email, OnTaskListeners.Bool listener) {
        query = "update User set email = '" + email + "' where id = " + userId;
        databaseLegacy.iud(query, listener);
    }

    public void selectUserEmail(int userId, OnTaskListeners.Result listener) {
        query = "select email from user where id = " + userId;
        databaseLegacy.select(query, listener);
    }


/*
    public void selectReplyId(int question_id, OnTaskListeners.Result listeners) {
        query = "select id from reply where comment_id = " + question_id + "order by date";
        databaseLegacy.select(query, listeners);
    }

    public void selectReplyUsernameByQuestion(int question_id, OnTaskListeners.Result listeners) {
        query = "select username from reply r,username u where r.user_id = u.id comment_id = " + question_id+ "order by date";
        databaseLegacy.select(query, listeners);
    }

    public void selectReplyContent(int question_id, OnTaskListeners.Result listeners) {
        query = "select content from reply where comment_id = " + question_id+ "order by date";
        databaseLegacy.select(query, listeners);
    }*/


    public void selectCommentUserIdUsernameTitleDescription(int questionID, OnTaskListeners.Result listener) {
        query = "select user_id,username,title,description from comment c, user u where u.id = c.user_id and c.id = " + questionID;
        databaseLegacy.select(query, listener);
    }


    public void updateReplyBestAnswer(int replyId, boolean isBest, OnTaskListeners.Bool listener) {
        query = "update reply set best_Answer = " + isBest + " where id = " + replyId;
        databaseLegacy.iud(query, listener);
    }

    public void updateReplyBestAnswerByAll(boolean isBest, OnTaskListeners.Bool listener) {
        query = "update reply set best_Answer = " + isBest;
        databaseLegacy.iud(query, listener);
    }


    public void insertReply(int userId, int questionId, String reply, OnTaskListeners.Bool listener) {
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh-mm-ss");
        String formattedDate = df.format(c);
        query = "insert into reply (comment_id,user_id,content,date,best_answer) values(" + questionId + "," + userId + ",'" + reply + "','" + formattedDate + "'," + false + ")";
        databaseLegacy.iud(query, listener);
    }


    public void insertReport(int userId, Integer questionId,Integer replyId,String discription, String type, OnTaskListeners.Bool listener) {
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh-mm-ss");
        String formattedDate = df.format(c);
        query = "insert into report (user_id,comment_id,reply_id,discription,date,solved,type) values("+userId+","+questionId+","+replyId+",'"+discription+"','"+formattedDate+"',"+false+",'"+type+"')";
        databaseLegacy.iud(query, listener);
    }


    public void selectReportCommentidReplyidDiscription(int reportID, OnTaskListeners.Result listener) {
        query = "select comment_id,reply_id,discription from report where id = "+reportID;
        databaseLegacy.select(query, listener);
    }

    public void selectReportIdDiscriptionByType(String type, OnTaskListeners.Result listener) {
        query = "select id,discription from report where solved = false and type = '"+type +"'";
        databaseLegacy.select(query, listener);
    }






    public void deleteUser(Integer replyId,OnTaskListeners.Bool listener)
    {
        query = "delete from user u,reply r where r.user_id = u.id and r.id = "+replyId;
        databaseLegacy.iud(query,listener);
    }

    public void updateUserSuspendedByReplyid(Integer replyId,boolean suspended,OnTaskListeners.Bool listener)
    {
        query = "update user u ,report r set u.suspended  = "+ suspended +" wehre r.user_id = u.id and r.id = "+ replyId;
        databaseLegacy.iud(query,listener);
    }

    public void deleteReply(Integer replyId,OnTaskListeners.Bool listener)
    {
        query = "delete from reply  where id = "+replyId;
        databaseLegacy.iud(query,listener);
    }

    public void deleteComment(Integer commentId,OnTaskListeners.Bool listener)
    {
        query = "delete from comment  where id = "+commentId;
        databaseLegacy.iud(query,listener);
    }

    public void selectCommentTitleDiscription(int questionId, OnTaskListeners.Result listener) {
        query = "select title,discription from comment where id = " + questionId;
        databaseLegacy.select(query, listener);
    }

    public void selectReplyContent(int replyId, OnTaskListeners.Result listener) {
        query = "select content from reply where id = " + replyId;
        databaseLegacy.select(query, listener);
    }






}
