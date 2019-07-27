package com.example.opentriviaquizapp.quiz;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.opentriviaquizapp.R;
import com.example.opentriviaquizapp.hub.HomeActivity;
import com.example.opentriviaquizapp.quiz.entities.DifficultyTypeConstants;
import com.example.opentriviaquizapp.quiz.solutions.ViewSolutionsActivity;
import com.example.opentriviaquizapp.userprofile.entities.BooleanQuestion;
import com.example.opentriviaquizapp.userprofile.entities.MultipleQuestion;
import com.example.opentriviaquizapp.userprofile.entities.Prize;
import com.example.opentriviaquizapp.system.DBHelper;
import com.example.opentriviaquizapp.system.SystemController;

import java.util.ArrayList;

public class MultipleQuizActivity extends AppCompatActivity {

    private ArrayList<MultipleQuestion> questionsAndAnswers;
    private ArrayList<Boolean> answerHasBeenSet;
    private ArrayList<String> userAnswers;
    private String currentQuestionNumberDescription;
    private int counter = 0;

    private boolean perfectTruthPrizeWon;
    private boolean perfectChoicePrizeWon;
    private boolean perfectQuizPrizeWon;

    private boolean disableBackButton;

    TextView currentQuestionNumberTextView;
    TextView questionTextView;
    RadioButton optionARadioButton;
    RadioButton optionBRadioButton;
    RadioButton optionCRadioButton;
    RadioButton optionDRadioButton;
    RadioGroup optionsRadioGroup;
    Button nextButton;
    Button previousButton;
    Button submitButton;

