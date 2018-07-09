package com.example.android.githubresearcher.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.example.android.githubresearcher.AppExecutors;
import com.example.android.githubresearcher.db.RepoListDao;
import com.example.android.githubresearcher.vo.Repo;
import com.example.android.githubresearcher.vo.RepoList;

import java.util.ArrayList;
import java.util.List;

public class RepoListRepositoryImpl implements RepoListRepository{

    private final AppExecutors appExecutors;
    private final RepoListDao repoListDao;

    public RepoListRepositoryImpl(AppExecutors appExecutors, RepoListDao repoListDao) {
        this.appExecutors = appExecutors;
        this.repoListDao = repoListDao;
    }

    @Override
    public void saveRepoList(List<RepoList> repoLists) {

        appExecutors.diskIO().execute(() -> {
            for (RepoList repoList: repoLists) {
                repoListDao.insertRepoList(repoList);
            }
        });
    }

    @Override
    public LiveData<List<Repo>> loadReposByUserListId(int userListId) {

        MutableLiveData<List<Repo>> repoListLiveData = new MutableLiveData<>();
        List<Repo> repos = new ArrayList<>();
        appExecutors.diskIO().execute(() -> {
            List<Integer> repoIds = repoListDao.findRepoIdsByUserListId(userListId);
            for (int repoId : repoIds) {
                repos.add(repoListDao.findRepoById(repoId));
            }
            repoListLiveData.postValue(repos);
        });
        return repoListLiveData;
    }
}
