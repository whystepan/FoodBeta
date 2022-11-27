package com.example.test.UI

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.example.test.*
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DishActivity : AppCompatActivity() {

    private lateinit var imLeftDish: ImageView // Иконка для выхода
    private lateinit var imHeartDish: ImageView // Иконка избранных

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dish)

        getWindow().setStatusBarColor(getResources().getColor(R.color.bg)) // Красим статусбар

        val dish_id = intent.getStringExtra("id")

        val imItemDish: ImageView = findViewById(R.id.imItemDish) // Пикча
        val tvNameDish: TextView = findViewById(R.id.tvNameDish) // Навзание блюда
        val tvPriceDish: TextView = findViewById(R.id.tvPriceDish) // Ценник
        val tvDescDish: TextView = findViewById(R.id.tvDescDish) // Описание
        imHeartDish = findViewById(R.id.imHeartDish)

        val retrofitServices: RetrofitServices =
            RetrofitClient.getClient("http://79.137.206.73/") // API Сервер
                .create(RetrofitServices::class.java)

        retrofitServices.getdish(dish_id!!.toInt()).enqueue(object: Callback<DishesClass>{
            override fun onResponse(call: Call<DishesClass>, response: Response<DishesClass>) {
                val dish = response.body()!!
                tvNameDish.text = dish.namedish
                tvPriceDish.text = "${dish.pr} руб."
                tvDescDish.text = dish.desc
                imItemDish.clipToOutline = true
                Picasso.get().load(dish.ic).error(R.drawable.noimage).into(imItemDish)
            }

            override fun onFailure(call: Call<DishesClass>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })


        imLeftDish = findViewById(R.id.imLeftDish)
        imLeftDish.setOnClickListener {
            val intent =
                Intent(this@DishActivity, MainActivity::class.java) // Переход в MainActivity
            startActivity(intent)
            this@DishActivity.finish()
        }
    }
}