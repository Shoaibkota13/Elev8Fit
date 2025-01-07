package com.fitness.elev8fit.presentation.activity.chat


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreens(viewModel: ChatViewModel, chatRoomId: String, onBackClick: () -> Unit) {
    val messages by viewModel.messages.collectAsState()
    val chatInitialized by viewModel.chatInitialized.collectAsState()
    var messageText by remember { mutableStateOf("") }
    val userId = FirebaseAuth.getInstance().currentUser?.uid ?: ""
    val scope = rememberCoroutineScope()
    val isUploading by viewModel.isUploading.collectAsState()

    LaunchedEffect(chatRoomId) {
        viewModel.initializeChat(chatRoomId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Chat with User") },
                colors = TopAppBarDefaults.topAppBarColors(MaterialTheme.colorScheme.primary),
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        if (!chatInitialized) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            Column(modifier = Modifier.padding(paddingValues)) {
                // Rest of the chat UI implementation...
                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    reverseLayout = true
                ) {
                    items(messages.reversed()) { message ->
                        ChatMessageItem(
                            message = message,
                            isCurrentUser = message.senderId == userId
                        )
                    }
                }

                MessageInput(
                    messageText = messageText,
                    onMessageChange = { messageText = it },
                    onSendMessage = {
                        if (messageText.isNotEmpty()) {
                            viewModel.sendMessage(userId, messageText)
                            messageText = ""
                        }
                    },
                    onImageSelected =
                     { uri ->
                        scope.launch {
                            viewModel.handleImageUpload(uri, userId)
                        }
                    }
                )
            }
            if (isUploading) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.5f)),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}

