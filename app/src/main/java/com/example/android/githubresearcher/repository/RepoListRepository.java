package com.example.android.githubresearcher.repository;

import com.example.android.githubresearcher.vo.RepoList;

import java.util.List;

public interface RepoListRepository {

    void saveRepoList(List<RepoList> repoLists);
}
