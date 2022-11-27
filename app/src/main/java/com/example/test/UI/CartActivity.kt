package com.example.test.UI

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.example.test.R

class CartActivity : AppCompatActivity() {

    private lateinit var imLeftCart: ImageView // Иконка для выхода

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        getWindow().setStatusBarColor(getResources().getColor(R.color.bg)) // Красим статусбар

        imLeftCart = findViewById(R.id.imLeftCart)
        imLeftCart.setOnClickListener{
            val intent = Intent(this@CartActivity, MainActivity::class.java) // Переход в MainActivity
            startActivity(intent)
            this@CartActivity.finish()

        }
    }
}