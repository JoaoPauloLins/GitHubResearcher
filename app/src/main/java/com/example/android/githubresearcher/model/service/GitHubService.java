package com.example.android.githubresearcher.model.service;

import com.example.android.githubresearcher.model.service.pojo.UserPojo;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GitHubService {

    @GET("autentica/{username}/{password}")
    Observable<UserPojo> getUser(@Path("username") String username,
                                 @Path("password") String password);
}
