package com.fitness.elev8fit.data.repository

import com.fitness.elev8fit.data.network.ExerciseApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitBuilder {
    private const val BASE_URL = "https://exercisedb-api.vercel.app/api/v1/"

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val exerciseApi: ExerciseApi = retrofit.create(ExerciseApi::class.java)
}