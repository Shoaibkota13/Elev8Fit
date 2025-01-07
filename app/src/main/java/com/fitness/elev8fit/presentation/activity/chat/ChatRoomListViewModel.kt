package com.fitness.elev8fit.presentation.activity.chat

import androidx.lifecycle.ViewModel
import com.fitness.elev8fit.domain.model.chat.ChatRoom
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow



class ChatRoomListViewModel : ViewModel() {
    private val firestore = FirebaseFirestore.getInstance()
    private val _chatRooms = MutableStateFlow<List<ChatRoom>>(emptyList())
    val chatRooms: StateFlow<List<ChatRoom>> = _chatRooms

    private val COACH_ID = "oriw3fgPs4NqpN5BxfDb58Uq6532"  // Coach's user ID

    init {
        fetchChatRoomsForCoach()
    }

    private fun fetchChatRoomsForCoach() {
        firestore.collection("chatrooms")
            .whereArrayContains("users", COACH_ID)
            .addSnapshotListener { snapshot, error ->
                if (error != null || snapshot == null) {
                    return@addSnapshotListener
                }

                val chatRooms = mutableListOf<ChatRoom>()

                snapshot.documents.forEach { document ->
                    val usersArray = document.get("users") as? List<String> ?: emptyList()
                    val otherUserId = usersArray.firstOrNull { it != COACH_ID } ?: "Unknown User"

                    // Fetch the user's name
                    fetchUserName(otherUserId) { userName ->
                        val chatRoom = ChatRoom(
                            id = document.id,
                            name = userName // Set the fetched user name here
                        )
                        chatRooms.add(chatRoom)

                        // Update the state flow after all user names are fetched
                        if (chatRooms.size == snapshot.documents.size) {
                            _chatRooms.value = chatRooms
                        }
                    }
                }
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