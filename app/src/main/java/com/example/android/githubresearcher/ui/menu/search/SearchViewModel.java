package com.example.android.githubresearcher.ui.menu.search;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;

import com.example.android.githubresearcher.repository.SearchRepository;
import com.example.android.githubresearcher.vo.Repo;
import com.example.android.githubresearcher.vo.Resource;

import java.util.List;

import javax.inject.Inject;

public class SearchViewModel extends ViewModel {

    private SearchRepository searchRepository;

    private MutableLiveData<String> searchLiveData = new MutableLiveData<>();

    private LiveData<Resource<List<Repo>>> repos = Transformations.switchMap(
            searchLiveData, search -> searchRepository.searchRepos(search)
    );

    @SuppressWarnings("unchecked")
    @Inject
    public SearchViewModel(SearchRepository searchRepository) {
        this.searchRepository = searchRepository;
    }

    public void searchRepos(String query, int page, int perPage) {
        searchLiveData.setValue(query+":"+page+":"+perPage);
    }

    public LiveData<Resource<List<Repo>>> getResult() {
        return repos;
    }
}
