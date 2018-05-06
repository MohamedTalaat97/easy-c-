package com.example.android.easyc.Views.ExamViews;

public class quiz {
    int id;
    String Question;
    int Answer;

    public quiz() {
    }

    ;

    void setQuestion(String Q) {
        Question = Q;
    }

    void setId(int d) {
        id = d;
    }

    void setAnswer(int A) {
        Answer = A;
    }

    String getQuestion() {
        return Question;
    }

    Integer getAnswer() {
        return Answer;
    }

    Integer getId() {
        return id;
    }
}
