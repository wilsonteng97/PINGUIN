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

/**
 * Adapter is required for recycler views to handle all the StarredItems contained in the recycler view
 */
public class StarredAdapter extends RecyclerView.Adapter<StarredAdapter.ViewHolder> {

    private ArrayList<StarredItem> starredItems;
    private OnItemClickListener mListener;

    /**
     * Specify on click action for each starred item
     */
    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    /**
     * Register a callback to be invoked when an item in this AdapterView has been clicked.
     * @param listener
     */
    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    /**
     * Create ViewHolder
     */
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

    /**
     * Constructor to initialise the StarredAdapter by passing in the ArrayList of starred items
     * @param starredItems
     */
    public StarredAdapter(ArrayList<StarredItem> starredItems){
        this.starredItems = starredItems;
    }

    /**
     * Initialise the ViewHolder once it has been created
     * @param parent
     * @param viewType
     * @return
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.starred_item, parent, false);
        ViewHolder vh = new ViewHolder(v, mListener);
        return vh;
    }

    /**
     * Helps to display the data at the specific position
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        StarredItem currentItem = this.starredItems.get(position);
        holder.nameView.setText(currentItem.getName());
        holder.addressView.setText(currentItem.getAddress());
    }

    /**
     * Return number of starred items
     * @return
     */
    @Override
    public int getItemCount() {
        if(starredItems != null)
            return this.starredItems.size();
        else
            return 0;
    }
}
