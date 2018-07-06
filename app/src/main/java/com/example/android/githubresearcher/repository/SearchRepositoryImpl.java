package com.example.android.githubresearcher.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.MainThread;

import com.example.android.githubresearcher.AppExecutors;
import com.example.android.githubresearcher.api.ApiResponse;
import com.example.android.githubresearcher.api.GithubService;
import com.example.android.githubresearcher.util.Objects;
import com.example.android.githubresearcher.vo.Repo;
import com.example.android.githubresearcher.vo.Resource;

import java.util.List;

public class SearchRepositoryImpl implements SearchRepository{

    private final GithubService githubService;

    private final MediatorLiveData<Resource<List<Repo>>> result = new MediatorLiveData<>();

    public SearchRepositoryImpl(GithubService githubService) {
        this.githubService = githubService;
    }

    @MainThread
    private void setValue(Resource<List<Repo>> newValue) {
        if (!Objects.equals(result.getValue(), newValue)) {
            result.setValue(newValue);
        }
    }

    @Override
    public LiveData<Resource<List<Repo>>> searchRepos(String search) {

        String query = search.split(":")[0];
        int page = Integer.parseInt(search.split(":")[1]);
        int perPage = Integer.parseInt(search.split(":")[2]);

        MutableLiveData<List<Repo>> dbSource = new MutableLiveData<>();
        LiveData<ApiResponse<List<Repo>>> apiResponse = githubService.getSearchRepos(query,page,perPage);
        result.addSource(dbSource, newData -> setValue(Resource.loading(newData)));
        result.addSource(apiResponse, response -> {
            result.removeSource(apiResponse);
            //noinspection ConstantConditions
            if (response.isSuccessful()) {
                result.removeSource(dbSource);
                List<Repo> repos = response.body;
                dbSource.postValue(repos);
                result.addSource(dbSource, newData -> setValue(Resource.success(repos)));
            } else {
                result.addSource(dbSource,
                        newData -> setValue(Resource.error(response.errorMessage, newData)));
            }
        });

        return result;
    }
}
