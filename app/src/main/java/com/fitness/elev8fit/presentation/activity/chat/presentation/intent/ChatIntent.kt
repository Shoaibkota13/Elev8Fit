package com.fitness.elev8fit.presentation.activity.chat.presentation.intent

import android.net.Uri

sealed class ChatIntent {
    data class SendMessage(val userId: String, val message: String) : ChatIntent()
    data class UploadImage(val uri: Uri, val userId: String) : ChatIntent()
    data class InitializeChat(val chatRoomId: String) : ChatIntent()
}