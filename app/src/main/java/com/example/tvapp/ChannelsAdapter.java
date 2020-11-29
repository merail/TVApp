package com.example.tvapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

public class ChannelsAdapter extends RecyclerView.Adapter<ChannelsAdapter.ChannelsHolder> {
    private final Context context;
    private final Channel[] channels;
    private final OnChannelClickListener onChannelClickListener;

    public ChannelsAdapter(Context context, Channel[] channels, OnChannelClickListener onChannelClickListener) {
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

    @Override
    public void onBindViewHolder(@NonNull ChannelsHolder holder, int position) {
        Glide.with(context)
                .load(Utils.BASE_URL + channels[position].logoUrl)
                .into(holder.channelLogoImageView);

        holder.channelLogoImageView.setOnClickListener(view ->
        {
            onChannelClickListener.onChannelClick(channels[position].id);
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
