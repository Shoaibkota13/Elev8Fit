package com.fitness.elev8fit.domain.model

import android.os.Parcel
import android.os.Parcelable

data class User(
    val id :String="",
    val name:String="",
    val email:String?="",
    val mobile:String?="",
    val age:String?="",
    val photoUrl: String? = "",

):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!
    ) {
    }

    override fun describeContents() =0

    override fun writeToParcel(dest: Parcel, flag: Int)= with(dest) {
        writeString(id)
        writeString(name)
        writeString(email)
        writeString(mobile)
        writeString(age)
        writeString(photoUrl)
    }

    companion object CREATOR : Parcelable.Creator<User> {
        override fun createFromParcel(parcel: Parcel): User {
            return User(parcel)
        }

        override fun newArray(size: Int): Array<User?> {
            return arrayOfNulls(size)
        }
    }
}