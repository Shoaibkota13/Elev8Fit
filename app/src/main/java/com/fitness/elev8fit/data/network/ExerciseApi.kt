//package com.fitness.elev8fit.data.network
//
//import com.fitness.elev8fit.domain.model.exercise.ApiResponse
//import retrofit2.http.GET
//import retrofit2.http.Query
//
//interface ExerciseApi {
//    @GET("exercises")
//    suspend fun getExercises(
//        @Query("offset") offset: Int,
//        @Query("limit") limit: Int
//    ): ApiResponse
//}



package com.fitness.elev8fit.data.network

import com.fitness.elev8fit.domain.model.exercise.Exercise
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ExerciseApi {
    @Headers(
        "X-RapidAPI-Key: ce20668418msh943f719645dd8f8p1dcaf9jsnb78e76bf7d6b", // API key in the header
        "X-RapidAPI-Host: exercisedb.p.rapidapi.com" // Host header
    )
    @GET("exercises")
    suspend fun getExercises(
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ): List<Exercise>
}
