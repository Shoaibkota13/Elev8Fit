package com.fitness.elev8fit.presentation.activity.chat.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fitness.elev8fit.data.repository.authfirebaseimpl
import com.fitness.elev8fit.data.repository.chatrepoimpl
import com.fitness.elev8fit.presentation.activity.chat.domain.model.ChatRoom
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ChatRoomListViewModel @Inject constructor(
    private val authRepository: authfirebaseimpl,
    private val chatrepo : chatrepoimpl
) : ViewModel() {
    private val _chatRooms = MutableStateFlow<List<ChatRoom>>(emptyList())
    val chatRooms: StateFlow<List<ChatRoom>> = _chatRooms

    init {
        fetchChatRoomsForCoach()
    }

    private fun fetchChatRoomsForCoach() {
        viewModelScope.launch {
            chatrepo.fetchChatRoomsForCoach { chatRoomsList ->
                _chatRooms.value = chatRoomsList
            }
        }
    }
}