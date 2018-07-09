package com.example.android.githubresearcher.ui.listdetail;

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
import android.widget.TextView;

import com.example.android.githubresearcher.R;
import com.example.android.githubresearcher.di.Injectable;
import com.example.android.githubresearcher.vo.UserList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListDetailFragment extends Fragment implements Injectable {

    private static final String LIST_KEY = "list";

    @BindView(R.id.list_name)
    TextView listName;

    @BindView(R.id.repo_list)
    RecyclerView repoList;

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    private ListDetailViewModel listDetailViewModel;

    private ListDetailAdapter listDetailAdapter;

    public static ListDetailFragment create(UserList userList) {
        ListDetailFragment listDetailFragment = new ListDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(LIST_KEY, userList);
        listDetailFragment.setArguments(bundle);
        return listDetailFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_detail, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        listDetailViewModel = ViewModelProviders.of(this, viewModelFactory).get(ListDetailViewModel.class);

        listDetailAdapter = new ListDetailAdapter(getActivity(), getContext());

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        repoList.setLayoutManager(linearLayoutManager);
        repoList.setAdapter(listDetailAdapter);

        UserList userList = (UserList)getArguments().getSerializable(LIST_KEY);
        listName.setText(userList.name);

        populateRepositories(userList.id);
    }

    public void populateRepositories(int userListId) {

        listDetailViewModel.loadRepoList(userListId);
        listDetailViewModel.getRepos().observe(this, repos -> {
            if (repos != null && repos.size() > 0) {
                listDetailAdapter.addRepositories(repos);
            }
        });
    }
}
