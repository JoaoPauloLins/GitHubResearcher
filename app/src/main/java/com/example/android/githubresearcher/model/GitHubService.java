package com.example.android.githubresearcher.model;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GitHubService {

    @GET("autentica")
    Observable<UserEntity> getUser(@Query("nome") String username,
                                   @Query("senha") String password);
}
