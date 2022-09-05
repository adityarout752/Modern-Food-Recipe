package com.example.food_recipe.foodrecipe.model


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ExtendedIngredient(
        @SerializedName("amount")
        val amount: Double,
        @SerializedName("consistency")
        val consistency: String,
        @SerializedName("image")
        val image: String?= null,
        @SerializedName("name")
        val name: String,
        @SerializedName("original")
        val original: String,
        @SerializedName("unit")
        val unit: String
):Parcelable