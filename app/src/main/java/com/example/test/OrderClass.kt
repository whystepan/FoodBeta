package com.example.test

import com.google.gson.annotations.SerializedName

data class OrderClass(
    @SerializedName("token")
    val token: Int,

    @SerializedName("cart_ids")
    val cartids: IntArray
)