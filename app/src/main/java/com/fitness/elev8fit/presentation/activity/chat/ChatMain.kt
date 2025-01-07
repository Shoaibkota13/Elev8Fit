package com.fitness.elev8fit.presentation.activity.chat

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.google.firebase.auth.FirebaseAuth


@Composable
fun ChatMain() {
    val currentUser = FirebaseAuth.getInstance().currentUser
    val context = LocalContext.current
    val isCoach = currentUser?.uid == "oriw3fgPs4NqpN5BxfDb58Uq6532"
    var selectedChatRoomId by remember { mutableStateOf<String?>(null) }
    var showChat by remember { mutableStateOf(false) }

    if (isCoach) {
        // Coach view - show list of chat rooms first
        if (selectedChatRoomId == null) {
            ChatRoomListScreen(
                viewModel = ChatRoomListViewModel(),
                onChatRoomSelected = { chatRoomId ->
                    selectedChatRoomId = chatRoomId
                }
            )
        } else {
            ChatScreens(
                viewModel = ChatViewModel(context),
                chatRoomId = selectedChatRoomId!!,
                onBackClick = { selectedChatRoomId = null }
            )
        }
    } else {
        // Regular user view
        if (!showChat) {
            // Show button to start chat

            // Show chat screen
            ChatScreen(
                viewModel = ChatViewModel(context),
                chatRoomId = currentUser?.uid ?: "",
                onBackClick = { showChat = false }
            )
        }
    }
}