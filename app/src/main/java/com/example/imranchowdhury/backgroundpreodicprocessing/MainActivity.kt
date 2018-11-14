package com.example.imranchowdhury.backgroundpreodicprocessing

import android.Manifest
import android.arch.lifecycle.ViewModelProviders
import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import androidx.work.PeriodicWorkRequest
import com.evernote.android.job.JobRequest
import com.example.imranchowdhury.backgroundpreodicprocessing.workmanager.FileWriteWorker
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    private lateinit var mViewModel: FileWriterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mViewModel = ViewModelProviders.of(this).get(FileWriterViewModel::class.java)
        checkStorageAccessPermission()
    }

    private fun checkStorageAccessPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            askStoragePermission()
        } else {
            startPreodicProcessing()
        }
    }

    private fun askStoragePermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ),
            948
        )
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {

        if (requestCode == 948) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startPreodicProcessing()
            }
        }
    }

    private fun startPreodicProcessing() {
        startWorkManager()
        startAndroidJob()
    }

    private fun startAndroidJob() {
        val fileWriteJobRequest = JobRequest
            .Builder("Tag")
            .setPeriodic(TimeUnit.MINUTES.toMillis(30))
            .build()

        mViewModel.getJobManagerInstance().schedule(fileWriteJobRequest)
    }

    private fun startWorkManager() {
        val fileWriterWorkRequest = PeriodicWorkRequest.Builder(
            FileWriteWorker::class.java,
            30,
            TimeUnit.MINUTES
        ).build()

        mViewModel.getWorkManagerInstance().enqueue(fileWriterWorkRequest)
    }
}
