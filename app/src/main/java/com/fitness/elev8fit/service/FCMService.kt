package com.fitness.elev8fit.service

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService

class FCMService : FirebaseMessagingService() {
    
    override fun onNewToken(token: String) {
        super.onNewToken(token)
        updateFCMToken(token)
    }

    companion object {
        fun updateFCMToken(token: String) {
            val currentUser = FirebaseAuth.getInstance().currentUser ?: return
            FirebaseFirestore.getInstance()
                .collection("Users")
                .document(currentUser.uid)
                .update("fcmtoken", token)
                .addOnFailureListener { e ->
                    Log.e("FCMService", "Failed to update FCM token", e)
                }
        }

        // Call this when user creates account or signs in
        fun getFCMToken() {
            FirebaseMessaging.getInstance().token
                .addOnSuccessListener { token ->
                    updateFCMToken(token)
                }
                .addOnFailureListener { e ->
                    Log.e("FCMService", "Failed to get FCM token", e)
                }
        }
    }
} 