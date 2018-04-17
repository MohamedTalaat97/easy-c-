package com.example.android.easyc.Views;

public class quiz {
    Integer id;
    String Question;
    Integer Answer;

    public quiz() {
    }

    ;

    void setQuestion(String Q) {
        Question = Q;
    }

    void setId(Integer d) {
        id = d;
    }

    void setAnswer(Integer A) {
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
