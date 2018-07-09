package com.example.android.githubresearcher.ui.menu.list;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.githubresearcher.R;
import com.example.android.githubresearcher.di.Injectable;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ListFragment extends Fragment implements Injectable {

    private static final String USERID_KEY = "userId";

    @BindView(R.id.list_wrapper)
    TextInputLayout listWrapper;

    @BindView(R.id.list_edit)
    EditText userListName;

    @BindView(R.id.create_information)
    TextView information;

    @BindView(R.id.list)
    RecyclerView list;

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    private ListViewModel listViewModel;

    private ListAdapter listAdapter;

    private int userId;

    public static ListFragment create(int userId) {
        ListFragment listFragment = new ListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(USERID_KEY, userId);
        listFragment.setArguments(bundle);
        return listFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        listWrapper.setHint("New list name");

        listViewModel = ViewModelProviders.of(this, viewModelFactory).get(ListViewModel.class);

        listAdapter = new ListAdapter(getContext());

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        list.setLayoutManager(linearLayoutManager);
        list.setAdapter(listAdapter);

        userId = getArguments().getInt(USERID_KEY);
        populateUserList(userId);
    }

    public void populateUserList(int userId) {

        listViewModel.loadUserList(userId);
        listViewModel.getUserList().observe(this, userLists -> {
            if (userLists != null) {
                if (userLists.size() > 0) {
                    information.setVisibility(View.INVISIBLE);
                    listAdapter.addUserList(userLists);
                } else {
                    information.setVisibility(View.VISIBLE);
                }
            } else {
                Toast.makeText(
                        getActivity(),
                        "Erro ao carregar suas listas.",
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    @OnClick(R.id.list_button)
    public void addUserList() {
        String name = userListName.getText().toString();
        listViewModel.saveUserList(userId, name);
    }
}
