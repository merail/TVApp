package com.example.tvapp;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tvapp.adapters.ChannelsAdapter;
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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements OnGroupClickListener, OnChannelClickListener {
    private GroupsJson[] groupsJson;

    private ProgressBar progressBar;
    private RecyclerView channelsRecyclerView;
    private RecyclerView programsRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.programsProgressBar);

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
}