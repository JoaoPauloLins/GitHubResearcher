package com.example.android.githubresearcher.ui.repodetail;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.example.android.githubresearcher.R;
import com.example.android.githubresearcher.vo.Repo;
import com.example.android.githubresearcher.vo.RepoList;
import com.example.android.githubresearcher.vo.UserList;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RepoDetailAdapter extends RecyclerView.Adapter<RepoDetailAdapter.ListViewHolder>{

    private List<RepoList> repoLists = new ArrayList<>();
    private List<UserList> userLists = new ArrayList<>();
    private Repo repo;

    public RepoDetailAdapter(Repo repo) {
        this.repo = repo;
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.intem_checkbox, parent, false);
        return new RepoDetailAdapter.ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {

        UserList userList = userLists.get(position);
        holder.itemCheckbox.setOnClickListener(v -> {
            boolean checked = ((CheckBox) v).isChecked();
            RepoList repoList = new RepoList(userList.id, repo.id);
            if (checked) {
                repoLists.add(repoList);
            } else {
                if (repoLists.contains(repoList)) {
                    repoLists.remove(repoList);
                }
            }
        });
        holder.itemCheckbox.setText(userList.name);
    }

    @Override
    public int getItemCount() {
        return userLists.size();
    }

    public void addUserList(List<UserList> userLists) {
        this.userLists.clear();
        notifyDataSetChanged();
        int position = this.userLists.size();
        this.userLists.addAll(userLists);
        notifyItemRangeInserted(position, userLists.size());
    }

    public List<RepoList> getRepoLists() {
        return repoLists;
    }

    public Repo getRepo() {
        return repo;
    }

    class ListViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.checkbox_item)
        CheckBox itemCheckbox;

        ListViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
