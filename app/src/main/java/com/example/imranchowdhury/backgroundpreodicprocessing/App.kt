package com.example.imranchowdhury.backgroundpreodicprocessing

import android.app.Application
import com.evernote.android.job.JobManager
import com.example.imranchowdhury.backgroundpreodicprocessing.androidjob.FileWriterJobCreator

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        JobManager.create(this).addJobCreator(FileWriterJobCreator())
    }
}