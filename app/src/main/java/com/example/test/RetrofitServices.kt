package com.example.test

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

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
}