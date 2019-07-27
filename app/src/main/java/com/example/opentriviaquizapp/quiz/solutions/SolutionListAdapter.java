package com.example.opentriviaquizapp.quiz.solutions;

import android.app.Activity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.example.opentriviaquizapp.R;
import com.example.opentriviaquizapp.userprofile.entities.BooleanQuestion;
import com.example.opentriviaquizapp.userprofile.entities.MultipleQuestion;
import com.example.opentriviaquizapp.system.SystemController;

import java.util.ArrayList;


public class SolutionListAdapter extends BaseAdapter {
    private Activity activity;

    private ArrayList<String> questions;
    private ArrayList<String> correctAnswers;
    private ArrayList<String> userAnswers;

    public SolutionListAdapter(Activity a) {
        activity = a;
        questions = new ArrayList<>();
        correctAnswers = new ArrayList<>();
        userAnswers = new ArrayList<>();

        ArrayList<BooleanQuestion> booleanCorrectAnswers = SystemController.getINSTANCE().getBooleanQuestions();
        ArrayList<Boolean> booleanUserAnswers = SystemController.getINSTANCE().getBooleanAnswers();
        ArrayList<Boolean> booleanAnswersHaveBeenSet = SystemController.getINSTANCE().getBooleanAnswersHaveBeenSet();

        ArrayList<MultipleQuestion> multipleCorrectAnswers = SystemController.getINSTANCE().getMultipleQuestions();
        ArrayList<String> multipleUserAnswers = SystemController.getINSTANCE().getStringAnswers();

        for (int i = 0; i < 5; i++) {
            questions.add(booleanCorrectAnswers.get(i).getQuestionedStatement());
            correctAnswers.add(String.valueOf(booleanCorrectAnswers.get(i).isAnswer()));
            userAnswers.add(booleanAnswersHaveBeenSet.get(i) ? booleanUserAnswers.get(i).toString() : "");
        }

        for (int i = 0; i < 5; i++) {
            questions.add(multipleCorrectAnswers.get(i).getQuestion());
            correctAnswers.add(String.valueOf(multipleCorrectAnswers.get(i).getCorrectOption()));
            userAnswers.add(multipleUserAnswers.get(i));
        }
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        SolutionListViewHolder holder;
        if (convertView == null) {
            holder = new SolutionListViewHolder();
            convertView = LayoutInflater.from(activity).inflate(R.layout.solutions_list_row, parent, false);
            holder.question = (TextView) convertView.findViewById(R.id.question);
            holder.correctAnswer = (TextView) convertView.findViewById(R.id.correctAnswer);
            holder.givenAnswer = (TextView) convertView.findViewById(R.id.givenAnswer);
            convertView.setTag(holder);
        } else {
            holder = (SolutionListViewHolder) convertView.getTag();
        }
        holder.question.setId(position);
        holder.correctAnswer.setId(position);
        holder.givenAnswer.setId(position);

        try{
            holder.question.setText(String.format("Question: %s", questions.get(position)));
            holder.correctAnswer.setText(String.format("Correct Answer: %s", correctAnswers.get(position)));
            holder.givenAnswer.setText(String.format("Your Answer: %s", userAnswers.get(position)));

        }catch(Exception e) {}
        return convertView;
    }
    public int getCount() {
        return questions.size();
    }
    public Object getItem(int position) {
        return position;
    }
    public long getItemId(int position) {
        return position;
    }
}

class SolutionListViewHolder {
    TextView question, correctAnswer, givenAnswer;
}