package com.example.opentriviaquizapp.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.opentriviaquizapp.R;
import com.example.opentriviaquizapp.models.MultipleQuestion;
import com.example.opentriviaquizapp.system.SystemController;

import java.util.ArrayList;

public class MultipleQuizActivity extends AppCompatActivity {

    private ArrayList<MultipleQuestion> questionsAndAnswers;
    private ArrayList<Boolean> answerHasBeenSet;
    private ArrayList<String> userAnswers;
    private String currentQuestionNumberDescription;
    private int counter = 0;

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

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SystemController.getINSTANCE().setStringAnswers(userAnswers);
                Toast.makeText(getApplicationContext(), SystemController.getINSTANCE().getBooleanAnswers()
                        + "\n" +
                        userAnswers.toString(), Toast.LENGTH_SHORT).show();//to show the answers have been registered

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
}



