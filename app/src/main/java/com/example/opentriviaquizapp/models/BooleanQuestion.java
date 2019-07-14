package com.example.opentriviaquizapp.models;

public class BooleanQuestion {

    private String questionedStatement;
    private boolean answer;

    public BooleanQuestion( String questionedStatement, boolean answer){
        this.setQuestionedStatement(questionedStatement);
        this.setAnswer(answer);
    }

    public String getQuestionedStatement() {
        return questionedStatement;
    }

    public void setQuestionedStatement(String questionedStatement) {
        this.questionedStatement = questionedStatement;
    }

    public boolean isAnswer() {
        return answer;
    }

    public void setAnswer(boolean answer) {
        this.answer = answer;
    }
}

