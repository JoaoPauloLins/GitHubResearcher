package com.example.android.githubresearcher.di;

import android.arch.lifecycle.ViewModel;

import com.example.android.githubresearcher.ui.login.LoginViewModel;
import com.example.android.githubresearcher.ui.menu.list.ListViewModel;
import com.example.android.githubresearcher.ui.menu.repositories.RepositoriesViewModel;
import com.example.android.githubresearcher.ui.menu.search.SearchViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel.class)
    abstract ViewModel bindLoginViewModel(LoginViewModel loginViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(RepositoriesViewModel.class)
    abstract ViewModel bindRepositoriesViewModel(RepositoriesViewModel repositoriesViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(SearchViewModel.class)
    abstract ViewModel bindSearchViewModel(SearchViewModel searchViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(ListViewModel.class)
    abstract ViewModel bindListViewModel(ListViewModel listViewModel);
}
