package com.fitness.elev8fit.domain.interfaces

import com.fitness.elev8fit.domain.model.Recipe
import com.fitness.elev8fit.domain.model.User
import com.fitness.elev8fit.domain.model.chat.ChatRoom
import com.fitness.elev8fit.domain.model.chat.Message
import com.fitness.elev8fit.presentation.activity.Recipe.RecipeViewModel
import com.fitness.elev8fit.presentation.activity.SignUp.SignUpViewModel
import com.fitness.elev8fit.presentation.activity.socialLoginSignIn.GoogleSignInViewModel

interface authoperations {

    suspend fun registerUser(signinactivity: SignUpViewModel, userInfo: User)

    suspend fun registergoogle(googleSignInViewModel: GoogleSignInViewModel, userInfo: User)

    suspend fun getcurrentuser(): String

    suspend fun fetchCurrentUser(onFetch: (User?) -> Unit)

    suspend fun RecipeDb(Recipe: RecipeViewModel, recipeInfo: Recipe)

    suspend fun updateSingleField(field: String, value: String)

    suspend fun fetchExercises(offset: Int, limit: Int)

  suspend fun onNewToken(token: String)
    suspend fun sendMessageToChatRoom(chatRoomId: String, message: Message)
    suspend fun fetchChatRoomsForCoach(onChatRoomsFetched: (List<ChatRoom>) -> Unit)
    suspend fun initializeChatRoom(chatRoomId: String, currentUserId: String)
    suspend fun observeChatMessages(chatRoomId: String, onMessagesUpdated: (List<Message>) -> Unit)

}
