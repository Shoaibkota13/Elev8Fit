package com.fitness.elev8fit

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import androidx.annotation.RequiresApi
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class Elev8FitApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        // Initialize the notification channel
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            setupNotificationChannel()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setupNotificationChannel() {
        val notificationChannel = NotificationChannel(
            "notification_channel_id",
            "General Notifications",
            NotificationManager.IMPORTANCE_HIGH
        ).apply {
            description = "This channel is used for general notifications."
        }

//        val notificationManager = getSystemService(NotificationManager::class.java)
//        notificationManager?.createNotificationChannel(notificationChannel)


        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        // Setting up the channel
        notificationManager.createNotificationChannel(notificationChannel)
    }
}
