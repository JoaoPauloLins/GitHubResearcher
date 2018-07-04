package com.example.android.githubresearcher.ui.menu;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;

import com.example.android.githubresearcher.repository.RepoRepository;
import com.example.android.githubresearcher.vo.Repo;
import com.example.android.githubresearcher.vo.Resource;

import java.util.List;

import javax.inject.Inject;

public class RepositoriesViewModel extends ViewModel {

    private RepoRepository repoRepository;

    private MutableLiveData<String> loginLiveData = new MutableLiveData<>();

    private LiveData<Resource<List<Repo>>> repos = Transformations.switchMap(
            loginLiveData, login -> repoRepository.loadRepos(login)
    );

    @SuppressWarnings("unchecked")
    @Inject
    public RepositoriesViewModel(RepoRepository repoRepository) {
        this.repoRepository = repoRepository;
    }

    public void loadRepos(String login) {
        loginLiveData.setValue(login);
    }

    public LiveData<Resource<List<Repo>>> getRepos() {
        return repos;
    }
}
