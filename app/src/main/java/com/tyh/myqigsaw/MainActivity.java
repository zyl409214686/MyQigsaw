package com.tyh.myqigsaw;

import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.play.core.splitinstall.SplitInstallManager;
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory;
import com.google.android.play.core.splitinstall.SplitInstallRequest;
import com.google.android.play.core.splitinstall.SplitInstallSessionState;
import com.google.android.play.core.splitinstall.SplitInstallStateUpdatedListener;
import com.google.android.play.core.splitinstall.model.SplitInstallSessionStatus;
import com.google.android.play.core.tasks.OnFailureListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {
    private static final String FISRT_FEATURE_ACTIVITY = "com.tyh.qigsaw_feature.FeatureActivity";
    private static final String SECOND_FEATURE_ACTIVITY = "com.tyh.second_feature.SecondFeatureActivity";
    private SplitInstallManager installManager;
    private SplitInstallStateUpdatedListener myListener = new SplitInstallStateUpdatedListener() {

        @Override
        public void onStateUpdate(SplitInstallSessionState state) {
            boolean multiInstall = state.moduleNames().size() > 1;
            if (state.status() == SplitInstallSessionStatus.INSTALLED) {
                for (String moduleName : state.moduleNames()) {
                    onSuccessfullyLoad(moduleName, !multiInstall);
                    toastAndLog(moduleName + " has been installed!!!!!");
                }
            } else if (state.status() == SplitInstallSessionStatus.REQUIRES_USER_CONFIRMATION) {
                try {
                    startIntentSender(state.resolutionIntent().getIntentSender(), null, 0, 0, 0);
                } catch (IntentSender.SendIntentException e) {
                    e.printStackTrace();
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        installManager = SplitInstallManagerFactory.create(this);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();
        installManager.registerListener(myListener);
    }

    @Override
    protected void onPause() {
        super.onPause();
        installManager.unregisterListener(myListener);
    }

    public void goFeature(View view) {
        startQigsawInstaller("qigsaw_feature");
    }

    public void goSecond(View view) {
        startQigsawInstaller("second_feature");
    }

    //1.开始安装插件
    private void startQigsawInstaller(String moduleName) {
        Intent intent = new Intent(this, QigsawInstallerActivity.class);
        ArrayList<String> moduleNames = new ArrayList<>();
        moduleNames.add(moduleName);
        intent.putStringArrayListExtra(QigsawInstallerActivity.KEY_MODULE_NAMES, moduleNames);
        startActivityForResult(intent, QigsawInstallerActivity.INSTALL_REQUEST_CODE);
    }

    //2.安装插件返回
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == QigsawInstallerActivity.INSTALL_REQUEST_CODE) {
            switch (resultCode) {
                case RESULT_OK:
                    if (data != null) {
                        ArrayList<String> moduleNames = data.getStringArrayListExtra(QigsawInstallerActivity.KEY_MODULE_NAMES);
                        if (moduleNames != null && moduleNames.size() == 1) {
                            loadAndLaunchModule(moduleNames.get(0));
                        }
                    }
                    break;
                case RESULT_CANCELED:
                    break;
                default:
                    break;
            }
        }
    }

    //3.开始加载插件
    private void loadAndLaunchModule(String name) {
        updateProgressMessage("Loading module " + name);
        if (installManager.getInstalledModules().contains(name)) {
            updateProgressMessage("Already installed!");
            onSuccessfullyLoad(name, true);
            return;
        }
        SplitInstallRequest request = SplitInstallRequest.newBuilder().addModule(name).build();
        installManager.startInstall(request).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception e) {
                toastAndLog(e.getMessage());
            }
        });
        updateProgressMessage("Starting install for " + name);
    }

    //4.加载插件成功
    private void onSuccessfullyLoad(String moduleName, boolean launch) {
        if (launch) {
            switch (moduleName) {
                case "second_feature":
                    launchActivity(SECOND_FEATURE_ACTIVITY);
                    break;
                case "qigsaw_feature":
                    launchActivity(FISRT_FEATURE_ACTIVITY);
                    break;
            }
        }
    }

    //5.跳转到指定Activity
    private void launchActivity(String fullActivityName) {
        Intent intent = new Intent();
        intent.setClassName(getPackageName(), fullActivityName);
        startActivity(intent);
    }

    private void toastAndLog(String message) {
        Log.e("===", message);
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }


    private void updateProgressMessage(String message) {
     /*   if (progressbarGroups.getVisibility() != View.VISIBLE) {
            displayProgress();
        }
        progressText.setText(message);*/
        Log.e("===", message);
    }

    public void uninstallFeature(View view) {

        if (installManager.getInstalledModules().contains("qigsaw_feature")) {
            ArrayList arrayList = new ArrayList();
            arrayList.add("qigsaw_feature");
            installManager.deferredUninstall(arrayList);
            Toast.makeText(MainActivity.this, "qigsaw_feature uninstall success", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(MainActivity.this, "qigsaw_feature is not exit", Toast.LENGTH_SHORT).show();
        }
    }

    public void uninstallSecond(View view) {
        if (installManager.getInstalledModules().contains("second_feature")) {
            ArrayList arrayList = new ArrayList();
            arrayList.add("second_feature");
            installManager.deferredUninstall(arrayList);
            Toast.makeText(MainActivity.this, "second_feature uninstall success", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(MainActivity.this, "second_feature is not exit", Toast.LENGTH_SHORT).show();
        }
    }
}