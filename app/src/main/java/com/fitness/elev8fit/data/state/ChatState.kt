package com.fitness.elev8fit.data.state

import com.fitness.elev8fit.domain.model.chat.Message

data class ChatState(
    val messages: List<Message> = emptyList(),
    val isLoading: Boolean = false,
    val isUploading: Boolean = false,
    val error: String? = null,
    val chatInitialized: Boolean = false
)