package com.example.test.UI

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.test.*
import com.google.android.material.tabs.TabLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var imCart: ImageView
    private var dishes: ArrayList<DishesClass> = ArrayList()
    private var rViewMain: RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getWindow().setStatusBarColor(getResources().getColor(R.color.bg)) // Красим статусбар

        imCart = findViewById(R.id.imCart)
        rViewMain = findViewById(R.id.rViewMain)

        imCart.setOnClickListener{
            val intent = Intent(this@MainActivity, CartActivity::class.java)
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

                for (dish in response.body()!!){
                    Log.e("Алярм", dish.namedish)
                }
                Log.e("Алярм", "аля?") // Вывод ошибки в Logcat
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
        rViewMain?.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
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