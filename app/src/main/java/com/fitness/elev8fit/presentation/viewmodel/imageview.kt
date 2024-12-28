package com.fitness.elev8fit.presentation.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.fitness.elev8fit.R

class imageview: ViewModel() {
    private val _selectedimage = mutableStateOf<Int>(R.drawable.boy)
    val selectedimg: State<Int?> = _selectedimage
    private val _ageinput = mutableStateOf<Int?>(null)
    val ageinput :State<Int?> =_ageinput
    private val _nameinput = mutableStateOf<String?>("null")
    val nameinput :State<String?> = _nameinput


    fun setimg(imgres :Int){
        _selectedimage.value = imgres
        println("Image set to: $imgres")
    }

    fun setage(age:Int){
        _ageinput.value = age
    }
    fun setname(name:String){
        _nameinput.value= name
    }
}
