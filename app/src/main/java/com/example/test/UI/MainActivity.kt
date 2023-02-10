package com.example.test.UI

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.test.*
import com.google.android.material.tabs.TabLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var imCart: ImageView
    private lateinit var imProfile: ImageView
    private var dishes: ArrayList<DishesClass> = ArrayList()
    private var rViewMain: RecyclerView? = null

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getWindow().setStatusBarColor(getResources().getColor(R.color.bg)) // Красим статусбар

        imCart = findViewById(R.id.imCart)
        rViewMain = findViewById(R.id.rViewMain)
        imProfile = findViewById(R.id.imProfile)

        val imExit: ImageView = findViewById(R.id.imExit)
        val prefs = getSharedPreferences("token", Context.MODE_PRIVATE)
        imExit.setOnClickListener{
            prefs.edit().remove("token").apply()
            val intent = Intent(this@MainActivity, LaunchActivity::class.java)
            startActivity(intent)
            this@MainActivity.finish()
        }

        imProfile.setOnClickListener {
            val intent = Intent(this@MainActivity, ProfileActivity::class.java)
            intent.putExtra("token", intent.getStringExtra("token"))
            startActivity(intent)
            this@MainActivity.onResume() //?
        }

        imCart.setOnClickListener{
            val intent = Intent(this@MainActivity, CartActivity::class.java)
            intent.putExtra("token", intent.getStringExtra("token"))
            startActivity(intent)
            this@MainActivity.finish()
        }

        val retrofitServices: RetrofitServices =
            RetrofitClient.getClient("http://79.137.206.73/") // API Сервер
                .create(RetrofitServices::class.java)

        val response = retrofitServices.getDishes().enqueue(object: Callback<ArrayList<DishesClass>>{
            override fun onResponse(
                call: Call<ArrayList<DishesClass>>,
                response: Response<ArrayList<DishesClass>>
            ) {
                updateRecyclerView(filterByCategory(response.body()!!, "Еда")) // фильтр при запуске (на еду)
                dishes = response.body()!! // крафтим глобальный список продуктов
            }

            override fun onFailure(call: Call<ArrayList<DishesClass>>, t: Throwable) {
                Log.e("Алярм", t.message.toString()) // Вывод ошибки в Logcat
            }

        })

        val tbMain: TabLayout = findViewById(R.id.tbMain)

        tbMain.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                updateRecyclerView(filterByCategory(dishes, tab!!.text.toString()))
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                Log.e("Алло", "беды")
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                Log.e("Алло", "бебебе")
            }

        })

    }
    fun updateRecyclerView(dishes: ArrayList<DishesClass>){
        rViewMain?.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        val recyclerAdapter = RecyclerAdapter(dishes)
        rViewMain?.adapter = recyclerAdapter
    }
    fun filterByCategory(dishes: ArrayList<DishesClass>, category: String): ArrayList<DishesClass>{
        val newDishes: ArrayList<DishesClass> = ArrayList()
        for (dish in dishes){
            if (dish.cat == category){
                newDishes.add(dish)
            }
        }
        return newDishes
    }
}