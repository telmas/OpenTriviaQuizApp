package com.example.opentriviaquizapp.userprofile.prizes;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.opentriviaquizapp.R;
import com.example.opentriviaquizapp.userprofile.entities.Prize;

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
            holder.name = (TextView) convertView.findViewById(R.id.prizeName);
            holder.description = (TextView) convertView.findViewById(R.id.prizeDescription);
            holder.thumbnailImage = (ImageView) convertView.findViewById(R.id.galleryImage);
            convertView.setTag(holder);
        } else {
            holder = (PrizesViewHolder) convertView.getTag();
        }
        holder.name.setId(position);
        holder.description.setId(position);
        holder.thumbnailImage.setId(position);

        try{
            holder.name.setText(prizes.get(position).getName());
            holder.description.setText(prizes.get(position).getDescription());

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
    TextView name, description;
}