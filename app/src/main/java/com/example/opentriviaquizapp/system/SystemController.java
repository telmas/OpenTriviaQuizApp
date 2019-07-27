package com.example.opentriviaquizapp.system;

import com.example.opentriviaquizapp.userprofile.entities.BooleanQuestion;
import com.example.opentriviaquizapp.userprofile.entities.MultipleQuestion;

import java.util.ArrayList;
import java.util.HashMap;

public class SystemController {

    private String userName;
    private int categoryID;
    private String difficulty;
    private ArrayList<BooleanQuestion> booleanQuestions;
    private ArrayList<Boolean> booleanAnswers;
    private ArrayList<Boolean> booleanAnswersHaveBeenSet;
    private ArrayList<MultipleQuestion> multipleQuestions;
    private ArrayList<String> stringAnswers;
    private HashMap<Integer, String> fetchedCategories;

    private ArrayList<String> categoryStringArrayList;
    private ArrayList<Integer> categoryIntegerArrayList;

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

    public HashMap<Integer, String> getFetchedCategories() {
        if(this.fetchedCategories == null){
            return new HashMap<>();
        }
        return fetchedCategories;
    }

    public void setFetchedCategories(HashMap<Integer, String> fetchedCategories) {
        this.fetchedCategories = fetchedCategories;
    }

    public ArrayList<String> getCategoryStringArrayList() {
        return categoryStringArrayList;
    }

    public void setCategoryStringArrayList(ArrayList<String> categoryStringArrayList) {
        this.categoryStringArrayList = categoryStringArrayList;
    }

    public ArrayList<Integer> getCategoryIntegerArrayList() {
        return categoryIntegerArrayList;
    }

    public void setCategoryIntegerArrayList(ArrayList<Integer> categoryIntegerArrayList) {
        this.categoryIntegerArrayList = categoryIntegerArrayList;
    }

    public ArrayList<Boolean> getBooleanAnswersHaveBeenSet() {
        return booleanAnswersHaveBeenSet;
    }

    public void setBooleanAnswersHaveBeenSet(ArrayList<Boolean> booleanAnswersHaveBeenSet) {
        this.booleanAnswersHaveBeenSet = booleanAnswersHaveBeenSet;
    }
}
