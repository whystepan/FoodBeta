package com.example.test.UI

import CartClass
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler
import com.example.test.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OrdersActivity : AppCompatActivity() {

    private lateinit var imExitOrders: ImageView // Иконка для выхода
    private var rViewOrders: RecyclerView? = null

    @SuppressLint("MissingInflatedId")
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

        val tvOrderDel: TextView = findViewById(R.id.tvOrderDel)
        tvOrderDel.setOnClickListener {
            retrofitServices.delorder(prefs.getString("token", "")!!.toInt())
                .enqueue(object : Callback<ResponseBody>{
                    override fun onResponse(
                        call: Call<ResponseBody>,
                        response: Response<ResponseBody>
                    ) {
                        CartArray.cart.clear()
                        rViewOrders = findViewById(R.id.rViewOrders)
                        rViewOrders?.layoutManager =
                            LinearLayoutManager(this@OrdersActivity, RecyclerView.VERTICAL, false)
                        val recyclerAdapter = RecyclerCartAdapter(CartArray.cart)
                        rViewOrders?.adapter = recyclerAdapter
                        Toast.makeText(this@OrdersActivity, "Заказ успешно оформлен. Ожидайте сообщения от оператора.", Toast.LENGTH_LONG).show()
                    }

                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        Toast.makeText(this@OrdersActivity, "Проблема с оформлением заказа. Обратитесь к оператору.", Toast.LENGTH_LONG).show()
                    }

                })
            }

        imExitOrders = findViewById(R.id.imExitOrders)
        imExitOrders.setOnClickListener {
            val intent = Intent(this@OrdersActivity, ProfileActivity::class.java) // Переход в
            startActivity(intent)
            this@OrdersActivity.finish()
        }
    }
}