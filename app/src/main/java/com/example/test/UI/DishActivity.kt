package com.example.test.UI

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.test.*
import com.squareup.picasso.Picasso
import okhttp3.ResponseBody
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
        val imCartDish: TextView = findViewById(R.id.imCartDish) // Добавить в корзину
        imHeartDish = findViewById(R.id.imHeartDish)


        val retrofitServices: RetrofitServices =
            RetrofitClient.getClient("http://79.137.206.73/") // API Сервер
                .create(RetrofitServices::class.java)

        val prefs = getSharedPreferences("token", Context.MODE_PRIVATE)
        Log.e("Алярм", prefs.getString("token", "")!!)
        Log.e("Алярм", dish_id.toString())
        imCartDish.setOnClickListener{
            retrofitServices.addcart(prefs.getString("token", "")!!.toInt(), dish_id!!.toInt()).enqueue(object: Callback<ResponseBody>{
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    Log.e("Алярм", response.code().toString())
                    Toast.makeText(this@DishActivity, "В корзине", Toast.LENGTH_LONG).show()
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Toast.makeText(this@DishActivity, "Каво", Toast.LENGTH_LONG).show()
                }

            })
        }

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