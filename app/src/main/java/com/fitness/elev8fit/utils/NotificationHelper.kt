//package com.fitness.elev8fit.utils
//
//import com.google.firebase.firestore.FirebaseFirestore
//import com.google.gson.Gson
//import kotlinx.coroutines.CoroutineScope
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.launch
//import okhttp3.MediaType.Companion.toMediaType
//import okhttp3.OkHttpClient
//import okhttp3.Request
//import okhttp3.RequestBody.Companion.toRequestBody
//import java.io.IOException
//
//object NotificationHelper {
//    private const val COACH_ID = "oriw3fgPs4NqpN5BxfDb58Uq6532"
//    private const val FCM_API_URL = "https://fcm.googleapis.com/v1/projects/elev8fit-f75d5/messages:send"
//    private const val SERVER_KEY = "_VuXnDT_bQoySydCFGnVrtFcxFcOC9E6FeRY7rq7ESI"
//
//    private val client = OkHttpClient()
//    private val gson = Gson()
//    private val jsonMediaType = "application/json; charset=utf-8".toMediaType()
//
//    fun sendNotificationToCoach(
//        title: String,
//        message: String,
//        data: Map<String, String> = emptyMap()
//    ) {
//        FirebaseFirestore.getInstance()
//            .collection("Users")
//            .document(COACH_ID)
//            .get()
//            .addOnSuccessListener { document ->
//                val coachFcmToken = document.getString("fcmtoken")
//                if (!coachFcmToken.isNullOrEmpty()) {
//                    CoroutineScope(Dispatchers.IO).launch {
//                        sendFcmNotification(coachFcmToken, title, message, data)
//                    }
//                }
//            }
//    }
//
//    private fun sendFcmNotification(
//        token: String,
//        title: String,
//        message: String,
//        data: Map<String, String>
//    ) {
//        try {
//            val fcmMessage = buildFcmMessage(token, title, message, data)
//            val requestBody = gson.toJson(fcmMessage).toRequestBody(jsonMediaType)
//
//            val request = Request.Builder()
//                .url(FCM_API_URL)
//                .addHeader("Authorization", "Bearer $SERVER_KEY")
//                .addHeader("Content-Type", "application/json")
//                .post(requestBody)
//                .build()
//
//            client.newCall(request).execute().use { response ->
//                if (!response.isSuccessful) {
//                    throw IOException("Unexpected response code: ${response.code}")
//                }
//                // Handle successful response if needed
//            }
//        } catch (e: Exception) {
//            e.printStackTrace()
//            // Handle error appropriately
//        }
//    }
//
//    private fun buildFcmMessage(
//        token: String,
//        title: String,
//        message: String,
//        data: Map<String, String>
//    ): Map<String, Any> {
//        return mapOf(
//            "message" to mapOf(
//                "token" to token,
//                "notification" to mapOf(
//                    "title" to title,
//                    "body" to message
//                ),
//                "data" to data,
//                "android" to mapOf(
//                    "priority" to "high",
//                    "notification" to mapOf(
//                        "click_action" to "OPEN_CHAT_ACTIVITY",
//                        "channel_id" to "chat_notifications"
//                    )
//                )
//            )
//        )
//    }
//
//
//}