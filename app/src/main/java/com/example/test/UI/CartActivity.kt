package com.example.test.UI

import CartClass
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
import com.example.test.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CartActivity : AppCompatActivity() {

    private lateinit var imLeftCart: ImageView // Иконка для выхода
    private var rViewCart: RecyclerView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        getWindow().setStatusBarColor(getResources().getColor(R.color.bg)) // Красим статусбар

        rViewCart = findViewById(R.id.rViewCart)

        imLeftCart = findViewById(R.id.imLeftCart)
        imLeftCart.setOnClickListener {
            val intent =
                Intent(this@CartActivity, MainActivity::class.java) // Переход в MainActivity
            startActivity(intent)
            this@CartActivity.finish()
        }
        val retrofitServices: RetrofitServices =
            RetrofitClient.getClient("http://79.137.206.73/") // API Сервер
                .create(RetrofitServices::class.java)

        val prefs = getSharedPreferences("token", Context.MODE_PRIVATE)
        Log.e("Алярм", prefs.getString("token", "")!!)

        val tvCartActivity: TextView = findViewById(R.id.tvCartActivity)
        tvCartActivity.setOnClickListener {
            var orderArray: IntArray = intArrayOf()
            for (dish in CartArray.cart) {
                orderArray += dish.dishId
            }
            val response =
                retrofitServices.addorder(OrderClass(prefs.getString("token", "")!!.toInt(), orderArray))
                    .enqueue(object : Callback<ResponseBody> {
                        override fun onResponse(
                            call: Call<ResponseBody>,
                            response: Response<ResponseBody>
                        ) {
                            CartArray.cart.clear()
                            updateRecyclerView(CartArray.cart)
                            Toast.makeText(this@CartActivity, "Заказ оформлен", Toast.LENGTH_LONG).show()
                        }

                        override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                            Log.e("Алярм", t.message.toString())
                        }

                    })
        }

        val response = retrofitServices.getcart(prefs.getString("token", "")!!.toInt())
            .enqueue(object : Callback<ArrayList<CartClass>> {
                override fun onResponse(
                    call: Call<ArrayList<CartClass>>,
                    response: Response<ArrayList<CartClass>>
                ) {
                    CartArray.cart = response.body()!!
                    updateRecyclerView(response.body()!!)
                }

                override fun onFailure(call: Call<ArrayList<CartClass>>, t: Throwable) {
                    Log.e("Алярм", t.message.toString())
                }

            })
    }

    fun updateRecyclerView(dishes: ArrayList<CartClass>) {
        rViewCart?.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        val recyclerAdapter = RecyclerCartAdapter(dishes)
        rViewCart?.adapter = recyclerAdapter
    }
}
