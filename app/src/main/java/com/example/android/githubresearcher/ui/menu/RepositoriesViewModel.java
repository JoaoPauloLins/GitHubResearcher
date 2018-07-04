package com.example.android.githubresearcher.ui.menu;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.android.githubresearcher.repository.RepoRepository;
import com.example.android.githubresearcher.vo.Repo;
import com.example.android.githubresearcher.vo.Resource;

import java.util.List;

import javax.inject.Inject;

public class RepositoriesViewModel extends ViewModel {

    private RepoRepository repoRepository;

    @SuppressWarnings("unchecked")
    @Inject
    public RepositoriesViewModel(RepoRepository repoRepository) {
        this.repoRepository = repoRepository;
    }

    public LiveData<Resource<List<Repo>>> getRepos(String login) {
        return repoRepository.loadRepos(login);
    }
}
