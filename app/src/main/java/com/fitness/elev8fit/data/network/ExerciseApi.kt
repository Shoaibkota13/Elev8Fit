package com.fitness.elev8fit.data.network

import com.fitness.elev8fit.domain.model.exercise.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ExerciseApi {
    @GET("exercises")
    suspend fun getExercises(
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ): ApiResponse
}