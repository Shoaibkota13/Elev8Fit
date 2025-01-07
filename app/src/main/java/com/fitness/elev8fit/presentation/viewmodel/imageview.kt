package com.fitness.elev8fit.presentation.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.fitness.elev8fit.R

class imageview: ViewModel() {
    private val _selectedimage = mutableStateOf<Int>(R.drawable.boy)
    val selectedimg: State<Int> = _selectedimage

    fun setimg(imgres :Int){
        _selectedimage.value = imgres
        println("Image set to: $imgres")
    }

}


class imageviews: ViewModel() {
    private val _selectedimage = mutableStateOf<Int>(R.drawable.boy)
    val selectedimg: State<Int> = _selectedimage

    fun setimg(imgres :Int){
        _selectedimage.value = imgres
        println("Image set to: $imgres")
    }

}