    DBHelper dataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_multiple);

        answerHasBeenSet = new ArrayList<>();
        answerHasBeenSet.add(false);
        answerHasBeenSet.add(false);
        answerHasBeenSet.add(false);
        answerHasBeenSet.add(false);
        answerHasBeenSet.add(false);

        dataBase = new DBHelper(this);

        disableBackButton = false;

        setup();
    }

    private void setup(){
        questionsAndAnswers = SystemController.getINSTANCE().getMultipleQuestions();
        userAnswers = new ArrayList<>();
        userAnswers.add("");
        userAnswers.add("");
        userAnswers.add("");
        userAnswers.add("");
        userAnswers.add("");
        nextButton = (Button) findViewById(R.id.nextButton) ;
        previousButton = (Button) findViewById(R.id.previousButton) ;
        submitButton = (Button) findViewById(R.id.submitButton);
        optionARadioButton = (RadioButton) findViewById(R.id.optionARadioButton);
        optionBRadioButton = (RadioButton) findViewById(R.id.optionBRadioButton);
        optionCRadioButton = (RadioButton) findViewById(R.id.optionCRadioButton);
        optionDRadioButton = (RadioButton) findViewById(R.id.optionDRadioButton);
        optionsRadioGroup = (RadioGroup)findViewById(R.id.optionsRadioGroup);
        questionTextView = (TextView) findViewById(R.id.questionTextView);
        currentQuestionNumberTextView = (TextView) findViewById(R.id.currentQuestionNumberTextView);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickNext(v);
            }
        });

        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickPrev(v);
            }
        });

        optionARadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                answerHasBeenSet.set(counter, true);
                userAnswers.set(counter, (String) optionARadioButton.getText());
            }
        });

        optionBRadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                answerHasBeenSet.set(counter, true);
                userAnswers.set(counter, (String) optionBRadioButton.getText());
            }
        });

        optionCRadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                answerHasBeenSet.set(counter, true);
                userAnswers.set(counter, (String) optionCRadioButton.getText());
            }
        });

        optionDRadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                answerHasBeenSet.set(counter, true);
                userAnswers.set(counter, (String) optionDRadioButton.getText());
            }
        });

        questionTextView.setText("Quiz");
        previousButton.setVisibility(View.GONE);
        currentQuestionNumberDescription = "Question: " + 1 + "/" + questionsAndAnswers.size();
        goNext();
    }

    public void clickNext(View view) {

        counter++;

        if (counter == questionsAndAnswers.size() -1) {
            nextButton.setVisibility(View.INVISIBLE);
            submitButton.setVisibility(View.VISIBLE);
            optionsRadioGroup.clearCheck();
            goNext();
        } else if (counter < questionsAndAnswers.size()) {
            optionsRadioGroup.clearCheck();
            submitButton.setVisibility(View.INVISIBLE);
            goNext();
        }
        if (counter > 0){
            previousButton.setVisibility(View.VISIBLE);
        }
    }

    public void goNext() {

        currentQuestionNumberTextView.setVisibility(View.VISIBLE);
        optionARadioButton.setVisibility(View.VISIBLE);
        optionBRadioButton.setVisibility(View.VISIBLE);
        optionCRadioButton.setVisibility(View.VISIBLE);
        optionDRadioButton.setVisibility(View.VISIBLE);

        if (counter >= questionsAndAnswers.size()) {
            counter = questionsAndAnswers.size() - 1;
        }

        optionARadioButton.setText(questionsAndAnswers.get(counter).getOptionA());
        optionBRadioButton.setText(questionsAndAnswers.get(counter).getOptionB());
        optionCRadioButton.setText(questionsAndAnswers.get(counter).getOptionC());
        optionDRadioButton.setText(questionsAndAnswers.get(counter).getOptionD());


        currentQuestionNumberDescription = "Question: " + (counter + 1) + "/" + questionsAndAnswers.size();
        currentQuestionNumberTextView.setText(currentQuestionNumberDescription);
        String q = questionsAndAnswers.get(counter).getQuestion();
        questionTextView.setText(q);

        try {
            if(userAnswers.size() >= counter){
                if(answerHasBeenSet.get(counter)) {
                    if (userAnswers.get(counter).trim().toUpperCase().equals(optionARadioButton.getText().toString().trim().toUpperCase())) {
                        optionARadioButton.setChecked(true);
                    } else if(userAnswers.get(counter).trim().toUpperCase().equals(optionBRadioButton.getText().toString().trim().toUpperCase())){
                        optionBRadioButton.setChecked(true);
                    } else if(userAnswers.get(counter).trim().toUpperCase().equals(optionCRadioButton.getText().toString().trim().toUpperCase())){
                        optionCRadioButton.setChecked(true);
                    }else if(userAnswers.get(counter).trim().toUpperCase().equals(optionDRadioButton.getText().toString().trim().toUpperCase())){
                        optionDRadioButton.setChecked(true);
                    }
                }
            } else {
                optionsRadioGroup.clearCheck();
            }
        } catch (Exception e) {
            optionsRadioGroup.clearCheck();
        }
    }

    public void clickPrev(View view) {

        counter--;

        if (counter == questionsAndAnswers.size() - 1) {
            nextButton.setVisibility(View.INVISIBLE);
            submitButton.setVisibility(View.VISIBLE);
            optionsRadioGroup.clearCheck();
            goPrev();
        } else if (counter < questionsAndAnswers.size()) {
            nextButton.setVisibility(View.VISIBLE);
            submitButton.setVisibility(View.INVISIBLE);
            goPrev();
        }
        if (counter == 0){
            previousButton.setVisibility(View.GONE);
        }
    }

    public void goPrev() {

        if (counter < 0) {
            counter = 0;
        }

        currentQuestionNumberTextView.setVisibility(View.VISIBLE);
        optionARadioButton.setVisibility(View.VISIBLE);
        optionBRadioButton.setVisibility(View.VISIBLE);
        optionCRadioButton.setVisibility(View.VISIBLE);
        optionDRadioButton.setVisibility(View.VISIBLE);

        currentQuestionNumberDescription = "Question: " + (counter + 1) + "/" + questionsAndAnswers.size();
        currentQuestionNumberTextView.setText(currentQuestionNumberDescription);
        questionTextView.setText(questionsAndAnswers.get(counter).getQuestion());

        optionARadioButton.setText(questionsAndAnswers.get(counter).getOptionA());
        optionBRadioButton.setText(questionsAndAnswers.get(counter).getOptionB());
        optionCRadioButton.setText(questionsAndAnswers.get(counter).getOptionC());
        optionDRadioButton.setText(questionsAndAnswers.get(counter).getOptionD());

        try {
            if(userAnswers.size() >= counter){
                if(answerHasBeenSet.get(counter)) {
                    if (userAnswers.get(counter).trim().toUpperCase().equals(optionARadioButton.getText().toString().trim().toUpperCase())) {
                        optionARadioButton.setChecked(true);
                    } else if(userAnswers.get(counter).trim().toUpperCase().equals(optionBRadioButton.getText().toString().trim().toUpperCase())){
                        optionBRadioButton.setChecked(true);
                    } else if(userAnswers.get(counter).trim().toUpperCase().equals(optionCRadioButton.getText().toString().trim().toUpperCase())){
                        optionCRadioButton.setChecked(true);
                    }else if(userAnswers.get(counter).trim().toUpperCase().equals(optionDRadioButton.getText().toString().trim().toUpperCase())){
                        optionDRadioButton.setChecked(true);
                    }
                }
            } else {
                optionsRadioGroup.clearCheck();
            }
        } catch (Exception e) {
            optionsRadioGroup.clearCheck();
        }
    }

    public int getScore() {

        ArrayList<BooleanQuestion> booleanCorrectAnswers = SystemController.getINSTANCE().getBooleanQuestions();
        ArrayList<Boolean> booleanUserAnswers = SystemController.getINSTANCE().getBooleanAnswers();
        ArrayList<Boolean> booleanAnswersHaveBeenSet = SystemController.getINSTANCE().getBooleanAnswersHaveBeenSet();

        ArrayList<MultipleQuestion> multipleCorrectAnswers = SystemController.getINSTANCE().getMultipleQuestions();
        ArrayList<String> multipleUserAnswers = SystemController.getINSTANCE().getStringAnswers();

        int score = 0;
        int booleanScore = 0;
        int multipleScore = 0;
        for (int i = 0; i < 5; i++) {

            if((booleanCorrectAnswers.get(i).isAnswer() == booleanUserAnswers.get(i)) && booleanAnswersHaveBeenSet.get(i)){
                score++;
                booleanScore++;
            }
            if(multipleCorrectAnswers.get(i).getCorrectOption().trim().equalsIgnoreCase(multipleUserAnswers.get(i).trim().toUpperCase())){
                score++;
                multipleScore++;
            }
        }
        setPrizes(booleanScore, multipleScore, score);
        return score;
    }

    public void showScore(View view){
        SystemController.getINSTANCE().setStringAnswers(userAnswers);

        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.dialog_score, null);

        Drawable drawable = ContextCompat.getDrawable(getApplicationContext(), R.drawable.score_ring);
        TextView scoreTextView = alertLayout.findViewById(R.id.scoreTextView);
        ProgressBar progressBar = alertLayout.findViewById(R.id.ringProgressbar);

        progressBar.setMax(10);
        progressBar.setSecondaryProgress(10);

        int score = getScore();
        progressBar.setProgress(score);
        progressBar.setProgressDrawable(drawable);
        scoreTextView.setText(score + "/10");

        AlertDialog.Builder scoreDialog = new AlertDialog.Builder(this);
        scoreDialog.setTitle("SCORE");
        scoreDialog.setMessage(getPrizesWonMessage());
        scoreDialog.setView(alertLayout);
        scoreDialog.setCancelable(false);

        scoreDialog.setNegativeButton("Home", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(MultipleQuizActivity.this, HomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        scoreDialog.setPositiveButton("Check Solutions", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(MultipleQuizActivity.this, ViewSolutionsActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        int difficultyId;
        switch(SystemController.getINSTANCE().getDifficulty().toUpperCase()) {
            case "EASY":
                difficultyId = DifficultyTypeConstants.OPTION_EASY;
                break;
            case "MEDIUM":
                difficultyId = DifficultyTypeConstants.OPTION_MEDIUM;
                break;
            case "HARD":
                difficultyId = DifficultyTypeConstants.OPTION_HARD;
                break;
            default:
                difficultyId = -1;
                break;
        }
        if(dataBase.storeUserScore(SystemController.getINSTANCE().getUserName().trim(), score,
                SystemController.getINSTANCE().getCategoryID(), difficultyId)) {

            AlertDialog dialog = scoreDialog.create();
            dialog.show();
            disableBackButton = true;
        }
    }

    private void setPrizes(int booleanScore, int multipleScore, int fullScore){
        if(booleanScore == 5 && !dataBase.hasWonPrize(SystemController.getINSTANCE().getUserName(), Prize.OPTION_PERFECT_TRUTH)){
            Prize prize = new Prize(Prize.OPTION_PERFECT_TRUTH, "Perfect Truth", "Answer 5 true/false questions correctly.");
            if(dataBase.storePrize(SystemController.getINSTANCE().getUserName(),prize)) {
                perfectTruthPrizeWon = true;
            }
        }
        if(multipleScore == 5 && !dataBase.hasWonPrize(SystemController.getINSTANCE().getUserName(), Prize.OPTION_PERFECT_CHOICE)){
            Prize prize = new Prize(Prize.OPTION_PERFECT_CHOICE, "Perfect Choice", "Answer 5 multiple choice questions correctly.");
            if(dataBase.storePrize(SystemController.getINSTANCE().getUserName(),prize)){
                perfectChoicePrizeWon = true;
            }
        }
        if(fullScore == 10 && !dataBase.hasWonPrize(SystemController.getINSTANCE().getUserName(), Prize.OPTION_PERFECT_QUIZ)){
            Prize prize = new Prize(Prize.OPTION_PERFECT_QUIZ, "Perfect Quiz", "Answer all 10 questions correctly.");
            if(dataBase.storePrize(SystemController.getINSTANCE().getUserName(),prize)){
                perfectQuizPrizeWon = true;
            }
        }
    }

    private String getPrizesWonMessage(){
        String message = "";
        if(perfectTruthPrizeWon || perfectChoicePrizeWon){
            message += "Congratulations! You won these prizes:\n";
        }
        if(perfectTruthPrizeWon){
            message += "\"Perfect Truth!\"\n";
        }
        if(perfectChoicePrizeWon){
            message += "\"Perfect Choice!\"\n";
        }
        if(perfectQuizPrizeWon){
            message += "\"Perfect Quiz!\"\n";
        }
        return message;
    }

    @Override
    public void onBackPressed() {
        if(disableBackButton){
            Toast.makeText(this, "Quiz is finished. Cannot go back.", Toast.LENGTH_LONG).show();
        } else {
            super.onBackPressed();
        }
    }
}



