package com.example.android.githubresearcher.ui.listdetail;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;

import com.example.android.githubresearcher.repository.RepoListRepository;
import com.example.android.githubresearcher.vo.Repo;

import java.util.List;

import javax.inject.Inject;

public class ListDetailViewModel extends ViewModel {

    private RepoListRepository repoListRepository;

    private MutableLiveData<Integer> userListIdLiveData = new MutableLiveData<>();

    private LiveData<List<Repo>> repos = Transformations.switchMap(
            userListIdLiveData, userListId -> repoListRepository.loadReposByUserListId(userListId)
    );

    @SuppressWarnings("unchecked")
    @Inject
    public ListDetailViewModel(RepoListRepository repoListRepository) {
        this.repoListRepository = repoListRepository;
    }

    public void loadRepoList(int userListId) {
        userListIdLiveData.setValue(userListId);
    }

    public LiveData<List<Repo>> getRepos() {
        return repos;
    }
}
