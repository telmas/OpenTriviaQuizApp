package com.example.opentriviaquizapp;

public class SystemController {

    private String userName;
    private int categoryID;
    private String difficulty;

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
}
