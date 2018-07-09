package com.example.android.githubresearcher.ui.menu.repositories;

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
import android.widget.TextView;

import com.example.android.githubresearcher.R;
import com.example.android.githubresearcher.ui.detail.DetailActivity;
import com.example.android.githubresearcher.vo.Repo;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RepositoriesAdapter extends RecyclerView.Adapter<RepositoriesAdapter.RepositoriesViewHolder> {

    private List<Repo> repositories = new ArrayList<>();
    private Activity activity;
    private Context context;

    public RepositoriesAdapter(Activity activity, Context context) {
        this.activity = activity;
        this.context = context;
    }

    @NonNull
    @Override
    public RepositoriesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_card, parent, false);
        return new RepositoriesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RepositoriesViewHolder holder, int position) {

        Repo repo = repositories.get(position);
        holder.repoCard.setOnClickListener(v -> {
            Intent intent = new Intent(activity, DetailActivity.class);
            intent.putExtra("Repo", repo);
            context.startActivity(intent);
        });
        holder.repoName.setText(repo.name);
    }

    @Override
    public int getItemCount() {
        return repositories.size();
    }

    public void addRepositories(List<Repo> repositories) {
        int position = this.repositories.size();
        this.repositories.addAll(repositories);
        notifyItemRangeInserted(position, repositories.size());
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
}
