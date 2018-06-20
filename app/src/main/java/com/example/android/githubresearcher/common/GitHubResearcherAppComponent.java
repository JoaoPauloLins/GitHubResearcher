package com.example.android.githubresearcher.common;

import android.app.Application;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;

@Singleton
@Component(modules = {
        AndroidInjectionModule.class,
        GitHubResearcherAppModule.class})
public interface GitHubResearcherAppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);
        GitHubResearcherAppComponent build();
    }

    void inject(GitHubResearcherApp gitHubResearcherApp);

    //UserRepository userRepository();
}
