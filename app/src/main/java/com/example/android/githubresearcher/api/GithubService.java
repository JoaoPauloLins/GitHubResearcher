package com.example.android.githubresearcher.api;

import android.arch.lifecycle.LiveData;

import com.example.android.githubresearcher.vo.Repo;
import com.example.android.githubresearcher.vo.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GithubService {

    @GET("autentica")
    LiveData<ApiResponse<User>> getUser();

    @GET("repos/{login}")
    LiveData<ApiResponse<List<Repo>>> getRepos(@Path("login") String login);

    @GET("search/repos/{query}/{page}/{perPage}")
    LiveData<ApiResponse<List<Repo>>> getSearchRepos(@Path("query") String query,
                                                     @Path("page") int page,
                                                     @Path("perPage") int perPage);
}
