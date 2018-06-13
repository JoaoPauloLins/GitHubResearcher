package com.example.android.githubresearcher.common;

import com.example.android.githubresearcher.viewmodel.UserViewModel;

import dagger.Subcomponent;

@Subcomponent
public interface ViewModelSubComponent {

    @Subcomponent.Builder
    interface Builder {
        ViewModelSubComponent build();
    }

    UserViewModel userViewModel();
}
