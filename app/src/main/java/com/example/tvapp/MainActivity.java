package com.example.tvapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements OnGroupClickListener, OnChannelClickListener {
    private GroupsJson[] groupsJson;

    private RecyclerView channelsRecyclerView;
    private RecyclerView programsRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        channelsRecyclerView = findViewById(R.id.channelsRecyclerView);
        channelsRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(),
                RecyclerView.HORIZONTAL, false));

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
        channelsRecyclerView.setAdapter(new ChannelsAdapter(getApplicationContext(),
                groupsJson[position].channels, MainActivity.this));
    }

    @Override
    public void onChannelClick(String channelId) {
        ProgramsService programsService = ProgramsBuilder.build();

        programsService.getPrograms(channelId).enqueue(new Callback<ProgramsJson[]>() {
            @Override
            public void onResponse(@NonNull Call<ProgramsJson[]> call, @NonNull Response<ProgramsJson[]> response) {
                ProgramsJson[] programsJson = response.body();

                if (programsJson != null) {
                    programsRecyclerView.setAdapter(new ProgramsAdapter(getApplicationContext(), programsJson));
                }
            }

            @Override
            public void onFailure(@NonNull Call<ProgramsJson[]> call, @NonNull Throwable t) {
                t.printStackTrace();
            }
        });
    }
}