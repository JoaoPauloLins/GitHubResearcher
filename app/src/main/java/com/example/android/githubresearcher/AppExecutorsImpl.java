package com.example.android.githubresearcher;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class AppExecutorsImpl implements AppExecutors{

    private final Executor diskIO;

    private final Executor networkIO;

    private final Executor mainThread;

    public AppExecutorsImpl(Executor diskIO, Executor networkIO, Executor mainThread) {
        this.diskIO = diskIO;
        this.networkIO = networkIO;
        this.mainThread = mainThread;
    }

    @Override
    public Executor diskIO() {
        return diskIO;
    }

    @Override
    public Executor networkIO() {
        return networkIO;
    }

    @Override
    public Executor mainThread() {
        return mainThread;
    }

    public static class MainThreadExecutor implements Executor {
        private Handler mainThreadHandler = new Handler(Looper.getMainLooper());
        @Override
        public void execute(@NonNull Runnable command) {
            mainThreadHandler.post(command);
        }
    }
}
