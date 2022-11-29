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
        val response = retrofitServices.getcart(prefs.getString("token", "")!!.toInt())
            .enqueue(object : Callback<ArrayList<CartClass>> {
                override fun onResponse(
                    call: Call<ArrayList<CartClass>>,
                    response: Response<ArrayList<CartClass>>
                ) {
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