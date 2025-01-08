package com.fitness.elev8fit.domain.interfaces

import com.fitness.elev8fit.domain.model.chat.ChatRoom
import kotlinx.coroutines.flow.Flow

interface chatting {
    fun observeChatRooms(coachId: String): Flow<List<ChatRoom>>
    suspend fun fetchUserName(userId: String): String
}