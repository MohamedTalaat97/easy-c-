package com.example.android.easyc.Models;


// this is the class that we will save all the data we need while he is using the program

public class UserData {

    private  static UserData instance = null;
    private  int userId = 17;
    private  String userName = "kk";
    private  int userLevel;
    private  char userType;

////////////////////////////////////////////////////////////

    /// private constuctor for singleton pattern
    private UserData()
    {

    }

    public static UserData getInstance()
    {
        if(instance == null)
            instance = new UserData();
        return instance;
    }


    //setters
    public void setUserId(int userid)
    {
        this.userId = userid;
    }
    public void setUserName(String userName)
    {
        this.userName = userName;
    }
    public void setUserLevel(int userLevel)
    {
        this.userLevel = userLevel;
    }
    public void setUserType(char usertype)
    {
       userType= usertype;
    }
////////////////////////////////////////////////////////////
    //getters
    public int getUserId()
    {
        return userId;
    }
    public String getUserName()
    {
        return userName;
    }
    public int getUserLevel()
    {
        return userLevel;
    }

    public char getUserType()
    {
        return userType;
    }


}
