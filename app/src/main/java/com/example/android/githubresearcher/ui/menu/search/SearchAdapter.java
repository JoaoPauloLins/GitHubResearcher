package com.example.android.githubresearcher.ui.menu.search;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.githubresearcher.R;
import com.example.android.githubresearcher.ui.detail.DetailActivity;
import com.example.android.githubresearcher.vo.Repo;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchAdapter extends RecyclerView.Adapter {

    private List<Repo> repositories = new ArrayList<>();
    private Activity activity;
    private Context context;

    private static final int VIEW_REPO = 1;
    private static final int VIEW_LOAD = 0;

    public SearchAdapter(Activity activity, Context context) {
        this.activity = activity;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder viewHolder;
        View view;

        if (viewType == VIEW_REPO) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_card, parent, false);
            viewHolder = new RepositoriesViewHolder(view);
        }
        else {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.search_progress_bar, parent, false);
            viewHolder = new LoadViewHolder(view);
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof RepositoriesViewHolder) {
            RepositoriesViewHolder repositoriesViewHolder = (RepositoriesViewHolder) holder;
            Repo repo = repositories.get(position);
            repositoriesViewHolder.repoCard.setOnClickListener(v -> {
                Intent intent = new Intent(activity, DetailActivity.class);
                intent.putExtra("Repo", repo);
                context.startActivity(intent);
            });
            repositoriesViewHolder.repoName.setText(repo.name);
        }
        else {
            LoadViewHolder loadViewHolder = (LoadViewHolder) holder;
            loadViewHolder.progressBar.setIndeterminate(true);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return repositories.get(position) != null ? VIEW_REPO : VIEW_LOAD;
    }

    @Override
    public int getItemCount() {
        return repositories.size();
    }

    public void addRepositories(List<Repo> repositories){
        if (repositories != null) {
            int position = this.repositories.size();
            this.repositories.addAll(repositories);
            notifyItemRangeInserted(position, repositories.size());
        }
    }

    public void removeRepositories() {
        repositories.clear();
        notifyDataSetChanged();
    }

    public void addLoad(){
        int position = repositories.size();
        if(position == 0 || repositories.get(position -1) != null){
            repositories.add(null);
            notifyItemRangeInserted(position, 1);
        }
    }

    public void removeLoad(){
        repositories.remove(null);
        notifyDataSetChanged();
    }

    class RepositoriesViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.card)
        CardView repoCard;
        @BindView(R.id.name)
        TextView repoName;

        RepositoriesViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public class LoadViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.search_load)
        ProgressBar progressBar;

        LoadViewHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
