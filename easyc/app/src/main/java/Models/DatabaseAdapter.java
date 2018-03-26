package Models;

import Interfaces.OnTaskListeners;

public class DatabaseAdapter {

    private DatabaseLegacy databaseLegacy = null;
    private String query;

    public DatabaseAdapter() {
        databaseLegacy = new DatabaseLegacy();
    }

    public DatabaseLegacy getDatabaseLegacy() {
        return databaseLegacy;
    }


    public void selectEmployeeId(OnTaskListeners.Result listeners) {
        query = "select * from employee";
        databaseLegacy.Select(query, listeners);

    }

    public void selectCategories(OnTaskListeners.Result listeners) {
        query = "select title from category";
        databaseLegacy.Select(query, listeners);

    }

    public void selectCatagoryIdByName(String name, OnTaskListeners.Result listeners) {
        query = "select id from category where title = '" + name + "'";
        databaseLegacy.Select(query, listeners);

    }

    public void selectTopics(int cat_id, OnTaskListeners.Result listeners) {
        query = "select title from topic where cat_id = '" + cat_id + "'";
        databaseLegacy.Select(query, listeners);

    }

    public void selectEmployeeName(OnTaskListeners.Result listeners) {
        query = "select name from employee";
        databaseLegacy.Select(query, listeners);

    }

    public void insertUser(String userName, String Pass, char Type, String age, String email,boolean suspended,String requestText, OnTaskListeners.Bool listeners) {
        query = "insert into user(username,password,type,age,email,suspended,request)" +
                " values('" + userName + "','" + Pass + "','" + Type + "'," + age + ",'" + email + "',"+suspended+",'"+requestText+"')";
        databaseLegacy.iud(query, listeners);

    }


    public void insertEmployeeName(String Name, OnTaskListeners.Bool listener) {
        query = "insert into employee (Name) values('" + Name + "')";
        databaseLegacy.iud(query, listener);

    }

    public void selectEmail(String Email, String password, OnTaskListeners.Result listener) {
        query = "select id from Log where Email = '" + Email + "' and password = '" + password + "'";
        databaseLegacy.Select(query, listener);
    }

    public void selectOpinionTitle(OnTaskListeners.Result listener) {
        //still waiting to make opinion table in database
        query = "select id,title from opinion";
        databaseLegacy.Select(query, listener);
    }

    public void selectOpinionTitleUnSeen(OnTaskListeners.Result listener) {
        //still waiting to make opinion table in database
        query = "select id,title from opinion where seen = false";
        databaseLegacy.Select(query, listener);
    }

    public void selectOpinionTitleUnReaded(OnTaskListeners.Result listener) {
        //still waiting to make opinion table in database
        query = "select id,title from opinion where readed = false";
        databaseLegacy.Select(query, listener);
    }

    public void selectOpinionFavourite(int id, OnTaskListeners.Result listener) {
        //still waiting to make opinion table in database
        query = "select favourite from opinion where id = " + id;
        databaseLegacy.Select(query, listener);
    }

    public void selectOpinionTitleFavourite(OnTaskListeners.Result listener) {
        //still waiting to make opinion table in database
        query = "select id,title from opinion where favourite = true";
        databaseLegacy.Select(query, listener);
    }


    public void selectOpinionDescription(int id, OnTaskListeners.Result listener) {
        //still waiting to make opinion table in database
        query = "select description from opinion where id = " + id;
        databaseLegacy.Select(query, listener);
    }

    public void updateOpinionReaded(int id, boolean read, OnTaskListeners.Bool listener) {
        query = "update opinion set readed = " + read + " where id = " + id;
        databaseLegacy.iud(query, listener);
    }

    public void updateOpinionFavourite(int id, Boolean favourite, OnTaskListeners.Bool listener) {
        query = "update opinion set favourite = " + favourite + " where id = " + id;
        databaseLegacy.iud(query, listener);
    }

    public void updateOpinionSeen(int id, boolean seen, OnTaskListeners.Bool listener) {
        query = "update opinion set seen = " + seen + " where id = " + id;
        databaseLegacy.iud(query, listener);
    }

    public void insertOpinion(int user_id, String title, String description, OnTaskListeners.Bool listener) {
        query = "insert into Opinion (user_id,title,description,readed,seen,favourite) values(" + user_id + ",'" + title + "','" + description + "',false,false,false)";
        databaseLegacy.iud(query, listener);
    }


    public void selectUserUsernamePassword(String email, OnTaskListeners.Result listener) {
        query = "select username,password from user where email = '" + email + "'";
        databaseLegacy.Select(query, listener);
    }

    public void selectUserUsername(String userName, OnTaskListeners.Result listener) {
        query = "select username from user where userName = '" + userName + "'";
        databaseLegacy.Select(query, listener);
    }

    public void selectUserEmail(String email, OnTaskListeners.Result listener) {
        query = "select email from user where email = '" + email + "'";
        databaseLegacy.Select(query, listener);
    }

    public void selectUserIdTypeSuspendedLevelName(String name,String password, OnTaskListeners.Result listener) {
        query = "select id,type,suspended,level,Name from user where (userName = '" + name + "' OR email = '" + name + "' )  and password = '"+ password+"'";
        databaseLegacy.Select(query, listener);
    }


    public void selectUserIdUserName(OnTaskListeners.Result listener) {
        query = "select id,username from user where suspended = true and type = 'I'";
        databaseLegacy.Select(query, listener);
    }

    public void selectUserRequest(Integer id,OnTaskListeners.Result listener) {
        query = "select request from user where id = "+id;
        databaseLegacy.Select(query, listener);
    }

    public void updateUserSuspendedRequest(Integer id , boolean state,OnTaskListeners.Bool listener) {
        query = "update User set suspended = "+state+", request = "+""+" where id = "+id;
        databaseLegacy.iud(query, listener);
    }







}
