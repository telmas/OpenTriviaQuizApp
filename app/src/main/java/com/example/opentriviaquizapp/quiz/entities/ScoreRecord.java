package com.example.opentriviaquizapp.quiz.entities;

public class ScoreRecord {

    private String category;
    private String difficulty;
    private int score;

    public ScoreRecord(String category, String difficulty, int score){
        this.setCategory(category);
        this.setDifficulty(difficulty);
        this.setScore(score);
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
