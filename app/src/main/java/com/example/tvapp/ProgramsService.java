package com.example.tvapp;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ProgramsService {

    @GET("/tv/program/:")
    Call<ProgramsJson[]> getPrograms(@Query("channelsId") String channelId);
}
