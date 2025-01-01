package com.fitness.elev8fit.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fitness.elev8fit.data.state.ExceriseState
import com.fitness.elev8fit.domain.repository.ExerciseRepository
import com.fitness.elev8fit.presentation.intent.ExerciseIntent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ExerciseViewModel : ViewModel() {
    private val repository = ExerciseRepository()
    private val _state = MutableStateFlow(ExceriseState())
    val state: StateFlow<ExceriseState> = _state



    fun handleAPIintent(intent: ExerciseIntent){
        when(intent){
            is ExerciseIntent.Loadexcercises-> fetchExercises(intent.offset,intent.limit)
        }

    }

    fun fetchExercises(offset: Int, limit: Int) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)

            repository.getExercises(offset, limit)
                .onSuccess { response ->
                        val data = response.data
                    Log.d("ExerciseViewModel", "API call successful: ${response.data}")
                    _state.value = _state.value.copy(
                        isLoading = false,
                      // Pass the full list of Exercise objects
                        successMessage = "Exercises loaded successfully",
                        exerciseList = data.exercises
                    )
                }
                .onFailure { error ->
                    _state.value = _state.value.copy(errorMessage = "byee")
                }
        }
    }
}