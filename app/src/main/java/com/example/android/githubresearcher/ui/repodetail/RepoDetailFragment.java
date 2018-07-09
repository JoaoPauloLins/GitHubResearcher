package com.example.android.githubresearcher.ui.repodetail;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.android.githubresearcher.R;
import com.example.android.githubresearcher.di.Injectable;
import com.example.android.githubresearcher.vo.Repo;
import com.example.android.githubresearcher.vo.RepoList;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RepoDetailFragment extends Fragment implements Injectable {

    private static final String REPO_KEY = "repo";

    @BindView(R.id.repo_add_layout)
    FrameLayout repoAddLayout;

    @BindView(R.id.repo_title)
    TextView repoTitle;

    @BindView(R.id.repo_description)
    TextView repoDescription;

    @BindView(R.id.repo_language)
    TextView repoLanguage;

    @BindView(R.id.repo_created_at)
    TextView repoCreatedAt;

    @BindView(R.id.checkbox_list)
    RecyclerView checkboxList;

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    private RepoDetailViewModel repoDetailViewModel;

    private RepoDetailAdapter repoDetailAdapter;

    private String repoPath;

    public static RepoDetailFragment create(Repo repo) {
        RepoDetailFragment repoDetailFragment = new RepoDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(REPO_KEY, repo);
        repoDetailFragment.setArguments(bundle);
        return repoDetailFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_repo_detail, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        repoAddLayout.setVisibility(View.INVISIBLE);

        repoDetailViewModel = ViewModelProviders.of(this, viewModelFactory).get(RepoDetailViewModel.class);

        Repo repo = (Repo) getArguments().getSerializable(REPO_KEY);
        repoPath = repo.name;
        String language = "Language: "+repo.language;
        String createdAt = "Created at: "+repo.createdAt;
        repoTitle.setText(repoPath);
        repoDescription.setText(repo.description);
        repoLanguage.setText(language);
        repoCreatedAt.setText(createdAt);

        repoDetailAdapter = new RepoDetailAdapter(repo);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        checkboxList.setLayoutManager(linearLayoutManager);
        checkboxList.setAdapter(repoDetailAdapter);
        populateCheckBoxList(repo.id);
    }

    public void populateCheckBoxList(int repoId) {

        repoDetailViewModel.loadUserList(repoId);
        repoDetailViewModel.getUserLists().observe(this, userLists -> {
            if (userLists != null && userLists.size() > 0) {
                repoDetailAdapter.addUserList(userLists);
            }
        });
    }

    @OnClick(R.id.more)
    public void moreDetail(View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        String url = "https://github.com/"+repoPath;
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }

    @OnClick(R.id.add_list)
    public void addList(View view) {
        repoAddLayout.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.cancel)
    public void cancel(View view) {
        repoAddLayout.setVisibility(View.INVISIBLE);
    }

    @OnClick(R.id.save)
    public void save(View view) {
        List<RepoList> repoLists = repoDetailAdapter.getRepoLists();
        repoDetailViewModel.saveRepoLists(repoLists);
        repoAddLayout.setVisibility(View.INVISIBLE);
    }
}
