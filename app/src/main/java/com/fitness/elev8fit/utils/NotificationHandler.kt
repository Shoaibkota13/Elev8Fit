package com.fitness.elev8fit.utils

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import androidx.core.app.NotificationCompat
import com.fitness.elev8fit.MainActivity
import com.fitness.elev8fit.R
import kotlin.random.Random

class NotificationHandler (private val context: Context) {
    private val notificationManager = context.getSystemService(NotificationManager::class.java)
    private val notificationChannelID = "notification_channel_id"

    fun showSimpleNotification(chatroomId:String) {
        //creating an intent to launch notification
        val intent = Intent(context,MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            putExtra("chatroomId",chatroomId)
        }

        val pendingIntent: PendingIntent = PendingIntent.getActivity(
            context,
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(context, notificationChannelID)
            .setContentTitle("Simple Notification")
            .setContentText("Message or text with notification")
            .setSmallIcon(R.drawable.logo)
            .setLargeIcon(
                BitmapFactory.decodeResource(
                    context.resources,
                    R.drawable.chat
                )
            )
            .setPriority(NotificationManager.IMPORTANCE_HIGH)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText("Excercise Kar liya abhi thoda masti karlo")
            )
            .build()  // finalizes the creation

        notificationManager.notify(Random.nextInt(), notification)
    }
}