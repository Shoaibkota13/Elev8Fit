package com.fitness.elev8fit.domain.model.chat

//
//data class Message(
//    val messageId: String = "",      // Unique ID for the message
//    val senderId: String = "",       // ID of the user who sent the message
//    val receiverId: String = "",     // ID of the receiver (coach or user)
//    val text: String = "",           // Message text content
//    val timestamp: Long = 0L,        // Timestamp of the message
//    val imageUrl: String? = null     // Optional URL for an attached image
//)


data class ChatRoom(
    val id: String = "",
    val name: String = "",
    val username :String=""
)

data class Message(
    val text: String = "",
    val senderId: String = "",
    val timestamp: Long = 0L,
    val imageUrl: String? = null  // Added for image support
)
