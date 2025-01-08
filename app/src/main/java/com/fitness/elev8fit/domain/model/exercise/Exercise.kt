package com.fitness.elev8fit.domain.model.exercise

import com.google.gson.annotations.SerializedName


data class Exercise(
    @SerializedName("bodyPart")
    val bodyPart: String,

    @SerializedName("equipment")
    val equipment: String,

    @SerializedName("gifUrl")
    val gifUrl: String,

    @SerializedName("id")
    val id: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("target")
    val target: String,

    @SerializedName("secondaryMuscles")
    val secondaryMuscles: List<String>,

    @SerializedName("instructions")
    val instructions: List<String>
)

//package com.fitness.elev8fit.domain.model.exercise

//data class Exercise(
//    val exerciseId: String,
//    val name: String,
//    val gifUrl: String,
//    val instructions: List<String>,
//    val targetMuscles: List<String>,
//    val bodyParts: List<String>,
//    val equipments: List<String>,
//    val secondaryMuscles: List<String>
//)
