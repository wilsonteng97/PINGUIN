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

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class NotificationsRecyclerViewAdapter extends RecyclerView.Adapter<NotificationsRecyclerViewAdapter.MyViewHolder> {

    Context context;
    ArrayList<Notification> notifications;

    public NotificationsRecyclerViewAdapter(Context c, ArrayList<Notification> n){
        context = c;
        notifications = n;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_notification, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        notifications.get(position).getUser();
        holder.titleTv.setText(notifications.get(position).getTitle());
        holder.messageTv.setText(notifications.get(position).getMessage());

        SimpleDateFormat spf = new SimpleDateFormat("MMM dd yyyy hh:mma");
        String date = spf.format(notifications.get(position).getTimestamp());
        holder.timeTv.setText(date);

        notifications.get(position).getClass_link();
        notifications.get(position).getHasRead();

    }

    @Override
    public int getItemCount() {
        return (notifications == null) ? 0 : notifications.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView iconIv;
        TextView titleTv, messageTv, timeTv;

        public MyViewHolder(View itemView) {
            super(itemView);
            iconIv = (ImageView) itemView.findViewById(R.id.iconIv);
            titleTv = (TextView) itemView.findViewById(R.id.titleTv);
            timeTv = (TextView) itemView.findViewById(R.id.timeTv);
            messageTv = (TextView) itemView.findViewById(R.id.messageTv);
        }
    }
}
