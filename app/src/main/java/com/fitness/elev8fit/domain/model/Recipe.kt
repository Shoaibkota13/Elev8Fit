package com.fitness.elev8fit.domain.model

import android.os.Parcel
import android.os.Parcelable

data class Recipe(
    val id:Int=0,
    val image:String="",
    val recipeTitle :String="",
    val recipeIngredient: List<String> = emptyList(), // Assuming it's a list of ingredients
    val instructions: List<String> = emptyList(),
    val prepTime: Int=0,
    val benifits:String=""
):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.createStringArrayList()!!,
        parcel.createStringArrayList()!!,
        parcel.readInt()!!,
        parcel.readString()!!
    ) {
    }

    override fun describeContents()=0

    override fun writeToParcel(dest: Parcel,flag: Int) =with(dest) {
        writeInt(id)
        writeString(recipeTitle)
        writeString(image)
        writeInt(prepTime)
        writeList(instructions)
        writeList(recipeIngredient)
        writeString(benifits)
        TODO("Not yet implemented")
    }

    companion object CREATOR : Parcelable.Creator<Recipe> {
        override fun createFromParcel(parcel: Parcel): Recipe {
            return Recipe(parcel)
        }

        override fun newArray(size: Int): Array<Recipe?> {
            return arrayOfNulls(size)
        }
    }
}