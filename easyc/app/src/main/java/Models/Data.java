package Models;


// this is the class that we will save all the data we need while he is using the program
public class Data {

    private static int userId = 1;
    private static String userName;
    private static int userLevel;
////////////////////////////////////////////////////////////

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
////////////////////////////////////////////////////////////
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
}