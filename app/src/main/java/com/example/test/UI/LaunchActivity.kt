package com.example.test.UI

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import com.example.test.R

class LaunchActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launch)

        getWindow().setStatusBarColor(getResources().getColor(R.color.bg)) // Красим статусбар

        val prefs = getSharedPreferences("token", Context.MODE_PRIVATE) // Чтение файла настроек

        object:CountDownTimer(1500, 1000){
            override fun onTick(p0: Long) {
            }

            override fun onFinish() {
                val token = prefs.getString("token", "")
                Log.e("Алярм", token.toString())
                if (token == "") {
                    val intent = Intent(this@LaunchActivity, SignActivity::class.java)
                    startActivity(intent)
                    this@LaunchActivity.finish()
                } else {
                    val intent = Intent(this@LaunchActivity, MainActivity::class.java) // кидаем моменталочку в main
                    intent.putExtra("token", token)
                    startActivity(intent)
                    this@LaunchActivity.finish()
                }
            }
        }.start()
    }
}