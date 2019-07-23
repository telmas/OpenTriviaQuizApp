package com.example.opentriviaquizapp.activities.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.opentriviaquizapp.R;
import com.example.opentriviaquizapp.models.ScoreRecord;

import java.util.ArrayList;


public class PreviousScoresListAdapter extends BaseAdapter {
    private Activity activity;

    private ArrayList<ScoreRecord> scores;

    public PreviousScoresListAdapter(Activity a, ArrayList<ScoreRecord> scores) {
        activity = a;
        this.scores = scores;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        PreviousScoresViewHolder holder;
        if (convertView == null) {
            holder = new PreviousScoresViewHolder();
            convertView = LayoutInflater.from(activity).inflate(R.layout.previous_scores_list_row, parent, false);
            holder.category = (TextView) convertView.findViewById(R.id.category);
            holder.difficulty = (TextView) convertView.findViewById(R.id.difficulty);
            holder.score = (TextView) convertView.findViewById(R.id.score);
            convertView.setTag(holder);
        } else {
            holder = (PreviousScoresViewHolder) convertView.getTag();
        }
        holder.category.setId(position);
        holder.difficulty.setId(position);
        holder.score.setId(position);

        try{
            holder.category.setText(String.format("Category: %s", scores.get(position).getCategory()));
            holder.difficulty.setText(String.format("Difficulty: %s", scores.get(position).getDifficulty()));
            holder.score.setText(String.format("Score: %s", scores.get(position).getScore()));

        }catch(Exception e) {}
        return convertView;
    }
    public int getCount() {
        return scores.size();
    }
    public Object getItem(int position) {
        return position;
    }
    public long getItemId(int position) {
        return position;
    }
}

class PreviousScoresViewHolder {
    TextView category, difficulty, score;
}