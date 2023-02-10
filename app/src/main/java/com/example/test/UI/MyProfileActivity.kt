package com.example.test.UI

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.example.test.R

class MyProfileActivity : AppCompatActivity() {

    private lateinit var imExitInfo: ImageView // Иконка для выхода
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_profile)


        imExitInfo = findViewById(R.id.imExitInfo)
        imExitInfo.setOnClickListener {
            val intent =
                Intent(this@MyProfileActivity, ProfileActivity::class.java) // Переход в MainActivity
            startActivity(intent)
            this@MyProfileActivity.finish()
        }
    }
}