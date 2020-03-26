package com.cz2006.fitflop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cz2006.fitflop.R;
import com.cz2006.fitflop.util.SettingsData;

public class SettingsAdapter extends RecyclerView.Adapter {

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_settings, parent, false);

        return new SettingsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((SettingsViewHolder) holder).bindView(position);
    }

    @Override
    public int getItemCount() {
        return SettingsData.title.length;
    }

//    String title[],description[];
//    int picturePath[];
//    Context context;
//
//    SettingsViewHolder holder;


//    public SettingsAdapter(Context c, String s1[], String s2[], int images[]) {
//        context = c;
//        title = s1;
//        description = s2;
//        picturePath = images;
//    }

    private class SettingsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mItemText1, mItemText2;
        private ImageView mItemImage;

        public SettingsViewHolder(@NonNull View itemView) {
            super(itemView);
            mItemImage = (ImageView) itemView.findViewById(R.id.image);
            mItemText1 = (TextView) itemView.findViewById(R.id.textView1);
            mItemText2 = (TextView) itemView.findViewById(R.id.textView2);
            itemView.setOnClickListener(this);
        }

        public void bindView(int position) {
             mItemText1.setText(SettingsData.title[position]);
             mItemText2.setText(SettingsData.description[position]);
             mItemImage.setImageResource(SettingsData.picturePath[position]);

            }

        public void onClick(View view) {

        }


        }

//        @Override
//        public void onClick(View v) {
//
//        }
}
