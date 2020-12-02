package com.example.tvapp.server;

import android.os.Parcel;
import android.os.Parcelable;

public class Channel implements Parcelable {
    public String id;
    public String name;
    public String logoUrl;

    protected Channel(Parcel in) {
        id = in.readString();
        name = in.readString();
        logoUrl = in.readString();
    }

    public static final Creator<Channel> CREATOR = new Creator<Channel>() {
        @Override
        public Channel createFromParcel(Parcel in) {
            return new Channel(in);
        }

        @Override
        public Channel[] newArray(int size) {
            return new Channel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(name);
        parcel.writeString(logoUrl);
    }
}