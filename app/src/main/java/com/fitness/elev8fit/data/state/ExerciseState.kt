package com.fitness.elev8fit.data.state

import com.fitness.elev8fit.domain.model.exercise.Exercise


data class ExceriseState(
    val isLoading: Boolean = false,
    val successMessage: String? = null,
    val errorMessage: String? = null,
    val exerciseList: List<Exercise> = emptyList()
)