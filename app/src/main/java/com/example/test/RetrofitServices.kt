package com.example.test

import CartClass
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface RetrofitServices {
    @GET("dishes") // Обработка запроса на получение блюд
    fun getDishes(): Call<ArrayList<DishesClass>> // Получение списка блюд

    @POST("auth/register")
    fun register(
        @Body user: ProfileClass
    ): Call<ResponseBody>

    @POST("auth/login")
    fun login(
        @Body user: ProfileClass
    ): Call<ResponseBody>

    @GET("get_dish/{dish_id}")
    fun getdish(
        @Path("dish_id") id: Int
    ): Call<DishesClass>

    @GET("get_cart/{token}")
    fun getcart(
        @Path("token") token: Int
    ): Call<ArrayList<CartClass>>

    @POST("add_to_cart")
    fun addcart(
        @Query("user_token") token: Int,
        @Query("dish_id") id: Int
    ): Call<ResponseBody>


    @DELETE("delete_from_cart")
    fun delete(
        @Query("item_id") id: Int,
        @Query("user_token") token: Int
    ): Call<ResponseBody>

    @POST("add_to_order")
    fun addorder(
        @Body order : OrderClass
    ): Call<ResponseBody>

    @GET("order/{user_token}")
    fun getorders(
        @Path("user_token") token: Int
    ): Call<ArrayList<CartClass>>

    @DELETE("delete_from_order")
    fun delorder(
        @Query("user_token") id: Int,
    ): Call<ResponseBody>
}