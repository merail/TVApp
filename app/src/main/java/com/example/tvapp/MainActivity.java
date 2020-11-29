package com.example.tvapp;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sendRequest();
    }

    private void sendRequest() {
        GroupsService groupsService = GroupsBuilder.build();

        groupsService.getDatabase().enqueue(new Callback<GroupsJson[]>() {
            @Override
            public void onResponse(@NonNull Call<GroupsJson[]> call, @NonNull Response<GroupsJson[]> response) {
                GroupsJson[] groupsJson = response.body();

                if (groupsJson != null) {
                    Log.d("aaaaaaaaaaaaaaa", groupsJson[0].id);
                }
            }

            @Override
            public void onFailure(@NonNull Call<GroupsJson[]> call, @NonNull Throwable t) {
                t.printStackTrace();
            }
        });
    }
}