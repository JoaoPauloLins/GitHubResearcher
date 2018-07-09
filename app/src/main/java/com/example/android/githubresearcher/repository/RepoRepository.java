package com.example.android.githubresearcher.repository;

import android.arch.lifecycle.LiveData;

import com.example.android.githubresearcher.vo.Repo;
import com.example.android.githubresearcher.vo.Resource;

import java.util.List;

public interface RepoRepository {

    LiveData<Resource<List<Repo>>> loadRepos(String user);
}
