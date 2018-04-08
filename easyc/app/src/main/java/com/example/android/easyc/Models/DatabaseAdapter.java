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

    public void selectUserIdTypeSuspended(String name, String password, OnTaskListeners.Result listener) {
        query = "select id,type,suspended from user where (userName = '" + name + "' OR email = '" + name + "' )  and password = '" + password + "'";
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

    public void selectReplyIdUserNameContent(int question_id, OnTaskListeners.Result listeners) {
        query = "select id,username,content from user,reply where user.id = user_id and comment_id = " + question_id;
        databaseLegacy.select(query, listeners);
    }

    public void selectReplyIdByBestAnswer(int question_id, OnTaskListeners.Result listeners) {
        query = "select id from reply where best_answer = true and comment_id = " + question_id;
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

}
