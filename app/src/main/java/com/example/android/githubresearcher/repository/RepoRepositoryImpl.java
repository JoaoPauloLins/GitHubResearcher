package com.example.android.githubresearcher.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.support.annotation.MainThread;

import com.example.android.githubresearcher.AppExecutors;
import com.example.android.githubresearcher.api.ApiResponse;
import com.example.android.githubresearcher.api.GithubService;
import com.example.android.githubresearcher.db.RepoDao;
import com.example.android.githubresearcher.util.Objects;
import com.example.android.githubresearcher.vo.Repo;
import com.example.android.githubresearcher.vo.Resource;

import java.util.List;

public class RepoRepositoryImpl implements RepoRepository {

    private final AppExecutors appExecutors;
    private final RepoDao repoDao;
    private final GithubService githubService;

    private final MediatorLiveData<Resource<List<Repo>>> result = new MediatorLiveData<>();

    public RepoRepositoryImpl(AppExecutors appExecutors,
                              RepoDao repoDao,
                              GithubService githubService) {
        this.appExecutors = appExecutors;
        this.repoDao = repoDao;
        this.githubService = githubService;
    }

    @MainThread
    private void setValue(Resource<List<Repo>> newValue) {
        if (!Objects.equals(result.getValue(), newValue)) {
            result.setValue(newValue);
        }
    }

    @Override
    public LiveData<Resource<List<Repo>>> loadRepos(String login) {

        LiveData<List<Repo>> dbSource = repoDao.findReposByUserLogin(login);
        result.addSource(dbSource, data -> {
            assert data != null;
            if (data.size() == 0) {
                result.removeSource(dbSource);
                LiveData<ApiResponse<List<Repo>>> apiResponse = githubService.getRepos(login);
                result.addSource(dbSource, newData ->setValue(Resource.loading(newData)));
                result.addSource(apiResponse, response -> {
                    result.removeSource(apiResponse);
                    //noinspection ConstantConditions
                    if (response.isSuccessful()) {
                        appExecutors.diskIO().execute(() -> {
                            List<Repo> repos = response.body;
                            assert repos != null;
                            for (Repo repo : repos) {
                                repoDao.insertRepos(repo);
                            }
                            result.addSource(repoDao.findReposByUserLogin(login),
                                    newData -> setValue(Resource.success(repos)));
                        });
                    } else {
                        result.addSource(dbSource,
                                newData -> setValue(Resource.error(response.errorMessage, newData)));
                    }
                });
            } else {
                setValue(Resource.success(data));
            }
        });
        return result;
    }
}
