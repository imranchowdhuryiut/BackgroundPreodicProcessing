package com.example.imranchowdhury.backgroundpreodicprocessing

import android.arch.lifecycle.ViewModel
import androidx.work.WorkManager
import com.evernote.android.job.JobManager

class FileWriterViewModel: ViewModel() {
    private val mWorkManager : WorkManager = WorkManager.getInstance()
    private val mJobManager : JobManager = JobManager.instance()

    fun getWorkManagerInstance (): WorkManager {
        return mWorkManager
    }

    fun getJobManagerInstance() : JobManager {
        return mJobManager
    }
}