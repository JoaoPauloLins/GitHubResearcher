package com.example.android.githubresearcher.model;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GitHubService {

    @GET("autentica/{username}/{password}")
    Observable<User> getUser(@Path("username") String username,
                             @Path("password") String password);
}
