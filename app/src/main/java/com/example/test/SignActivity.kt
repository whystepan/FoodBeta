package com.example.test

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import okhttp3.ResponseBody
import org.json.JSONObject
import org.w3c.dom.Text
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignActivity : AppCompatActivity() {

    private lateinit var tvRegSi: TextView
    var token: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign)

        val edSignEmail: EditText = findViewById(R.id.edSignEmail)
        val edSignPass: EditText = findViewById(R.id.edSignPass)
        val tvLoginSi: TextView = findViewById(R.id.tvLoginSi)
        tvRegSi = findViewById(R.id.tvRegSi)

        val prefs = getSharedPreferences("token", Context.MODE_PRIVATE)

        getWindow().setStatusBarColor(getResources().getColor(R.color.bg)) // Красим статусбар

        val retrofitServices: RetrofitServices =
            RetrofitClient.getClient("http://79.137.206.73/") // Ставим APIшку
                .create(RetrofitServices::class.java)

        tvLoginSi.setOnClickListener {
            if (edSignEmail.text.isEmpty() || edSignPass.text.isEmpty()) { // Проверка на пустоту полей
                Toast.makeText(this@SignActivity, "Не все поля заполнены", Toast.LENGTH_LONG).show()
                return@setOnClickListener // АЛЯРМ, ГАЛЯЯЯ, АТМЕНА.
            }
            if (!edSignEmail.text.contains("@")){ // Проверка на наличие символа @ (поле - Email)
                Toast.makeText(this@SignActivity, "Почта написана через очко", Toast.LENGTH_LONG).show()
                return@setOnClickListener // ГАЛЯ, АТМЕНА!!!
            }
            val user = ProfileClass(edSignEmail.text.toString(), edSignPass.text.toString(), "", "0", "") // Создали класс и парсим его
            val request = retrofitServices.login(user).enqueue(object: Callback<ResponseBody> {
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    // Log.e("Алярм", response.body()!!.string()) // Вывод ошибки в Logcat
                    if (response.code() == 400) {
                        Toast.makeText(this@SignActivity, "Хуйня кароче", Toast.LENGTH_LONG).show()
                    } else {
                        token = JSONObject(response.body()!!.string()).getString("token")
                        prefs.edit().putString("token", token).apply() // Сохраняем токен
                        Log.e("Алярм", response.body()!!.string()) // Вывод ошибки в Logcat
                        val intent = Intent(this@SignActivity, MainActivity::class.java)
                        startActivity(intent)
                        this@SignActivity.finish()
                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Toast.makeText(this@SignActivity, "Ошибка входа", Toast.LENGTH_LONG).show() // Вывод ошибки
                    Log.e("Алярм", t.message.toString()) // Вывод ошибки в Logcat
                }
            })
        }
    }
        override fun onResume() {
            super.onResume()
            val tvRegSi: TextView = findViewById(R.id.tvRegSi)
            tvRegSi.setOnClickListener {
                val intent = Intent(this@SignActivity, SignUpActivity::class.java)
                startActivity(intent)
            }
        }
    }
