package com.example.android.githubresearcher.ui.menu.repositories;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.android.githubresearcher.R;
import com.example.android.githubresearcher.di.Injectable;
import com.example.android.githubresearcher.vo.User;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RepositoriesFragment extends Fragment implements Injectable {

    private static final String USER_KEY = "user";

    @BindView(R.id.imageUser)
    ImageView userAvatar;

    @BindView(R.id.nomeUser)
    TextView nomeUser;

    @BindView(R.id.loginUser)
    TextView loginUser;

    @BindView(R.id.bioUser)
    TextView bioUser;

    @BindView(R.id.repoList)
    RecyclerView repoList;

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    private RepositoriesViewModel repositoriesViewModel;

    private RepositoriesAdapter repositoriesAdapter;

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

        repositoriesAdapter = new RepositoriesAdapter();
        repositoriesAdapter.setContext(getContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        repoList.setLayoutManager(linearLayoutManager);
        repoList.setAdapter(repositoriesAdapter);

        User user = (User) getArguments().getSerializable(USER_KEY);
        nomeUser.setText(user.name);
        loginUser.setText(user.login);
        bioUser.setText(user.bio);
        Glide.with(getContext())
                .load(user.avatar)
                .into(userAvatar);

        populateRepositories(user.login);
    }

    public void populateRepositories(String login) {
        repositoriesViewModel.loadRepos(login);
        repositoriesViewModel.getRepos().observe(this, repoResource -> {
            if (repoResource != null) {
                switch (repoResource.status){
                    case SUCCESS:
                        if (repoResource.data != null && repoResource.data.size() > 0) {
                            repositoriesAdapter.addRepositories(repoResource.data);
                        } else {
                            Toast.makeText(
                                    getActivity(),
                                    "Não há repositórios.",
                                    Toast.LENGTH_LONG).show();
                        }
                        break;
                    case ERROR:
                        Toast.makeText(
                                getActivity(),
                                "Erro ao carregar repositórios.",
                                Toast.LENGTH_LONG).show();
                        break;
                }
            } else {
                Toast.makeText(
                        getActivity(),
                        "Erro ao carregar repositórios.",
                        Toast.LENGTH_LONG).show();
            }
        });
    }
}
