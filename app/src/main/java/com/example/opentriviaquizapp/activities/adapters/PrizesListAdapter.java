package com.example.opentriviaquizapp.activities.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.opentriviaquizapp.R;
import com.example.opentriviaquizapp.models.Prize;
import com.example.opentriviaquizapp.models.ScoreRecord;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class PrizesListAdapter extends BaseAdapter {
    private Activity activity;

    private ArrayList<Prize> prizes;

    public PrizesListAdapter(Activity a, ArrayList<Prize> prizes) {
        activity = a;
        this.prizes = prizes;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        PrizesViewHolder holder;
        if (convertView == null) {
            holder = new PrizesViewHolder();
            convertView = LayoutInflater.from(activity).inflate(R.layout.prizes_list_row, parent, false);
            holder.description = (TextView) convertView.findViewById(R.id.prize);
            holder.thumbnailImage = (ImageView) convertView.findViewById(R.id.galleryImage);
            convertView.setTag(holder);
        } else {
            holder = (PrizesViewHolder) convertView.getTag();
        }
        holder.description.setId(position);
        holder.thumbnailImage.setId(position);

        try{
            holder.description.setText(prizes.get(position).getDescription());
            Picasso.with(activity)
                    .load("http://www.myiconfinder.com/uploads/iconsets/256-256-5d41edabc60f4fd2cf3c9f5d35d84045-trophy.png")
                    .resize(250, 275)
                    .into(holder.thumbnailImage);

        }catch(Exception e) {}
        return convertView;
    }
    public int getCount() {
        return prizes.size();
    }
    public Object getItem(int position) {
        return position;
    }
    public long getItemId(int position) {
        return position;
    }
}

class PrizesViewHolder {
    ImageView thumbnailImage;
    TextView description;
}