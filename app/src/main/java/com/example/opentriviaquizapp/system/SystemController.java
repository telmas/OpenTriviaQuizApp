package com.example.opentriviaquizapp.system;

import com.example.opentriviaquizapp.models.BooleanQuestion;
import com.example.opentriviaquizapp.models.MultipleQuestion;

import java.util.ArrayList;

public class SystemController {

    private String userName;
    private int categoryID;
    private String difficulty;
    private ArrayList<BooleanQuestion> booleanQuestions;
    private ArrayList<Boolean> booleanAnswers;
    private ArrayList<MultipleQuestion> multipleQuestions;
    private  ArrayList<String> stringAnswers;

    private static final SystemController INSTANCE = new SystemController();

    public static SystemController getINSTANCE() {
        return INSTANCE;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public ArrayList<BooleanQuestion> getBooleanQuestions() {
        return booleanQuestions;
    }

    public void setBooleanQuestions(ArrayList<BooleanQuestion> booleanQuestions) {
        this.booleanQuestions = booleanQuestions;
    }

    public ArrayList<Boolean> getBooleanAnswers() {

        return booleanAnswers;
    }

    public void setBooleanAnswers(ArrayList<Boolean> booleanAnswers) {
        this.booleanAnswers = booleanAnswers;
    }

    public ArrayList<MultipleQuestion> getMultipleQuestions() {
        return multipleQuestions;
    }

    public void setMultipleQuestions(ArrayList<MultipleQuestion> multipleQuestions) {
        this.multipleQuestions = multipleQuestions;
    }

    public ArrayList<String> getStringAnswers() {
        return stringAnswers;
    }

    public void setStringAnswers(ArrayList<String> stringAnswers) {
        this.stringAnswers = stringAnswers;
    }
}
