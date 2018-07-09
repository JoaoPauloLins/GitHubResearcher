package com.example.android.githubresearcher.ui.menu.list;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;

import com.example.android.githubresearcher.repository.UserListRepository;
import com.example.android.githubresearcher.vo.Resource;
import com.example.android.githubresearcher.vo.UserList;

import java.util.List;

import javax.inject.Inject;

public class ListViewModel extends ViewModel {

    private UserListRepository userListRepository;

    private MutableLiveData<Integer> userIdLiveData = new MutableLiveData<>();

    private LiveData<List<UserList>> userLists = Transformations.switchMap(
            userIdLiveData, userId -> userListRepository.loadUserList(userId)
    );

    @SuppressWarnings("unchecked")
    @Inject
    public ListViewModel(UserListRepository userListRepository) {
        this.userListRepository = userListRepository;
    }

    public void loadUserList(int userId) {
        userIdLiveData.setValue(userId);
    }

    public LiveData<List<UserList>> getUserList() {
        return userLists;
    }

    public void saveUserList(int userId, String name){
        UserList userList = new UserList(userId, name);
        userListRepository.saveUserList(userList);
    }
}
