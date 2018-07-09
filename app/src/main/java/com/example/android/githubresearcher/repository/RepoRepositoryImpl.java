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
    public LiveData<Resource<List<Repo>>> loadRepos(String user) {

        int userId = Integer.parseInt(user.split(":")[0]);
        String userLogin = user.split(":")[1];

        LiveData<List<Repo>> dbSource = repoDao.findReposByUserId(userId);
        result.addSource(dbSource, data -> {
            assert data != null;
            if (data.size() == 0) {
                result.removeSource(dbSource);
                LiveData<ApiResponse<List<Repo>>> apiResponse = githubService.getRepos(userLogin);
                result.addSource(dbSource, newData ->setValue(Resource.loading(newData)));
                result.addSource(apiResponse, response -> {
                    result.removeSource(apiResponse);
                    result.removeSource(dbSource);
                    //noinspection ConstantConditions
                    if (response.isSuccessful()) {
                        appExecutors.diskIO().execute(() -> {
                            List<Repo> repos = response.body;
                            assert repos != null;
                            for (Repo repo : repos) {
                                repoDao.insertRepos(repo);
                            }
                            result.addSource(repoDao.findReposByUserId(userId),
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
