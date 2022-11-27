package com.example.test

import com.google.gson.annotations.SerializedName

data class DishesClass(
    @SerializedName("id") // @ - Аннотация
    val id: Int, // Переменная

    @SerializedName("category")
    val cat: String,

    @SerializedName("name")
    val namedish: String,

    @SerializedName("description")
    val desc: String,

    @SerializedName("price")
    val pr: Float,

    @SerializedName("img_url")
    val ic: String
    )
