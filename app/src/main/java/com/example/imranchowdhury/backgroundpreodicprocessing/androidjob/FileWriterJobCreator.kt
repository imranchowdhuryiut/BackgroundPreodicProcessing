package com.example.imranchowdhury.backgroundpreodicprocessing.androidjob

import com.evernote.android.job.Job
import com.evernote.android.job.JobCreator

class FileWriterJobCreator : JobCreator {
    override fun create(tag: String): Job? {
        return FileWriterJob()
    }

}
