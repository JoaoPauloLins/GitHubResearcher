package com.example.android.githubresearcher.ui.detail;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;

import com.example.android.githubresearcher.repository.RepoListRepository;
import com.example.android.githubresearcher.repository.UserListRepository;
import com.example.android.githubresearcher.vo.RepoList;
import com.example.android.githubresearcher.vo.UserList;

import java.util.List;

import javax.inject.Inject;

public class DetailViewModel extends ViewModel {

    private RepoListRepository repoListRepository;
    private UserListRepository userListRepository;

    private MutableLiveData<Integer> repoIdLiveData = new MutableLiveData<>();

    private LiveData<List<UserList>> userLists = Transformations.switchMap(
            repoIdLiveData, repoId -> userListRepository.loadUserListByRepo(repoId)
    );

    @SuppressWarnings("unchecked")
    @Inject
    public DetailViewModel(RepoListRepository repoListRepository,
                           UserListRepository userListRepository) {
        this.repoListRepository = repoListRepository;
        this.userListRepository = userListRepository;
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
}
