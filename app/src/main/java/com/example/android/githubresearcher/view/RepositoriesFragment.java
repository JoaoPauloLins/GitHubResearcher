package com.example.android.githubresearcher.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.githubresearcher.R;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RepositoriesFragment extends Fragment {

    @BindView(R.id.imageUser)
    ImageView userAvatar;

    @BindView(R.id.nomeUser)
    TextView nomeUser;

    @BindView(R.id.loginUser)
    TextView loginUser;

    @BindView(R.id.bioUser)
    TextView bioUser;

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
        nomeUser.setText(getArguments().getString("NOME"));
        loginUser.setText(getArguments().getString("LOGIN"));
        bioUser.setText(getArguments().getString("BIO"));
        Picasso.with(getContext()).load(getArguments().getString("AVATAR")).into(userAvatar);
    }
}
