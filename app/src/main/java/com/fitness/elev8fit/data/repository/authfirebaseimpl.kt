package com.fitness.elev8fit.data.repository

import android.util.Log
import com.fitness.elev8fit.data.constant.Constants
import com.fitness.elev8fit.domain.interfaces.authoperations
import com.fitness.elev8fit.domain.model.Recipe
import com.fitness.elev8fit.domain.model.User
import com.fitness.elev8fit.domain.model.chat.ChatRoom
import com.fitness.elev8fit.domain.model.chat.Message
import com.fitness.elev8fit.presentation.activity.Recipe.RecipeViewModel
import com.fitness.elev8fit.presentation.activity.SignUp.SignUpViewModel
import com.fitness.elev8fit.presentation.activity.socialLoginSignIn.GoogleSignInViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.SetOptions
import javax.inject.Inject

class authfirebaseimpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) : authoperations {

    companion object {
        const val COACH_ID = "oriw3fgPs4NqpN5BxfDb58Uq6532" // Hardcoded coach ID
    }

    override suspend fun registerUser(signinactivity: SignUpViewModel, userInfo: User) {
        firestore.collection(Constants.user)
            .document(getcurrentuser())
            .set(userInfo, SetOptions.merge())
            .addOnSuccessListener {
                signinactivity.onregistersucess()
            }
    }

    override suspend fun registergoogle(
        googleSignInViewModel: GoogleSignInViewModel,
        userInfo: User
    ) {
        firestore.collection(Constants.user)
            .document(getcurrentuser())
            .set(userInfo, SetOptions.merge())
            .addOnSuccessListener {
                googleSignInViewModel.onsucess()
            }
    }

    override suspend fun getcurrentuser(): String {
        return auth.currentUser?.uid ?: throw IllegalStateException("No User Found")
    }

    override suspend fun fetchCurrentUser(onUserFetched: (User?) -> Unit) {
        val userId = getcurrentuser()

        firestore.collection("Users")
            .document(userId)
            .addSnapshotListener { snapshot, e ->
                if (e != null) {
                    Log.e("Firestore", "Error fetching user data: ${e.message}")
                    onUserFetched(null)
                    return@addSnapshotListener
                }

                if (snapshot != null && snapshot.exists()) {
                    val currentUserData = snapshot.toObject(User::class.java)
                    Log.d("Firestore", "User data fetched successfully: $currentUserData")
                    onUserFetched(currentUserData)
                } else {
                    Log.d("Firestore", "No data found for user with ID: $userId")
                    onUserFetched(null)
                }
            }
    }

    override suspend fun RecipeDb(Recipe: RecipeViewModel, recipeInfo: Recipe) {
        firestore.collection(Constants.Recipe)
            .add(recipeInfo)
            .addOnSuccessListener {
                Recipe.saved()
            }
            .addOnFailureListener { e ->
                Log.e("Firestore", "Error saving recipe: ${e.message}")
            }
    }

    override suspend fun updateSingleField(field: String, value: String) {
        val currentUser = auth.currentUser
        currentUser?.let {
            val userId = it.uid
            firestore.collection("Users").document(userId)
                .update(field, value)
                .addOnSuccessListener {
                    // Optionally, fetch the updated data after update
                }
                .addOnFailureListener {
                    Log.w("ProfileViewModel", "Error updating document")
                }
        }
    }

    override suspend fun fetchExercises(offset: Int, limit: Int) {
        // Implementation for fetching exercises (if needed)
    }


    override suspend fun onNewToken(token: String) {
        // Store the new FCM token in Firestore
        val currentUser = FirebaseAuth.getInstance().currentUser ?: return
        FirebaseFirestore.getInstance()
            .collection("Users")
            .document(currentUser.uid)
            .update("fcmtoken", token)
            .addOnFailureListener { e ->
                Log.e("FCMService", "Failed to update FCM token", e)
            }
    }

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
            .whereArrayContains("users", COACH_ID)
            .addSnapshotListener { snapshot, error ->
                if (error != null || snapshot == null) {
                    onChatRoomsFetched(emptyList())
                    return@addSnapshotListener
                }

                val chatRooms = mutableListOf<ChatRoom>()
                var completedQueries = 0

                snapshot.documents.forEach { document ->
                    val usersArray = document.get("users") as? List<String> ?: emptyList()
                    val otherUserId = usersArray.firstOrNull { it != COACH_ID } ?: "Unknown User"

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
                    "users" to listOf(currentUserId, COACH_ID),
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