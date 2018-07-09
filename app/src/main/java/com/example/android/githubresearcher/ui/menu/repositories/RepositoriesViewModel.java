package com.example.android.githubresearcher.ui.menu.repositories;

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

    private MutableLiveData<String> userLiveData = new MutableLiveData<>();

    private LiveData<Resource<List<Repo>>> repos = Transformations.switchMap(
            userLiveData, user -> repoRepository.loadRepos(user)
    );

    @SuppressWarnings("unchecked")
    @Inject
    public RepositoriesViewModel(RepoRepository repoRepository) {
        this.repoRepository = repoRepository;
    }

    public void loadRepos(int userId, String userLogin) {
        String user = userId+":"+userLogin;
        userLiveData.setValue(user);
    }

    public LiveData<Resource<List<Repo>>> getRepos() {
        return repos;
    }
}
