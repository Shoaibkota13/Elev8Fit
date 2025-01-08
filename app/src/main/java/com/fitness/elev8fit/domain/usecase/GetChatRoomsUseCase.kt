//package com.fitness.elev8fit.domain.usecase
//
//import com.fitness.elev8fit.domain.model.chat.ChatRoom
//import com.fitness.elev8fit.domain.repository.ChatRepository
//import com.fitness.elev8fit.util.Resource
//import kotlinx.coroutines.flow.Flow
//import javax.inject.Inject
//
//class GetChatRoomsUseCase @Inject constructor(
//    private val chatRepository: ChatRepository
//) {
//    operator fun invoke(): Flow<Resource<List<ChatRoom>>> {
//        return chatRepository.getChatRooms()
//    }
//}