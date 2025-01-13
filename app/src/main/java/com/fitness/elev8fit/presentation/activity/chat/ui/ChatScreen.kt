@file:Suppress("DEPRECATION")

package com.fitness.elev8fit.presentation.activity.chat.ui

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieAnimatable
import com.airbnb.lottie.compose.rememberLottieComposition
import com.fitness.elev8fit.R
import com.fitness.elev8fit.domain.model.chat.Message
import com.fitness.elev8fit.presentation.activity.chat.ChatViewModel
import com.fitness.elev8fit.presentation.intent.ChatIntent
import com.fitness.elev8fit.presentation.navigation.Navdestination
import com.fitness.elev8fit.utils.NotificationHandler
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun ChatScreen(viewModel: ChatViewModel, chatRoomId: String,navController: NavController) {
//
//
//    val state by viewModel.state.collectAsState()
//    var messageText by remember { mutableStateOf("") }
//    val userId = FirebaseAuth.getInstance().currentUser?.uid ?: ""
//    val scope = rememberCoroutineScope()
//    val context = LocalContext.current
//    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.done))
//    val progress = rememberLottieAnimatable()
//
//    val notificationHandler = NotificationHandler(context)
//
//
//
//
//    // Start the animation and keep it looping while `state.isUploading` is true
//    LaunchedEffect(state.isUploading) {
//        if (state.isUploading) {
//            progress.animate(
//                composition = composition,
//                iterations = LottieConstants.IterateForever // Loops infinitely
//            )
//        } else {
//            progress.isAtEnd // Stop animation when `state.isUploading` is false
//        }
//    }
//
//
//
//    LaunchedEffect(chatRoomId) {
//        viewModel.processIntent(ChatIntent.InitializeChat(chatRoomId))
//        // Show notification when chat is opened
//    }
//
//    Scaffold(
//        topBar = {
//            TopAppBar(
//                title = { Text("Chat with Coach") },
//                colors = TopAppBarDefaults.topAppBarColors(MaterialTheme.colorScheme.primary),
//                navigationIcon = {
//
//                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", modifier = Modifier.clickable {
//                            navController.navigate(Navdestination.home.toString())
//                        })
//
//                }
//            )
//        },
//        containerColor = MaterialTheme.colorScheme.tertiary
//    ) { paddingValues ->
//        if (!state.chatInitialized) {
//            Box(
//                modifier = Modifier.fillMaxSize(),
//                contentAlignment = Alignment.Center
//            ) {
//                CircularProgressIndicator()
//            }
//        } else {
//            Column(modifier = Modifier.padding(paddingValues)) {
//                LazyColumn(
//                    modifier = Modifier
//                        .weight(1f)
//                        .fillMaxWidth(),
//                    reverseLayout = true
//                ) {
//                    items(state.messages.reversed()) { message ->
//                        ChatMessageItem(
//                            message = message,
//                            isCurrentUser = message.senderId == userId
//                        )
//                    }
//                }
//
//                MessageInput(
//                    messageText = messageText,
//                    onMessageChange = { messageText = it },
//                    onSendMessage = {
//                        if (messageText.isNotEmpty()) {
//                            viewModel.processIntent(ChatIntent.SendMessage(userId, messageText))
//                            messageText = ""
//                            notificationHandler.showSimpleNotification(chatRoomId)
//                        }
//                    },
//                    onImageSelected = { uri ->
//                        scope.launch {
//                            viewModel.processIntent(ChatIntent.UploadImage(uri, userId))
//                        }
//                    }
//                )
//            }
//
//            if (state.isUploading) {
//                Box(
//                    modifier = Modifier
//                        .fillMaxSize()
//                        .background(Color.Black.copy(alpha = 0.5f)),
//                    contentAlignment = Alignment.Center
//                ) {
//                    LottieAnimation(
//                        composition = composition,
//                        progress =  progress.progress,
//                        modifier = Modifier.size(150.dp)
//                    )
//                }
//            }
//        }
//    }
//}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(viewModel: ChatViewModel, chatRoomId: String, navController: NavController) {
    val state by viewModel.state.collectAsState()
    var messageText by remember { mutableStateOf("") }
    val userId = FirebaseAuth.getInstance().currentUser?.uid ?: ""
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.done))
    val progress = rememberLottieAnimatable()

    val notificationHandler = NotificationHandler(context)

    // Log when the state is updating
    LaunchedEffect(state) {
        Log.d("ChatScreen", "State updated: $state")
    }

    // Log when chat initialization happens
    LaunchedEffect(chatRoomId) {
        Log.d("ChatScreen", "Initializing chat for chatRoomId: $chatRoomId")
        viewModel.processIntent(ChatIntent.InitializeChat(chatRoomId))
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Chat with Coach") },
                colors = TopAppBarDefaults.topAppBarColors(MaterialTheme.colorScheme.primary),
                navigationIcon = {
                    Log.d("ChatScreen", "Back button clicked")
                    Icon(
                        Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        modifier = Modifier.clickable {
                            Log.d("Navigation", "Navigating to home: ${Navdestination.home.toString()}")
                            navController.navigate(Navdestination.home.toString())
                        }
                    )
                }
            )
        },
        containerColor = MaterialTheme.colorScheme.tertiary
    ) { paddingValues ->
        if (!state.chatInitialized) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            Column(modifier = Modifier.padding(paddingValues)) {
                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    reverseLayout = true
                ) {
                    items(state.messages.reversed()) { message ->
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
                            Log.d("ChatScreen", "Sending message: $messageText")
                            viewModel.processIntent(ChatIntent.SendMessage(userId, messageText))
                            messageText = ""
                            notificationHandler.showSimpleNotification(chatRoomId)
                        }
                    },
                    onImageSelected = { uri ->
                        scope.launch {
                            viewModel.processIntent(ChatIntent.UploadImage(uri, userId))
                        }
                    }
                )
            }
        }
    }
}




