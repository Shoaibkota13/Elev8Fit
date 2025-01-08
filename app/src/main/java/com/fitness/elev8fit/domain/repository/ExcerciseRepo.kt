package com.fitness.elev8fit.domain.repository

import RetrofitBuilder
import com.fitness.elev8fit.domain.model.exercise.Exercise

class ExerciseRepository {
    suspend fun getExercises(offset: Int, limit: Int): Result<List<Exercise>> {
        return try {
            val response = RetrofitBuilder.exerciseApi.getExercises(offset, limit)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
//
//package com.fitness.elev8fit.domain.repository
//
//
//import com.fitness.elev8fit.domain.model.exercise.Exercise
//
//class ExerciseRepository {
//    suspend fun getExercises(offset: Int, limit: Int): Result<List<Exercise>> {
//        return try {
//            val response = RetrofitBuilder.exerciseApi.getExercises(offset, limit)
//            if (response.success) {
//                Result.success(response.data.exercises) // Extract the list of exercises
//            } else {
//                Result.failure(Exception("Failed to fetch exercises"))
//            }
//        } catch (e: Exception) {
//            Result.failure(e)
//        }
//    }
//}
