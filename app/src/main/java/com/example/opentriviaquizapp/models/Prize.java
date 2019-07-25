package com.example.opentriviaquizapp.models;

public class Prize {

    private int id;
    private String description;

    public Prize(int id, String description){
        this.setId(id);
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
}
