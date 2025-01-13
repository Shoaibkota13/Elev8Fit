package com.fitness.elev8fit.presentation.activity.chat.domain.repository

import com.fitness.elev8fit.presentation.activity.chat.domain.model.ChatRoom
import com.fitness.elev8fit.presentation.activity.chat.domain.model.Message

interface chatrepo {
    suspend fun sendMessageToChatRoom(chatRoomId: String, message: Message)
    suspend fun fetchChatRoomsForCoach(onChatRoomsFetched: (List<ChatRoom>) -> Unit)
    suspend fun initializeChatRoom(chatRoomId: String, currentUserId: String)
    suspend fun observeChatMessages(chatRoomId: String, onMessagesUpdated: (List<Message>) -> Unit)

}