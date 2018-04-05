package com.example.android.easyc.Models;


// this is the class that we will save all the data we need while he is using the program
<<<<<<< HEAD:easyc/app/src/main/java/com/example/android/easyc/Models/Data.java
public class Data {
    private  static Data instance = null;
=======
public class UserData {
>>>>>>> 34698dfb9ae71db7e915f42b06a798f3ffb30845:easyc/app/src/main/java/com/example/android/easyc/Models/UserData.java

    private  int userId = 17;
    private  String userName;
    private  int userLevel;
    private  char userType;

////////////////////////////////////////////////////////////

    /// private constuctor for singleton pattern
    private Data()
    {

    }

    public static Data getInstance()
    {
        if(instance == null)
            instance = new Data();
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
