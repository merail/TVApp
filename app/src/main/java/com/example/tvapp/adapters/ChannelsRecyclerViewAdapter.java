package com.example.tvapp.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.tvapp.R;
import com.example.tvapp.Utils;
import com.example.tvapp.interfaces.OnChannelClickListener;
import com.example.tvapp.server.Channel;

public class ChannelsRecyclerViewAdapter extends RecyclerView.Adapter<ChannelsRecyclerViewAdapter.ChannelsHolder> {
    private final Context context;
    private final Channel[] channels;
    private final OnChannelClickListener onChannelClickListener;

    private int selectedPosition = -1;

    public ChannelsRecyclerViewAdapter(Context context, Channel[] channels, OnChannelClickListener onChannelClickListener) {
        this.context = context;
        this.channels = channels;
        this.onChannelClickListener = onChannelClickListener;
    }

    @NonNull
    @Override
    public ChannelsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.channel, parent, false);

        return new ChannelsHolder(v);
    }

    @Override
    public int getItemCount() {
        return channels.length;
    }

    @SuppressLint("NewApi")
    @Override
    public void onBindViewHolder(@NonNull ChannelsHolder holder, int position) {
        if (selectedPosition == position)
            holder.channelLogoImageView.setBackgroundColor(context.getColor(R.color.teal_700));
        else
            holder.channelLogoImageView.setBackgroundColor(context.getColor(R.color.black));

        Glide.with(context)
                .load(Utils.BASE_URL + channels[position].logoUrl)
                .into(holder.channelLogoImageView);

        holder.channelLogoImageView.setOnClickListener(view ->
        {
            onChannelClickListener.onChannelClick(channels[position].id);

            selectedPosition = position;
            notifyDataSetChanged();
        });
    }

    public static class ChannelsHolder extends RecyclerView.ViewHolder {
        ImageView channelLogoImageView;

        public ChannelsHolder(@NonNull View itemView) {
            super(itemView);

            channelLogoImageView = itemView.findViewById(R.id.channelLogoImageView);
        }
    }
}
