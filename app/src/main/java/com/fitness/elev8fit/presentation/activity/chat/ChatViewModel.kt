package com.fitness.elev8fit.presentation.activity.chat

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import com.fitness.elev8fit.domain.model.chat.Message
import com.fitness.elev8fit.utils.S3Uploader
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ChatViewModel(private val context: Context) : ViewModel() {
    private val _messages = MutableStateFlow<List<Message>>(emptyList())
    val messages: StateFlow<List<Message>> = _messages
    private val firestore = FirebaseFirestore.getInstance()
    private var selectedChatRoomId: String? = null
    private val s3Uploader = S3Uploader(context)
    
    private val _isUploading = MutableStateFlow(false)
    val isUploading: StateFlow<Boolean> = _isUploading

    private val _chatInitialized = MutableStateFlow(false)
    val chatInitialized: StateFlow<Boolean> = _chatInitialized

    fun initializeChat(chatRoomId: String) {
        selectedChatRoomId = chatRoomId
        val currentUser = FirebaseAuth.getInstance().currentUser ?: return

        // Create or verify chat room
        val chatRoomRef = firestore.collection("chatrooms").document(chatRoomId)

        chatRoomRef.get().addOnSuccessListener { document ->
            if (!document.exists()) {
                // Create new chat room
                val chatRoom = hashMapOf(
                    "users" to listOf(currentUser.uid, "oriw3fgPs4NqpN5BxfDb58Uq6532"),
                    "createdAt" to System.currentTimeMillis(),
                    "lastMessage" to "",
                    "lastMessageTime" to System.currentTimeMillis()
                )

                chatRoomRef.set(chatRoom)
                    .addOnSuccessListener {
                        _chatInitialized.value = true
                        observeMessages(chatRoomId)

                        // Send notification to coach about new chat
                        firestore.collection("Users")
                            .document(currentUser.uid)
                            .get()
                            .addOnSuccessListener { userDoc ->
                                val userName = userDoc.getString("name") ?: "New User"
//                                NotificationHelper.sendNotificationToCoach(
//                                    title = "New Chat Request",
//                                    message = "$userName has started a new chat",
//                                    data = mapOf(
//                                        "chatRoomId" to chatRoomId,
//                                        "userId" to currentUser.uid,
//                                        "type" to "new_chat"
//                                    )
//                                )
                            }
                    }
            } else {
                _chatInitialized.value = true
                observeMessages(chatRoomId)
            }
        }
    }

    private fun observeMessages(chatRoomId: String) {
        firestore.collection("chatrooms")
            .document(chatRoomId)
            .collection("messages")
            .orderBy("timestamp")
            .addSnapshotListener { snapshot, e ->
                if (e != null) return@addSnapshotListener
                if (snapshot != null) {
                    _messages.value = snapshot.documents.mapNotNull { doc ->
                        doc.toObject(Message::class.java)
                    }
                }
            }
    }

    fun sendMessage(userId: String, messageText: String) {
        val chatRoomId = selectedChatRoomId ?: return
        val message = Message(
            senderId = userId,
            text = messageText,
            timestamp = System.currentTimeMillis()
        )

        firestore.collection("chatrooms")
            .document(chatRoomId)
            .collection("messages")
            .add(message)
            .addOnSuccessListener {
                // Update last message in chat room
                firestore.collection("chatrooms")
                    .document(chatRoomId)
                    .update(
                        mapOf(
                            "lastMessage" to messageText,
                            "lastMessageTime" to message.timestamp
                        )
                    )
            }
    }

    suspend fun handleImageUpload(imageUri: Uri, userId: String) {
        try {
            _isUploading.value = true
            val imageUrl = s3Uploader.uploadImage(imageUri)
            
            val message = Message(
                senderId = userId,
                text = "",
                timestamp = System.currentTimeMillis(),
                imageUrl = imageUrl
            )
            
            selectedChatRoomId?.let { chatRoomId ->
                firestore.collection("chatrooms")
                    .document(chatRoomId)
                    .collection("messages")
                    .add(message)
                    .addOnSuccessListener {
                        firestore.collection("chatrooms")
                            .document(chatRoomId)
                            .update(
                                mapOf(
                                    "lastMessage" to "📷 Image",
                                    "lastMessageTime" to message.timestamp
                                )
                            )
                    }
            }
        } finally {
            _isUploading.value = false
        }
    }
}