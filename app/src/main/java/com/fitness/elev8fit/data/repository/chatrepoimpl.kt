package com.fitness.elev8fit.data.repository

import android.util.Log
import com.fitness.elev8fit.presentation.activity.chat.domain.model.ChatRoom
import com.fitness.elev8fit.presentation.activity.chat.domain.model.Message
import com.fitness.elev8fit.presentation.activity.chat.domain.repository.chatrepo
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import javax.inject.Inject

class chatrepoimpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
): chatrepo {
    override suspend fun sendMessageToChatRoom(chatRoomId: String, message: Message) {
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
                            "lastMessage" to (message.text.ifEmpty { "📷 Image" }),
                            "lastMessageTime" to message.timestamp
                        )
                    )
            }
    }
    override suspend fun fetchChatRoomsForCoach(onChatRoomsFetched: (List<ChatRoom>) -> Unit) {
        firestore.collection("chatrooms")
            .whereArrayContains("users", authfirebaseimpl.COACH_ID)
            .addSnapshotListener { snapshot, error ->
                if (error != null || snapshot == null) {
                    onChatRoomsFetched(emptyList())
                    return@addSnapshotListener
                }

                val chatRooms = mutableListOf<ChatRoom>()
                var completedQueries = 0

                snapshot.documents.forEach { document ->
                    val usersArray = document.get("users") as? List<String> ?: emptyList()
                    val otherUserId = usersArray.firstOrNull { it != authfirebaseimpl.COACH_ID } ?: "Unknown User"

                    fetchUserName(otherUserId) { userName ->
                        chatRooms.add(
                            ChatRoom(
                                id = document.id,
                                name = userName
                            )
                        )

                        completedQueries++
                        if (completedQueries == snapshot.documents.size) {
                            onChatRoomsFetched(chatRooms)
                        }
                    }
                }
            }
    }


    override suspend fun initializeChatRoom(chatRoomId: String, currentUserId: String) {
        val chatRoomRef = firestore.collection("chatrooms").document(chatRoomId)

        chatRoomRef.get().addOnSuccessListener { document ->
            if (!document.exists()) {
                val chatRoom = hashMapOf(
                    "users" to listOf(currentUserId, authfirebaseimpl.COACH_ID),
                    "createdAt" to System.currentTimeMillis(),
                    "lastMessage" to "",
                    "lastMessageTime" to System.currentTimeMillis()
                )
                chatRoomRef.set(chatRoom)
            }
        }
    }
    override suspend fun observeChatMessages(
        chatRoomId: String,
        onMessagesUpdated: (List<Message>) -> Unit
    ) {
        firestore.collection("chatrooms")
            .document(chatRoomId)
            .collection("messages")
            .orderBy("timestamp", Query.Direction.ASCENDING)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    Log.e("Firestore", "Error observing messages: ${error.message}")
                    onMessagesUpdated(emptyList())
                    return@addSnapshotListener
                }

                val messages = snapshot?.documents?.mapNotNull { doc ->
                    doc.toObject(Message::class.java)
                } ?: emptyList()

                onMessagesUpdated(messages)
            }
    }
    private fun fetchUserName(userId: String, callback: (String) -> Unit) {
        firestore.collection("Users")
            .document(userId)
            .get()
            .addOnSuccessListener { document ->
                val userName = document.getString("name") ?: "Unknown User"
                callback(userName)
            }
            .addOnFailureListener {
                callback("Unknown User")
            }


    }
}