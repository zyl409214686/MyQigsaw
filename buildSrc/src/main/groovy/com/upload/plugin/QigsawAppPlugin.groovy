package com.upload.plugin

import com.iqiyi.qigsaw.buildtool.gradle.QigsawAppBasePlugin
import com.iqiyi.qigsaw.buildtool.gradle.upload.SplitApkUploaderInstance
import com.upload.plugin.upload.SampleSplitApkUploader
import com.upload.plugin.extension.SplitUploadExtension
import org.gradle.api.Project

public class QigsawAppPlugin extends QigsawAppBasePlugin {

    @Override
    void apply(Project project) {
        super.apply(project)
        print("test")
        SplitApkUploaderInstance.set(new SampleSplitApkUploader())
        project.extensions.create("splitUpload", SplitUploadExtension)
    }
}
