package com.cz2006.fitflop.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cz2006.fitflop.R;
import com.cz2006.fitflop.model.StarredItem;

import java.util.ArrayList;

public class StarredAdapter extends RecyclerView.Adapter<StarredAdapter.ViewHolder> {

    private ArrayList<StarredItem> starredItems;
    private OnItemClickListener mListener;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView nameView;
        public TextView addressView;

        public ViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            nameView = itemView.findViewById(R.id.starredName);
            addressView = itemView.findViewById(R.id.starredAddress);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listener != null){
                        int position = getAdapterPosition();
                        if(position!=RecyclerView.NO_POSITION) {   //ensure position is valid
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }

    public StarredAdapter(ArrayList<StarredItem> starredItems){
        this.starredItems = starredItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.starred_item, parent, false);
        ViewHolder vh = new ViewHolder(v, mListener);
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
        if(starredItems != null)
            return this.starredItems.size();
        else
            return 0;
    }
}
