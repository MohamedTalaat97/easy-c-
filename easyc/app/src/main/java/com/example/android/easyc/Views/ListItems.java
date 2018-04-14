package com.example.android.easyc.Views;

/**
 * Created by KhALeD SaBrY on 14-Apr-18.
 */

public class ListItems {
    String username;
    String content;
    int userId;
    int replyId;
    boolean bestAnswer;

    public ListItems(String username, String content, int userId, int replyId,boolean bestAnswer) {
        this.username = username;
        this.content = content;
        this.userId = userId;
        this.replyId = replyId;
        this.bestAnswer = bestAnswer;
    }

    public boolean isBestAnswer() {
        return bestAnswer;
    }

    public String getUsername() {
        return username;
    }

    public String getContent() {
        return content;
    }

    public int getUserId() {
        return userId;
    }

    public int getReplyId() {
        return replyId;
    }
}
