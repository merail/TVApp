package com.example.tvapp.interfaces;

import android.os.Parcelable;

public interface OnChannelClickListener extends Parcelable {
    void onChannelClick(String channelId);
}
