package com.tyh.myqigsaw.reporter;

import android.content.Context;

import com.iqiyi.android.qigsaw.core.splitreport.DefaultSplitLoadReporter;
import com.iqiyi.android.qigsaw.core.splitreport.SplitBriefInfo;
import com.iqiyi.android.qigsaw.core.splitreport.SplitLoadError;

import java.util.List;

import androidx.annotation.NonNull;

public class SampleSplitLoadReporter extends DefaultSplitLoadReporter {

    public SampleSplitLoadReporter(Context context) {
        super(context);
    }

    @Override
    public void onLoadOK(String processName, @NonNull List<SplitBriefInfo> loadOKSplits, long cost) {
        super.onLoadOK(processName, loadOKSplits, cost);
    }

    @Override
    public void onLoadFailed(String processName, @NonNull List<SplitBriefInfo> loadOKSplits, @NonNull List<SplitLoadError> loadErrorSplits, long cost) {
        super.onLoadFailed(processName, loadOKSplits, loadErrorSplits, cost);
    }
}
