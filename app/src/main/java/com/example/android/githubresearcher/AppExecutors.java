package com.example.android.githubresearcher;

import java.util.concurrent.Executor;

public interface AppExecutors {

    Executor diskIO();
    Executor networkIO();
    Executor mainThread();
}
