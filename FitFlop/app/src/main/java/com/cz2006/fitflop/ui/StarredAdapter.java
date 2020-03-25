package com.cz2006.fitflop.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cz2006.fitflop.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class StarredAdapter extends RecyclerView.Adapter<StarredAdapter.ViewHolder> {

    private ArrayList<StarredItem> starredItems;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView nameView;
        public TextView addressView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameView = itemView.findViewById(R.id.starredName);
            addressView = itemView.findViewById(R.id.starredAddress);
        }
    }

    public StarredAdapter(ArrayList<StarredItem> starredItems){
        this.starredItems = starredItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.starred_item, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        StarredItem currentItem = this.starredItems.get(position);
        holder.nameView.setText(currentItem.getName());
        holder.addressView.setText(currentItem.getAddress());
    }

    @Override
    public int getItemCount() {
        return this.starredItems.size();
    }
}