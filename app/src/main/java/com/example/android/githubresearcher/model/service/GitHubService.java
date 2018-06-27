package com.example.android.githubresearcher.model.service;

import com.example.android.githubresearcher.model.service.pojo.UserPojo;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GitHubService {

    @GET("autentica")
    Observable<UserPojo> getUser();
}
