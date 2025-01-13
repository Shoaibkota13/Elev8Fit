package com.fitness.elev8fit.presentation.activity.chat.presentation.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fitness.elev8fit.data.repository.authfirebaseimpl
import com.fitness.elev8fit.data.repository.chatrepoimpl
import com.fitness.elev8fit.presentation.activity.chat.data.state.ChatState
import com.fitness.elev8fit.presentation.activity.chat.domain.model.Message
import com.fitness.elev8fit.presentation.activity.chat.presentation.intent.ChatIntent
import com.fitness.elev8fit.utils.S3Uploader
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val authRepository: authfirebaseimpl,
    private val chatrepo:chatrepoimpl,
    private val s3Uploader: S3Uploader
) : ViewModel() {
    private val _state = MutableStateFlow(ChatState())
    val state: StateFlow<ChatState> = _state.asStateFlow()

    private var selectedChatRoomId: String? = null

    fun processIntent(intent: ChatIntent) {
        when (intent) {
            is ChatIntent.SendMessage -> sendMessage(intent.userId, intent.message)
            is ChatIntent.UploadImage -> handleImageUpload(intent.uri, intent.userId)
            is ChatIntent.InitializeChat -> initializeChat(intent.chatRoomId)
        }
    }

    private fun initializeChat(chatRoomId: String) {
        selectedChatRoomId = chatRoomId
        val currentUser = FirebaseAuth.getInstance().currentUser ?: return

        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            try {
                chatrepo.initializeChatRoom(chatRoomId, currentUser.uid)
                _state.update { it.copy(chatInitialized = true, isLoading = false) }
              observeMessages(chatRoomId)
            } catch (e: Exception) {
                _state.update { it.copy(error = e.message, isLoading = false) }
            }
        }
    }

    private fun sendMessage(userId: String, messageText: String) {
        val chatRoomId = selectedChatRoomId ?: return

        viewModelScope.launch {
            try {
                val message = Message(
                    senderId = userId,
                    text = messageText,
                    timestamp = System.currentTimeMillis()
                )
                chatrepo.sendMessageToChatRoom(chatRoomId, message)

            } catch (e: Exception) {
                _state.update { it.copy(error = e.message) }
            }
        }
    }

    private fun observeMessages(chatRoomId: String) {
        viewModelScope.launch {
            try {
                chatrepo.observeChatMessages(chatRoomId) { messages ->
                    _state.update { currentState ->
                        currentState.copy(messages = messages)
                    }
                }
            } catch (e: Exception) {
                _state.update { it.copy(error = e.message) }
            }
        }
    }

    private fun handleImageUpload(imageUri: Uri, userId: String) {
        viewModelScope.launch {
            _state.update { it.copy(isUploading = true) }
            try {
                val imageUrl = s3Uploader.uploadImage(imageUri)
                val message = Message(
                    senderId = userId,
                    text = "",
                    timestamp = System.currentTimeMillis(),
                    imageUrl = imageUrl
                )
                selectedChatRoomId?.let { chatRoomId ->
                    chatrepo.sendMessageToChatRoom(chatRoomId, message)
                }
            } catch (e: Exception) {
                _state.update { it.copy(error = e.message) }
            } finally {
                _state.update { it.copy(isUploading = false) }
            }
        }
    }


}


