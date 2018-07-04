package com.example.android.githubresearcher.ui.menu;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.android.githubresearcher.R;
import com.example.android.githubresearcher.vo.User;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RepositoriesFragment extends Fragment {

    private static final String USER_KEY = "user";

    @BindView(R.id.imageUser)
    ImageView userAvatar;

    @BindView(R.id.nomeUser)
    TextView nomeUser;

    @BindView(R.id.loginUser)
    TextView loginUser;

    @BindView(R.id.bioUser)
    TextView bioUser;

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    private RepositoriesViewModel repositoriesViewModel;

    public static RepositoriesFragment create(User user) {
        RepositoriesFragment repositoriesFragment = new RepositoriesFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(USER_KEY, user);
        repositoriesFragment.setArguments(bundle);
        return repositoriesFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_repositories, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        repositoriesViewModel = ViewModelProviders.of(this, viewModelFactory).get(RepositoriesViewModel.class);

        User user = (User) getArguments().getSerializable(USER_KEY);
        nomeUser.setText(user.name);
        loginUser.setText(user.login);
        bioUser.setText(user.bio);
        Glide.with(getContext())
                .load(user.avatar)
                .into(userAvatar);
    }
}
