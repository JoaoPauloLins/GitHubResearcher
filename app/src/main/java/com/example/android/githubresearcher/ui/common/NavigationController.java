package com.example.android.githubresearcher.ui.common;

import android.support.v4.app.FragmentManager;

import com.example.android.githubresearcher.LoginActivity;
import com.example.android.githubresearcher.R;
import com.example.android.githubresearcher.ui.login.LoginFragment;
import com.example.android.githubresearcher.ui.login.LoginFragment;

import javax.inject.Inject;

public class NavigationController {
    private final int containerId;
    private final FragmentManager fragmentManager;
    @Inject
    public NavigationController(LoginActivity loginActivity) {
        this.containerId = R.id.container;
        this.fragmentManager = loginActivity.getSupportFragmentManager();
    }

    public void navigateToLogin() {
        LoginFragment loginFragment = new LoginFragment();
        fragmentManager.beginTransaction()
                .replace(containerId, loginFragment)
                .commitAllowingStateLoss();
    }

//    public void navigateToUser(String id) {
//        String tag = "user" + "/" + id;
//        LoginFragment userFragment = LoginFragment.create(id);
//        fragmentManager.beginTransaction()
//                .replace(containerId, userFragment, tag)
//                .addToBackStack(null)
//                .commitAllowingStateLoss();
//    }
}
