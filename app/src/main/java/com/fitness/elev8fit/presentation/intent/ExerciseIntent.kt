package com.fitness.elev8fit.presentation.intent

sealed class ExerciseIntent {
    data class Loadexcercises(val offset:Int,val limit:Int):ExerciseIntent()

}
