package com.example.android.githubresearcher.repository.service;

import com.example.android.githubresearcher.repository.service.pojo.UserPojo;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GitHubService {

    @GET("autentica/{username}/{password}")
    Observable<UserPojo> getUser(@Path("username") String username,
                                 @Path("password") String password);
}
