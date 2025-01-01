package com.fitness.elev8fit.domain.repository


import com.fitness.elev8fit.data.repository.RetrofitBuilder
import com.fitness.elev8fit.domain.model.exercise.ApiResponse

class ExerciseRepository {
    suspend fun getExercises(offset: Int, limit: Int): Result<ApiResponse> {
        return try {
            val response = RetrofitBuilder.exerciseApi.getExercises(offset, limit)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}