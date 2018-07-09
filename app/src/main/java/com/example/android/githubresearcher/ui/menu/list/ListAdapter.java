package com.example.android.githubresearcher.ui.menu.list;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.githubresearcher.R;
import com.example.android.githubresearcher.ui.listdetail.ListDetailActivity;
import com.example.android.githubresearcher.vo.UserList;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ListViewHolder> {

    private List<UserList> userLists = new ArrayList<>();
    private Activity activity;
    private Context context;

    public ListAdapter(Activity activity, Context context) {
        this.activity = activity;
        this.context = context;
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_card, parent, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {

        UserList userList = userLists.get(position);
        holder.listCard.setOnClickListener(v -> {
            Intent intent = new Intent(activity, ListDetailActivity.class);
            intent.putExtra("List",userList);
            context.startActivity(intent);
        });
        holder.listName.setText(userList.name);
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

    class ListViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.card)
        CardView listCard;
        @BindView(R.id.name)
        TextView listName;

        ListViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
