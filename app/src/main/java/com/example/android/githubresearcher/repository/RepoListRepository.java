package com.example.android.githubresearcher.repository;

import android.arch.lifecycle.LiveData;

import com.example.android.githubresearcher.vo.Repo;
import com.example.android.githubresearcher.vo.RepoList;

import java.util.List;

public interface RepoListRepository {

    void saveRepoList(List<RepoList> repoLists);
    LiveData<List<Repo>> loadReposByUserListId(int userListId);
}
