package com.fitness.elev8fit.presentation.activity.chat.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.fitness.elev8fit.presentation.activity.chat.ChatRoomListViewModel
import com.fitness.elev8fit.presentation.activity.chat.ChatViewModel
import com.google.firebase.auth.FirebaseAuth

@Composable
fun ChatMain() {
    val currentUser = FirebaseAuth.getInstance().currentUser
    val isCoach = currentUser?.uid == "oriw3fgPs4NqpN5BxfDb58Uq6532"
    var selectedChatRoomId by remember { mutableStateOf<String?>(null) }
    var showChat by remember { mutableStateOf(false) }

    if (isCoach) {
        // Coach view - show list of chat rooms first
        if (selectedChatRoomId == null) {
            val chatRoomListViewModel: ChatRoomListViewModel = hiltViewModel()
            ChatRoomListScreen(
                viewModel = chatRoomListViewModel,
                onChatRoomSelected = { chatRoomId ->
                    selectedChatRoomId = chatRoomId
                }
            )
        } else {
            val chatViewModel: ChatViewModel = hiltViewModel()
            CoachChatScreen(
                viewModel = chatViewModel,
                chatRoomId = selectedChatRoomId!!,
                onBackClick = { selectedChatRoomId = null }
            )
        }
    } else {
        // Regular user view
        if (!showChat) {
            // Show button to start chat
            // TODO: Add button UI here
//           // Auto-show chat for now, replace with button logic

            val chatViewModel: ChatViewModel = hiltViewModel()
            ChatScreen(
                viewModel = chatViewModel,
                chatRoomId = currentUser?.uid ?: "",
                onBackClick = { showChat = false }
            )
        }
    }
}


