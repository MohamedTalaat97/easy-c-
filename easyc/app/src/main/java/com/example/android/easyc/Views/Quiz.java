package com.example.android.easyc.Views;

public class Quiz
{
    String Question;
    Boolean Answer;
    public
    Quiz() {};
    void setQuestion(String Q)
    {
        Question = Q;
    }
    void setAnswer(Boolean A)
    {
        Answer = A;
    }
    String getQuestion()
    {
        return Question;
    }
    Boolean getAnswer()
    {
        return Answer;
    }
}
