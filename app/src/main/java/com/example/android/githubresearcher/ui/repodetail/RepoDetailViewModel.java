package com.example.android.githubresearcher.ui.repodetail;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;

import com.example.android.githubresearcher.repository.RepoListRepository;
import com.example.android.githubresearcher.repository.RepoRepository;
import com.example.android.githubresearcher.repository.UserListRepository;
import com.example.android.githubresearcher.vo.Readme;
import com.example.android.githubresearcher.vo.RepoList;
import com.example.android.githubresearcher.vo.Resource;
import com.example.android.githubresearcher.vo.UserList;

import java.util.List;

import javax.inject.Inject;

public class RepoDetailViewModel extends ViewModel {

    private RepoListRepository repoListRepository;
    private UserListRepository userListRepository;
    private RepoRepository repoRepository;

    private MutableLiveData<Integer> repoIdLiveData = new MutableLiveData<>();

    private LiveData<List<UserList>> userLists = Transformations.switchMap(
            repoIdLiveData, repoId -> userListRepository.loadUserListByRepo(repoId)
    );

    private MutableLiveData<String> repoPathLiveData = new MutableLiveData<>();

    private LiveData<Resource<Readme>> readme = Transformations.switchMap(
            repoPathLiveData, repoPath -> repoRepository.loadReadme(repoPath)
    );

    @SuppressWarnings("unchecked")
    @Inject
    public RepoDetailViewModel(RepoListRepository repoListRepository,
                               UserListRepository userListRepository,
                               RepoRepository repoRepository) {
        this.repoListRepository = repoListRepository;
        this.userListRepository = userListRepository;
        this.repoRepository = repoRepository;
    }

    public void loadUserList(int repoId) {
        repoIdLiveData.setValue(repoId);
    }

    public LiveData<List<UserList>> getUserLists() {
        return userLists;
    }

    public void saveRepoLists(List<RepoList> repoLists) {
        repoListRepository.saveRepoList(repoLists);
    }

    public void loadReadme(String repoPath) {
        repoPathLiveData.setValue(repoPath);
    }

    public LiveData<Resource<Readme>> getReadme() {
        return readme;
    }
}
