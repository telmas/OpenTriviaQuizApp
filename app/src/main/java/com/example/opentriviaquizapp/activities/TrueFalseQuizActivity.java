package com.example.opentriviaquizapp.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.opentriviaquizapp.R;
import com.example.opentriviaquizapp.models.BooleanQuestion;
import com.example.opentriviaquizapp.models.MultipleQuestion;
import com.example.opentriviaquizapp.system.SystemController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class TrueFalseQuizActivity extends AppCompatActivity {

    private RequestQueue mQueue;
    private ArrayList<MultipleQuestion> multipleQuestionsAndAnswersArrayList;

    private ArrayList<BooleanQuestion> questionsAndAnswers;
    private ArrayList<Boolean> answerHasBeenSet;
    private ArrayList<Boolean> userAnswers;
    private String currentQuestionNumberDescription;
    private int counter = 0;

    TextView currentQuestionNumberTextView;
    TextView questionTextView;
    RadioButton trueOptionRadioButton;
    RadioButton falseOptionRadioButtonB;
    RadioGroup optionsRadioGroup;
    Button previousButton;
    Button nextButton;
    Button proceedButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        questionsAndAnswers = SystemController.getINSTANCE().getBooleanQuestions();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_true_false);

        answerHasBeenSet = new ArrayList<>();
        answerHasBeenSet.add(false);
        answerHasBeenSet.add(false);
        answerHasBeenSet.add(false);
        answerHasBeenSet.add(false);
        answerHasBeenSet.add(false);

        mQueue = Volley.newRequestQueue(this);
        jsonParse();

        setup();
    }

    private void jsonParse() {
        String url = "https://opentdb.com/api.php?amount=5&category=" +
                SystemController.getINSTANCE().getCategoryID() +
                "&difficulty=" +
                SystemController.getINSTANCE().getDifficulty() +
                "&type=multiple&encode=url3986";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("results");
                            multipleQuestionsAndAnswersArrayList = new ArrayList<>();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                String question = null;
                                try {
                                    question = java.net.URLDecoder.decode(jsonObject.getString("question"),"UTF-8");

                                String answer = java.net.URLDecoder.decode(jsonObject.getString("correct_answer"),"UTF-8");
                                JSONArray incorrectAnswers = jsonObject.getJSONArray("incorrect_answers");
                                String incorrectAnswer0 = java.net.URLDecoder.decode(incorrectAnswers.getString(0),"UTF-8");
                                String incorrectAnswer1 = java.net.URLDecoder.decode(incorrectAnswers.getString(1),"UTF-8");
                                String incorrectAnswer2 = java.net.URLDecoder.decode(incorrectAnswers.getString(2),"UTF-8");
                                String[] options = {answer, incorrectAnswer0, incorrectAnswer1, incorrectAnswer2};
                                Collections.shuffle(Arrays.asList(options));
                                MultipleQuestion multipleQuestion = new MultipleQuestion();
                                multipleQuestion.setQuestion(question);
                                multipleQuestion.setCorrectOption(answer);
                                multipleQuestion.populateOptions(0,options[0]);
                                multipleQuestion.populateOptions(1,options[1]);
                                multipleQuestion.populateOptions(2,options[2]);
                                multipleQuestion.populateOptions(3,options[3]);
                                multipleQuestionsAndAnswersArrayList.add(multipleQuestion);
                                } catch (UnsupportedEncodingException e) {
                                    e.printStackTrace();
                                }
                            }
                            SystemController.getINSTANCE().setMultipleQuestions(multipleQuestionsAndAnswersArrayList);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        mQueue.add(request);
    }

    private void setup(){

        userAnswers = new ArrayList<>();
        userAnswers.add(false);
        userAnswers.add(false);
        userAnswers.add(false);
        userAnswers.add(false);
        userAnswers.add(false);
        previousButton = (Button) findViewById(R.id.previousButton) ;
        nextButton = (Button) findViewById(R.id.nextButton) ;
        proceedButton = (Button) findViewById(R.id.proceedButton);
        trueOptionRadioButton = (RadioButton) findViewById(R.id.trueOptionRadioButton);
        falseOptionRadioButtonB = (RadioButton) findViewById(R.id.falseOptionRadioButtonB);
        optionsRadioGroup = (RadioGroup)findViewById(R.id.optionsRadioGroup);
        questionTextView = (TextView) findViewById(R.id.questionTextView);
        currentQuestionNumberTextView = (TextView) findViewById(R.id.currentQuestionNumberTextView) ;

        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickPrev(v);
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickNext(v);
            }
        });

        proceedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SystemController.getINSTANCE().setBooleanAnswers(userAnswers);
                SystemController.getINSTANCE().setBooleanAnswersHaveBeenSet(answerHasBeenSet);
                Intent intent = new Intent(TrueFalseQuizActivity.this, MultipleQuizActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        trueOptionRadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                answerHasBeenSet.set(counter, true);
                userAnswers.set(counter, true);
            }
        });

        falseOptionRadioButtonB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                answerHasBeenSet.set(counter, true);
                userAnswers.set(counter, false);
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
            proceedButton.setVisibility(View.VISIBLE);
            optionsRadioGroup.clearCheck();
            goNext();
        } else if (counter < questionsAndAnswers.size()) {
            optionsRadioGroup.clearCheck();
            proceedButton.setVisibility(View.INVISIBLE);
            goNext();
        }
        if (counter > 0)
            previousButton.setVisibility(View.VISIBLE);
    }

    public void goNext() {

        if (counter >= questionsAndAnswers.size()) {
            counter = questionsAndAnswers.size() - 1;
        }

        currentQuestionNumberTextView.setVisibility(View.VISIBLE);
        trueOptionRadioButton.setVisibility(View.VISIBLE);
        falseOptionRadioButtonB.setVisibility(View.VISIBLE);

        currentQuestionNumberDescription = "Question: " + (counter + 1) + "/" + questionsAndAnswers.size();
        currentQuestionNumberTextView.setText(currentQuestionNumberDescription);
        questionTextView.setText(questionsAndAnswers.get(counter).getQuestionedStatement());

        try {
            if(userAnswers.size() >= counter){
                if(answerHasBeenSet.get(counter)) {
                    if (userAnswers.get(counter)) {
                        trueOptionRadioButton.setChecked(true);
                    } else {
                        falseOptionRadioButtonB.setChecked(true);
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
            proceedButton.setVisibility(View.VISIBLE);
            optionsRadioGroup.clearCheck();
            goPrev();
        } else if (counter < questionsAndAnswers.size()) {
            nextButton.setVisibility(View.VISIBLE);
            proceedButton.setVisibility(View.INVISIBLE);
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
        trueOptionRadioButton.setVisibility(View.VISIBLE);
        falseOptionRadioButtonB.setVisibility(View.VISIBLE);
        currentQuestionNumberDescription = "Question: " + (counter + 1) + "/" + questionsAndAnswers.size();
        currentQuestionNumberTextView.setText(currentQuestionNumberDescription);
        questionTextView.setText(questionsAndAnswers.get(counter).getQuestionedStatement());
        try {
            if(userAnswers.size() >= counter){
                if(answerHasBeenSet.get(counter)) {
                    if (userAnswers.get(counter)) {
                        trueOptionRadioButton.setChecked(true);
                    } else  {
                        falseOptionRadioButtonB.setChecked(true);
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



