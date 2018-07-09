package com.example.android.githubresearcher.ui.detail;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
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

public class DetailFragment extends Fragment implements Injectable {

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

    private DetailViewModel detailViewModel;

    private DetailAdapter detailAdapter;

    private String repoPath;

    public static DetailFragment create(Repo repo) {
        DetailFragment detailFragment = new DetailFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(REPO_KEY, repo);
        detailFragment.setArguments(bundle);
        return detailFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        repoAddLayout.setVisibility(View.INVISIBLE);

        detailViewModel = ViewModelProviders.of(this, viewModelFactory).get(DetailViewModel.class);

        Repo repo = (Repo) getArguments().getSerializable(REPO_KEY);
        repoPath = repo.name;
        String language = "Language: "+repo.language;
        String createdAt = "Created at: "+repo.createdAt;
        repoTitle.setText(repoPath);
        repoDescription.setText(repo.description);
        repoLanguage.setText(language);
        repoCreatedAt.setText(createdAt);

        detailAdapter = new DetailAdapter(repo);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        checkboxList.setLayoutManager(linearLayoutManager);
        checkboxList.setAdapter(detailAdapter);
        populateCheckBoxList(repo.id);
    }

    public void populateCheckBoxList(int repoId) {

        detailViewModel.loadUserList(repoId);
        detailViewModel.getUserLists().observe(this, userLists -> {
            if (userLists != null) {
                if (userLists.size() > 0) {
                    detailAdapter.addUserList(userLists);
                }
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
        List<RepoList> repoLists = detailAdapter.getRepoLists();
        detailViewModel.saveRepoLists(repoLists);
        repoAddLayout.setVisibility(View.INVISIBLE);
    }
}
