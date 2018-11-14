package com.example.imranchowdhury.backgroundpreodicprocessing.workmanager

import android.content.Context
import android.os.Environment
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import java.io.File
import java.io.FileOutputStream
import java.io.PrintWriter
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class FileWriteWorker
constructor(
    mContext: Context,
    mWorkerParameters: WorkerParameters
): Worker(mContext, mWorkerParameters) {
    override fun doWork(): Result {
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
            val file = File(fileLocation, "LogWorkManager.txt")
            file.createNewFile()
            val printWriter = PrintWriter(FileOutputStream(file), true)
            printWriter.append("\nWorkManager Completed Work at: " + dateFormat.format(Date()))
            printWriter.close()
            true
        } catch (exception: Exception) {
            Log.d("FileWriteWorker", exception.message)
            false
        }
    }

}