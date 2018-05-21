package com.example.android.githubresearcher.repository.service;

import com.example.android.githubresearcher.repository.service.pojo.RepositoryPojo;
import com.example.android.githubresearcher.repository.service.pojo.UserPojo;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GitHubService {

    @GET("users/{name}")
    Observable<UserPojo> getUser(@Path("name") String name);

    @GET("users/{name}/repos")
    Observable<List<RepositoryPojo>> getUserRepository(@Path("{name}") String name,
                                                       @Query("page") int page,
                                                       @Query("per_page") int perPage);
}
