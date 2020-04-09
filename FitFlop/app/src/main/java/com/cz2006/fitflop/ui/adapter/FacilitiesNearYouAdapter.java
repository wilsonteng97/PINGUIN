package com.cz2006.fitflop.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cz2006.fitflop.R;
import com.cz2006.fitflop.UserClient;
import com.cz2006.fitflop.model.GeoJsonFeatureHashMapInfo;

import java.util.ArrayList;

/**
 * Adapter is required for recycler views to handle all the facilities contained in the recycler view
 */
public class FacilitiesNearYouAdapter extends RecyclerView.Adapter<FacilitiesNearYouAdapter.ViewHolder> {

    private ArrayList<String> facilitiesNearYou;
    private OnItemClickListener mListener;
    private String name;
    private String address;
    Context context;

    /**
     * Called by RecyclerView when it starts observing this Adapter
     * @param recyclerView
     */
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        context = recyclerView.getContext();
    }

    /**
     * Specify on click action for each facility in the list
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
     * Constructor to initialise the adapter by passing in the ArrayList of facilities
     * @param facilitiesNearYou
     */
    public FacilitiesNearYouAdapter(ArrayList<String> facilitiesNearYou){
        this.facilitiesNearYou = facilitiesNearYou;
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
        String currentItem = this.facilitiesNearYou.get(position);
        holder.nameView.setText(currentItem);
        name = currentItem;
        GeoJsonFeatureHashMapInfo geoJsonFeatureInfo = ((UserClient) context.getApplicationContext()).getGeoJsonFeatureInfo();
        address = geoJsonFeatureInfo.getInfo(currentItem, "ADDRESSPOSTALCODE");
        holder.addressView.setText(address);
    }

    /**
     * Return number of facilities in the ArrayList
     * @return
     */
    @Override
    public int getItemCount() {
        if(facilitiesNearYou != null)
            return this.facilitiesNearYou.size();
        else
            return 0;
    }
}
