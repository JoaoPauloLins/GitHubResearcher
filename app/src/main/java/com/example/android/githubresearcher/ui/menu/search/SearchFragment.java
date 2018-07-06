package com.example.android.githubresearcher.ui.menu.search;

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
import android.widget.Toast;

import com.example.android.githubresearcher.R;
import com.example.android.githubresearcher.di.Injectable;
import com.example.android.githubresearcher.util.EndlessRecyclerViewScrollListener;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SearchFragment extends Fragment implements Injectable {

    @BindView(R.id.search_wrapper)
    TextInputLayout searchWrapper;

    @BindView(R.id.search_query)
    EditText searchQuery;

    @BindView(R.id.repoList)
    RecyclerView repoList;

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    private String query;

    private SearchViewModel searchViewModel;

    private SearchAdapter searchAdapter;

    private EndlessRecyclerViewScrollListener scrollListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        searchWrapper.setHint("Repositories");

        searchViewModel = ViewModelProviders.of(this, viewModelFactory).get(SearchViewModel.class);

        searchAdapter = new SearchAdapter();
        searchAdapter.setContext(getContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        repoList.setLayoutManager(linearLayoutManager);
        repoList.setAdapter(searchAdapter);

        scrollListener = new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page) {
                addRepos(query, page);
            }
        };
        repoList.addOnScrollListener(scrollListener);
    }

    public void addRepos(String query, int page) {
        searchViewModel.searchRepos(query, page, 10);
        searchViewModel.getResult().observe(this, repoResource -> {
            if (repoResource != null) {
                switch (repoResource.status){
                    case LOADING:
                        searchAdapter.addLoad();
                        break;
                    case SUCCESS:
                        if (repoResource.data != null && repoResource.data.size() > 0) {
                            searchAdapter.removeLoad();
                            searchAdapter.addRepositories(repoResource.data);
                            scrollListener.setLoaded();
                        } else {
                            Toast.makeText(
                                    getActivity(),
                                    "Não há repositórios para esta pesquisa.",
                                    Toast.LENGTH_LONG).show();
                        }
                        break;
                    case ERROR:
                        Toast.makeText(
                                getActivity(),
                                "Erro ao pesquisar repositórios.",
                                Toast.LENGTH_LONG).show();
                        break;
                }
            } else {
                Toast.makeText(
                        getActivity(),
                        "Erro ao pesquisar repositórios.",
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    @OnClick(R.id.search_button)
    public void search() {
        searchAdapter.removeRepositories();
        query = searchQuery.getText().toString();
        addRepos(query,1);
    }
}
