package com.example.test.UI

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.example.test.R
import com.example.test.RetrofitClient
import com.example.test.RetrofitServices

class ProfileActivity : AppCompatActivity() {

    private lateinit var tvExit: TextView// Иконка для выхода
    private lateinit var tvOrders: TextView
    private lateinit var tvCart: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        getWindow().setStatusBarColor(getResources().getColor(R.color.bg)) // Красим статусбар

        tvExit = findViewById(R.id.tvExit)
        val prefs = getSharedPreferences("token", Context.MODE_PRIVATE)
        tvExit.setOnClickListener {
            prefs.edit().remove("token").apply()
            val intent = Intent(this@ProfileActivity, LaunchActivity::class.java)
            startActivity(intent)
            this@ProfileActivity.finish()
        }

        tvOrders = findViewById(R.id.tvOrders)
        tvOrders.setOnClickListener {
            val intent = Intent(this@ProfileActivity, OrdersActivity::class.java)
            intent.putExtra("token", intent.getStringExtra("token"))
            startActivity(intent)
            this@ProfileActivity.onResume()
        }

        tvCart = findViewById(R.id.tvCart)
        tvCart.setOnClickListener {
            val intent = Intent(this@ProfileActivity, CartActivity::class.java)
            intent.putExtra("token", intent.getStringExtra("token"))
            startActivity(intent)
            this@ProfileActivity.finish()
        }















        val retrofitServices: RetrofitServices =
            RetrofitClient.getClient("http://79.137.206.73/") // API Сервер
                .create(RetrofitServices::class.java)
        }
    }
