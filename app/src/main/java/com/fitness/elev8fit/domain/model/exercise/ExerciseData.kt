package com.fitness.elev8fit.domain.model.exercise

data class ExerciseData(
    val previousPage: String,
    val nextPage: String,
    val totalPages: Int,
    val totalExercises: Int,
    val currentPage: Int,
    val exercises: List<Exercise>
)