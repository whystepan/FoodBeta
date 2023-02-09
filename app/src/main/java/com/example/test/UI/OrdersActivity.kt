package com.example.test.UI

import CartClass
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler
import com.example.test.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OrdersActivity : AppCompatActivity() {

    private lateinit var imExitOrders: ImageView // Иконка для выхода
    private var rViewOrders: RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_orders)

        val retrofitServices: RetrofitServices =
            RetrofitClient.getClient("http://79.137.206.73/") // API Сервер
                .create(RetrofitServices::class.java)
        val prefs = getSharedPreferences("token", Context.MODE_PRIVATE)
        Log.e("Алярм", prefs.getString("token", "")!!)

        retrofitServices.getorders(prefs.getString("token", "")!!.toInt())
            .enqueue(object : Callback<ArrayList<CartClass>> {
                override fun onResponse(
                    call: Call<ArrayList<CartClass>>,
                    response: Response<ArrayList<CartClass>>
                ) {
                    rViewOrders = findViewById(R.id.rViewOrders)
                    rViewOrders?.layoutManager =
                        LinearLayoutManager(this@OrdersActivity, RecyclerView.VERTICAL, false)
                    val recyclerAdapter = RecyclerCartAdapter(response.body()!!)
                    rViewOrders?.adapter = recyclerAdapter
                }

                override fun onFailure(call: Call<ArrayList<CartClass>>, t: Throwable) {
                    Log.e("Алярм", t.message.toString())
                }
            })



        imExitOrders = findViewById(R.id.imExitOrders)
        imExitOrders.setOnClickListener {
            val intent = Intent(this@OrdersActivity, ProfileActivity::class.java) // Переход в
            startActivity(intent)
            this@OrdersActivity.finish()
        }


    }
}