package com.fitness.elev8fit.data.state

data class RecipeState(
    var id:Int=0,
    var recipetitle :String="",
    var  recipeIngredient :List<String> = emptyList(),
    var image:Int=0,
    var instruction :List<String> = emptyList(),
    var benfits : String="",
    var prepTime:String="",
    var category :String="",
    val isLoading: Boolean = false,
    val successMessage: String? = null,
    val errorMessage: String? = null
)
