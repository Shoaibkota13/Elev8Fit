package com.fitness.elev8fit.presentation.intent


sealed class ChatRoomListIntent {
    object FetchChatRooms : ChatRoomListIntent()
}
