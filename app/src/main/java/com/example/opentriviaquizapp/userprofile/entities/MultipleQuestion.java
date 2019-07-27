package com.example.opentriviaquizapp.userprofile.entities;

public class MultipleQuestion {

    private String question;
    private String optionA;
    private String optionB;
    private String optionC;
    private String optionD;
    private String correctOption;

    public MultipleQuestion(){

    }

    public MultipleQuestion(String question, String optionA, String optionB, String optionC, String optionD, String correctOption){
        this.setQuestion(question);
        this.setOptionA(optionA);
        this.setOptionB(optionB);
        this.setOptionC(optionC);
        this.setOptionD(optionD);
        this.setCorrectOption(correctOption);
    }


    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getOptionA() {
        return optionA;
    }

    public void setOptionA(String optionA) {
        this.optionA = optionA;
    }

    public String getOptionB() {
        return optionB;
    }

    public void setOptionB(String optionB) {
        this.optionB = optionB;
    }

    public String getOptionC() {
        return optionC;
    }

    public void setOptionC(String optionC) {
        this.optionC = optionC;
    }

    public String getOptionD() {
        return optionD;
    }

    public void setOptionD(String optionD) {
        this.optionD = optionD;
    }

    public String getCorrectOption() {
        return correctOption;
    }

    public void setCorrectOption(String correctOption) {
        this.correctOption = correctOption;
    }

    public void populateOptions(int position, String option){
        switch (position){
            case 0:
                setOptionA(option);
                break;
            case 1:
                setOptionB(option);
                break;
            case 2:
                setOptionC(option);
                break;
            case 3:
                setOptionD(option);
                break;
        }
    }
}

