package com.example.android.githubresearcher.ui.repodetail;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.githubresearcher.R;
import com.example.android.githubresearcher.di.Injectable;
import com.example.android.githubresearcher.util.OKLoader;
import com.example.android.githubresearcher.vo.Repo;
import com.example.android.githubresearcher.vo.RepoList;
import com.yydcdut.markdown.MarkdownTextView;
import com.yydcdut.markdown.callback.OnLinkClickCallback;
import com.yydcdut.markdown.callback.OnTodoClickCallback;
import com.yydcdut.markdown.loader.MDImageLoader;
import com.yydcdut.markdown.syntax.text.TextFactory;
import com.yydcdut.markdown.theme.ThemeSunburst;
import com.yydcdut.rxmarkdown.RxMDConfiguration;
import com.yydcdut.rxmarkdown.RxMarkdown;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

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

    @BindView(R.id.repo_readme)
    MarkdownTextView readme;

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    private RepoDetailViewModel repoDetailViewModel;

    private RepoDetailAdapter repoDetailAdapter;

    private String repoPath;

    private Toast mToast;

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

        populateReadme(repo.name);

        repoDetailAdapter = new RepoDetailAdapter(repo);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        checkboxList.setLayoutManager(linearLayoutManager);
        checkboxList.setAdapter(repoDetailAdapter);
        populateCheckBoxList(repo.id);
    }

    public void populateReadme(String repoPath) {

        repoDetailViewModel.loadReadme(repoPath);
        repoDetailViewModel.getReadme().observe(this, readmeResource -> {
            if (readmeResource != null) {
                switch (readmeResource.status){
                    case SUCCESS:
                        if (readmeResource.data != null) {
                            MDImageLoader mdImageLoader = new OKLoader(getContext());
                            readme.setVisibility(View.VISIBLE);
                            rxMarkdown(readme, readmeResource.data.content, mdImageLoader);
                        }
                }
            }
        });
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

    private void rxMarkdown(final TextView textView, String content, MDImageLoader imageLoader) {
        RxMDConfiguration rxMDConfiguration = new RxMDConfiguration.Builder(getContext())
                .setDefaultImageSize(50, 50)
                .setBlockQuotesLineColor(0xff33b5e5)
                .setHeader1RelativeSize(1.6f)
                .setHeader2RelativeSize(1.5f)
                .setHeader3RelativeSize(1.4f)
                .setHeader4RelativeSize(1.3f)
                .setHeader5RelativeSize(1.2f)
                .setHeader6RelativeSize(1.1f)
                .setHorizontalRulesColor(0xff99cc00)
                .setCodeBgColor(0xffff4444)
                .setTodoColor(0xffaa66cc)
                .setTodoDoneColor(0xffff8800)
                .setUnOrderListColor(0xff00ddff)
                .setRxMDImageLoader(imageLoader)
                .setHorizontalRulesHeight(1)
                .setLinkFontColor(Color.BLUE)
                .showLinkUnderline(false)
                .setTheme(new ThemeSunburst())
                .setOnLinkClickCallback(new OnLinkClickCallback() {
                    @Override
                    public void onLinkClicked(View view, String link) {
                        toast(link);
                    }
                })
                .setOnTodoClickCallback(new OnTodoClickCallback() {
                    @Override
                    public CharSequence onTodoClicked(View view, String line, int lineNumber) {
                        toast("line:" + line + "\n" + "line number:" + lineNumber);
                        return textView.getText();
                    }
                })
                .build();
        RxMarkdown.with(content, getContext())
                .config(rxMDConfiguration)
                .factory(TextFactory.create())
                .intoObservable()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<CharSequence>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(CharSequence charSequence) {
                        textView.setText(charSequence, TextView.BufferType.SPANNABLE);
                    }
                });
    }

    private void toast(String msg) {
        if (mToast == null) {
            mToast = Toast.makeText(getActivity(), "", Toast.LENGTH_SHORT);
        }
        mToast.setText(msg);
        mToast.show();
    }
}
