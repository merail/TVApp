package com.example.tvapp.server;

import retrofit2.Call;
import retrofit2.http.GET;

public interface GroupsService {

    @GET("/tv/group/")
    Call<GroupsJson[]> getGroups();
}
