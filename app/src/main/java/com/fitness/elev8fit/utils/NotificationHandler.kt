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
            flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
            putExtra("chatroomId",chatroomId)
        }

        val pendingIntent: PendingIntent = PendingIntent.getActivity(
            context,
            Random.nextInt(), // Use random request code to avoid PendingIntent collision
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(context, notificationChannelID)
            .setContentTitle("New Message")
            .setContentText("You have a new message")
            .setSmallIcon(R.drawable.logo)
            .setLargeIcon(
                BitmapFactory.decodeResource(
                    context.resources,
                    R.drawable.chat
                )
            )
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()  // finalizes the creation

        notificationManager.notify(chatroomId.hashCode(), notification)
    }
}