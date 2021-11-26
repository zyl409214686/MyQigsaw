package com.tyh.myqigsaw;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;

import com.iqiyi.android.qigsaw.core.Qigsaw;
import com.iqiyi.android.qigsaw.core.SplitActivityLifecycleCallbacks;
import com.iqiyi.android.qigsaw.core.SplitConfiguration;
import com.iqiyi.android.qigsaw.core.splitload.SplitLoad;
import com.iqiyi.android.qigsaw.core.splitreport.SplitBriefInfo;
import com.tyh.myqigsaw.downloader.SampleDownloader;
import com.tyh.myqigsaw.reporter.SampleLogger;
import com.tyh.myqigsaw.reporter.SampleSplitInstallReporter;
import com.tyh.myqigsaw.reporter.SampleSplitLoadReporter;
import com.tyh.myqigsaw.reporter.SampleSplitUninstallReporter;
import com.tyh.myqigsaw.reporter.SampleSplitUpdateReporter;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.multidex.MultiDex;

/**
 * <p>@author : tangyanghai</p>
 * <p>@time : 2021/7/7</p>
 * <p>@for : </p>
 * <p></p>
 */
public class App extends Application {
    //Qigsaw多进程配置
    //private static final String[] workProcesses = {":qigsaw"};


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(base);
        //Using QigsawConfig.java to get same info about splits, for example
//        Log.d(TAG, String.format("There are %d splits in your app!", QigsawConfig.DYNAMIC_FEATURES.length));
        SplitConfiguration configuration = SplitConfiguration.newBuilder()
                .splitLoadMode(SplitLoad.MULTIPLE_CLASSLOADER)
//                .workProcesses(workProcesses)
//                .forbiddenWorkProcesses(forbiddenWorkProcesses)
                .logger(new SampleLogger())
                .verifySignature(true)
                .loadReporter(new SampleSplitLoadReporter(this))
                .installReporter(new SampleSplitInstallReporter(this))
                .uninstallReporter(new SampleSplitUninstallReporter(this))
                .updateReporter(new SampleSplitUpdateReporter(this))
                .obtainUserConfirmationDialogClass(SampleObtainUserConfirmationDialog.class)
                .build();
        Qigsaw.install(this, new SampleDownloader(), configuration);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Qigsaw.onApplicationCreated();
        //预加载  新版本没有了...
//        Qigsaw.preloadInstalledSplits(Arrays.asList(QigsawConfig.DYNAMIC_FEATURES));
        Qigsaw.registerSplitActivityLifecycleCallbacks(new SplitActivityLifecycleCallbacks() {

            @Override
            public void onSplitActivityCreated(@NonNull SplitBriefInfo briefInfo, @NonNull Activity activity, @Nullable Bundle savedInstanceState) {

            }

            @Override
            public void onSplitActivityStarted(@NonNull SplitBriefInfo briefInfo, @NonNull Activity activity) {

            }

            @Override
            public void onSplitActivityResumed(@NonNull SplitBriefInfo briefInfo, @NonNull Activity activity) {

            }

            @Override
            public void onSplitActivityPaused(@NonNull SplitBriefInfo briefInfo, @NonNull Activity activity) {

            }

            @Override
            public void onSplitActivityStopped(@NonNull SplitBriefInfo briefInfo, @NonNull Activity activity) {

            }

            @Override
            public void onSplitActivitySaveInstanceState(@NonNull SplitBriefInfo briefInfo, @NonNull Activity activity, @NonNull Bundle outState) {

            }

            @Override
            public void onSplitActivityDestroyed(@NonNull SplitBriefInfo briefInfo, @NonNull Activity activity) {

            }
        });
    }

    @Override
    public Resources getResources() {
        //将Application#onResource事件通知给qigsaw。
        Qigsaw.onApplicationGetResources(super.getResources());
        return super.getResources();
    }
}
