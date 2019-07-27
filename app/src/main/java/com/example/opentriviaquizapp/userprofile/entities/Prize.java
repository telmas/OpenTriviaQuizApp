package com.example.opentriviaquizapp.userprofile.entities;



public class Prize {

    public static final int OPTION_PERFECT_TRUTH = 0;
    public static final int OPTION_PERFECT_CHOICE = 1;
    public static final int OPTION_PERFECT_QUIZ = 2;

    private int id;
    private String name;
    private String description;

    public Prize(int id, String name, String description){
        this.setId(id);
        this.setName(name);
        this.setDescription(description);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
