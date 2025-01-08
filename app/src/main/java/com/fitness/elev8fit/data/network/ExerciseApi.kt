//package com.fitness.elev8fit.data.network
//
//import com.fitness.elev8fit.domain.model.exercise.ApiResponse
//import retrofit2.http.GET
//import retrofit2.http.Query
//
//
//interface ExerciseApi {
//    @GET("exercises")
//    suspend fun getExercises(
//        @Query("offset") offset: Int,
//        @Query("limit") limit: Int
//    ): ApiResponse
//}
//


package com.fitness.elev8fit.data.network

import com.fitness.elev8fit.domain.model.exercise.Exercise
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ExerciseApi {
    @Headers(
        "x-rapidapi-key: 06f1743368mshc90894eb6dbd178p144d9ejsnd899d00a1e5b", // API key in the header
        "X-RapidAPI-Host: exercisedb.p.rapidapi.com" // Host header
    )
    @GET("exercises")
    suspend fun getExercises(
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ): List<Exercise>
}
