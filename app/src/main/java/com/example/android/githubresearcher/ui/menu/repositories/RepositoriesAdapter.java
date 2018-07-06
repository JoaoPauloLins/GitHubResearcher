package com.example.android.githubresearcher.ui.menu.repositories;

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
import com.example.android.githubresearcher.vo.Repo;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RepositoriesAdapter extends RecyclerView.Adapter<RepositoriesAdapter.RepositoriesViewHolder> {

    private List<Repo> repositories = new ArrayList<>();
    private Context context;

    @NonNull
    @Override
    public RepositoriesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.repo_card, parent, false);
        return new RepositoriesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RepositoriesViewHolder holder, int position) {

        Repo repo = repositories.get(position);
        String repoPath = repo.userLogin+"/"+repo.name;
        holder.repoCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://github.com/"+repoPath;
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                context.startActivity(intent);
            }
        });
        holder.repoName.setText(repoPath);
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

    public void setContext(Context context) {
        this.context = context;
    }

    class RepositoriesViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.repoCard)
        CardView repoCard;
        @BindView(R.id.repoName)
        TextView repoName;

        RepositoriesViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
