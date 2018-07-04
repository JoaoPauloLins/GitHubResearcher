package com.example.android.githubresearcher.di;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.example.android.githubresearcher.ui.login.LoginViewModel;
import com.example.android.githubresearcher.ui.menu.RepositoriesViewModel;
import com.example.android.githubresearcher.viewmodel.GithubViewModelFactory;

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

//    @Binds
//    abstract ViewModelProvider.Factory bindViewModelFactory(GithubViewModelFactory factory);
}
