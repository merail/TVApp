package com.example.tvapp.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.tvapp.ChannelsFragment;

import java.util.ArrayList;

public class ChannelsViewPagerAdapter extends FragmentPagerAdapter {
    private final ArrayList<ChannelsFragment> channelsFragments;

    public ChannelsViewPagerAdapter(@NonNull FragmentManager fm, ArrayList<ChannelsFragment> channelsFragments) {
        super(fm, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);

        this.channelsFragments = channelsFragments;
    }

    @Override
    public int getCount() {
        return channelsFragments.size();
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return channelsFragments.get(position);
    }
}