package com.upload.plugin.upload

import com.iqiyi.qigsaw.buildtool.gradle.upload.SplitApkUploadException
import com.iqiyi.qigsaw.buildtool.gradle.upload.SplitApkUploader
import org.gradle.api.Project

class SampleSplitApkUploader implements SplitApkUploader {


    public static final String FEATURE_FIRST = "https://eepaas-public.s3.cn-north-1.jdcloud-oss.com/qigsaw_feature-master.apk"
    public static final String FEATURE_SECOND = "https://eepaas-public.s3.cn-north-1.jdcloud-oss.com/second_feature-master.apk"

    @Override
    String uploadSync(Project appProject, File splitApk, String splitName) throws SplitApkUploadException {
        List<String> testOnly = appProject.extensions.splitUpload.testOnly
        boolean useTestEnv = appProject.extensions.splitUpload.useTestEnv
        if (useTestEnv) {
            return uploadSplitApk(splitApk, splitName, true)
        } else {
            return uploadSplitApk(splitApk, splitName, usingTestEnvAnyWay(testOnly, splitName))
        }
    }


    static boolean usingTestEnvAnyWay(List<String> testOnly, String splitName) {
        return testOnly != null && testOnly.contains(splitName)
    }

    /**
     * Implement this method to upload split apks to your own server.
     */
    static String uploadSplitApk(File splitApk, String splitName, boolean useTestEnv) {
        println("====Upload split " + splitName + " split apk file path: " + splitApk + " useTestEnv: " + useTestEnv + " isExit:"+splitApk.exists())
        switch (splitName){
            case "qigsaw_feature":
                return FEATURE_FIRST
            case "second_feature":
                return FEATURE_SECOND
        }
        //todo::在这里实现上传逻辑
        return FEATURE_FIRST
    }
}