@Composable
fun ChatMessageItem(message: Message, isCurrentUser: Boolean) {
    val alignment = if (isCurrentUser) Alignment.End else Alignment.Start
    val backgroundColor = if (isCurrentUser) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary
    val textColor = if (isCurrentUser) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        horizontalArrangement = if (isCurrentUser) Arrangement.End else Arrangement.Start
    ) {
        Box(
            modifier = Modifier
                .background(backgroundColor, RoundedCornerShape(12.dp))
                .padding(8.dp)
        ) {
            Column {
                // Display image if present
                message.imageUrl?.let { imageUrl ->
                    AsyncImage(
                        model = imageUrl,
                        contentDescription = "Shared Image",
                        modifier = Modifier
                            .size(200.dp)
                            .clip(RoundedCornerShape(8.dp)),
                        contentScale = ContentScale.Crop
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                }

                // Display text if present
                if (message.text.isNotEmpty()) {
                    Text(
                        text = message.text,
                        color = textColor,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MessageInput(
    messageText: String,
    onMessageChange: (String) -> Unit,
    onSendMessage: () -> Unit,
    onImageSelected: (Uri) -> Unit
) {

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri: Uri? ->
            uri?.let { onImageSelected(it) }
        }
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Box for TextField and Add Icon
        Box(
            modifier = Modifier
                .weight(1f) // TextField takes up most of the row
                .background(MaterialTheme.colorScheme.surfaceVariant, RoundedCornerShape(16.dp))
                .padding(horizontal = 8.dp, vertical = 4.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                TextField(
                    value = messageText,
                    onValueChange = onMessageChange,
                    placeholder = { Text("Type a message...") },
                    modifier = Modifier.weight(1f), // Ensures TextField takes up most space
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color.Transparent, // Transparent background for TextField
                        cursorColor = MaterialTheme.colorScheme.primary
                    )
                )

                Spacer(modifier = Modifier.width(8.dp)) // Space between TextField and Add Icon

                IconButton(
                    onClick = { launcher.launch("image/*") },
                    modifier = Modifier
                        .size(40.dp) // Size for the Add icon

                ) {
                    Icon(
                        Icons.Default.Add,
                        contentDescription = "Add",
                        tint = Color.White
                    )
                }
            }
        }

        Spacer(modifier = Modifier.width(8.dp)) // Space between TextField section and Send Icon

        // Send Icon Button
        IconButton(
            onClick = onSendMessage,
            modifier = Modifier
                .size(48.dp) // Size for the Send icon
                .background(MaterialTheme.colorScheme.primary, CircleShape)
        ) {
            Icon(
                Icons.Default.Send,
                contentDescription = "Send",
                tint = Color.White
            )
        }
    }
}

