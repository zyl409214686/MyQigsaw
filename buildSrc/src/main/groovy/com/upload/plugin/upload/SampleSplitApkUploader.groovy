package com.upload.plugin.upload

import com.iqiyi.qigsaw.buildtool.gradle.upload.SplitApkUploadException
import com.iqiyi.qigsaw.buildtool.gradle.upload.SplitApkUploader
import org.gradle.api.Project

class SampleSplitApkUploader implements SplitApkUploader {

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
        println("====Upload split " + splitName + " split apk file path: " + splitApk + " useTestEnv: " + useTestEnv)
        //todo::在这里实现上传逻辑
        return "http://qvyrhs9ma.hn-bkt.clouddn.com/apk/second_feature-release.apk?e=1625814928&attname=&token=yTsDAOK294MphgquNxStddfBqdRlaFXtXy_peHmE:Pd4FBxw8nl6dbmBsFiCz5gRSWMU="
    }
}
