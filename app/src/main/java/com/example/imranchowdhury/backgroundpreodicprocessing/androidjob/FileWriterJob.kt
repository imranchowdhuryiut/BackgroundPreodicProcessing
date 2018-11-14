package com.example.imranchowdhury.backgroundpreodicprocessing.androidjob

import android.os.Environment
import android.util.Log
import com.evernote.android.job.Job
import java.io.File
import java.io.FileOutputStream
import java.io.PrintWriter
import java.text.SimpleDateFormat
import java.util.*


class FileWriterJob : Job() {

    override fun onRunJob(params: Params): Result {
        val isWorkDone = doFileWritingWork()
        if (isWorkDone) {
            return Result.SUCCESS
        }
        return Result.FAILURE
    }

    private fun doFileWritingWork(): Boolean {
        return try {
            val fileLocation = File(
                Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_DOWNLOADS), "")
            if (!fileLocation.exists()) {
                fileLocation.mkdirs()
            }
            val dateFormat = SimpleDateFormat("HH:mm:ss", Locale.ENGLISH)
            val file = File(fileLocation, "LogAndroidJob.txt")
            file.createNewFile()
            val printWriter = PrintWriter(FileOutputStream(file), true)
            printWriter.append("\nAndroid Job Completed Work at: " + dateFormat.format(Date()))
            printWriter.close()
            true
        } catch (exception: Exception) {
            Log.d("FileWriterJob", exception.message)
            false
        }
    }

}
