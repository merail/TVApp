package com.example.tvapp;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Parcel;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.tvapp.adapters.ChannelsViewPagerAdapter;
import com.example.tvapp.adapters.GroupsAdapter;
import com.example.tvapp.adapters.ProgramsAdapter;
import com.example.tvapp.interfaces.OnChannelClickListener;
import com.example.tvapp.interfaces.OnGroupClickListener;
import com.example.tvapp.server.GroupsBuilder;
import com.example.tvapp.server.GroupsJson;
import com.example.tvapp.server.GroupsService;
import com.example.tvapp.server.ProgramsBuilder;
import com.example.tvapp.server.ProgramsJson;
import com.example.tvapp.server.ProgramsService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@SuppressLint("ParcelCreator")
public class MainActivity extends AppCompatActivity implements OnGroupClickListener, OnChannelClickListener {
    private GroupsJson[] groupsJson;

    private ProgressBar progressBar;
    private ViewPager channelsViewPager;
    private ChannelsViewPagerAdapter channelsViewPagerAdapter;
    private RecyclerView programsRecyclerView;

    private ArrayList<ChannelsFragment> channelsFragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.programsProgressBar);

        channelsViewPager = findViewById(R.id.channelsViewPager);
        channelsFragments = new ArrayList<>();

        programsRecyclerView = findViewById(R.id.programsRecyclerView);
        programsRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(),
                RecyclerView.HORIZONTAL, false));

        sendGroupsRequest();
    }

    private void sendGroupsRequest() {
        GroupsService groupsService = GroupsBuilder.build();

        groupsService.getGroups().enqueue(new Callback<GroupsJson[]>() {
            @Override
            public void onResponse(@NonNull Call<GroupsJson[]> call, @NonNull Response<GroupsJson[]> response) {
                groupsJson = response.body();

                if (groupsJson != null) {
                    RecyclerView groupsRecyclerView = findViewById(R.id.groupsRecyclerView);
                    groupsRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(),
                            RecyclerView.HORIZONTAL, false));
                    groupsRecyclerView.setAdapter(new GroupsAdapter(getApplicationContext(),
                            groupsJson, MainActivity.this));

                    getFragments();
                    channelsViewPagerAdapter = new ChannelsViewPagerAdapter(getSupportFragmentManager(), channelsFragments);
                    channelsViewPager.setAdapter(channelsViewPagerAdapter);
                    onGroupClick(0);
                    onChannelClick(groupsJson[0].channels[0].id);
                }
            }

            @Override
            public void onFailure(@NonNull Call<GroupsJson[]> call, @NonNull Throwable t) {
                t.printStackTrace();
            }
        });
    }

    @Override
    public void onGroupClick(int position) {
        channelsViewPager.setCurrentItem(position);
    }

    @Override
    public void onChannelClick(String channelId) {
        ProgramsService programsService = ProgramsBuilder.build();

        programsService.getPrograms(channelId).enqueue(new Callback<ProgramsJson[]>() {
            @Override
            public void onResponse(@NonNull Call<ProgramsJson[]> call, @NonNull Response<ProgramsJson[]> response) {
                ProgramsJson[] programsJson = response.body();

                if (programsJson != null) {
                    progressBar.setVisibility(View.GONE);
                    programsRecyclerView.setAdapter(new ProgramsAdapter(getApplicationContext(), programsJson));
                }
            }

            @Override
            public void onFailure(@NonNull Call<ProgramsJson[]> call, @NonNull Throwable t) {
                t.printStackTrace();
            }
        });
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

    }

    private void getFragments() {
        for (GroupsJson groupJson : groupsJson) {
            channelsFragments.add(ChannelsFragment.newInstance(groupJson.channels, MainActivity.this));
        }
    }
}