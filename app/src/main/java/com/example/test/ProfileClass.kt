package com.example.test

import com.google.gson.annotations.SerializedName

data class ProfileClass(
    @SerializedName("email") // @ - аннотация
    val email: String, // переменная

    @SerializedName("password") // @ - аннотация
    val pass: String, // переменная

    @SerializedName("login") // @ - аннотация
    val login: String, // переменная

    @SerializedName("token") // @ - аннотация
    val token: String, // переменная

    @SerializedName("phone") // @ - аннотация
    val phone: String, // переменная
)
