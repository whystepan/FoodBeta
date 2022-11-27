package com.example.test

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpActivity : AppCompatActivity() {

    var token: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        val edMailSu: EditText = findViewById(R.id.edMailSu)
        val edPassSu: EditText = findViewById(R.id.edPassSu)
        val edPhoneSu: EditText = findViewById(R.id.edPhoneSu)
        val edLoginSu: EditText = findViewById(R.id.edLoginSu)
        val tvRegSu: TextView = findViewById(R.id.tvRegSu)

        val prefs = getSharedPreferences("token", Context.MODE_PRIVATE) // Чтение файла настроек

        getWindow().setStatusBarColor(getResources().getColor(R.color.bg)) // Красим статусбар

        val retrofitServices: RetrofitServices =
            RetrofitClient.getClient("http://79.137.206.73/").create(RetrofitServices::class.java)

        tvRegSu.setOnClickListener{
            if (edMailSu.text.isEmpty()||edPassSu.text.isEmpty()||edPhoneSu.text.isEmpty()||edLoginSu.text.isEmpty()){ // Проверка на пустоту полей
                Toast.makeText(this@SignUpActivity, "Не все поля заполнены", Toast.LENGTH_LONG).show()
                return@setOnClickListener // АЛЯРМ, ГАЛЯЯЯ, АТМЕНА.
            }
            if (!edMailSu.text.contains("@")){
                Toast.makeText(this@SignUpActivity, "Почта написана через очко", Toast.LENGTH_LONG).show()
                return@setOnClickListener // ГАЛЯ, АТМЕНА!!!
            }
            val user = ProfileClass(edMailSu.text.toString(), edPassSu.text.toString(), edLoginSu.text.toString(), "0", edPhoneSu.text.toString()) // Создали класс и парсим его
            val request = retrofitServices.register(user).enqueue(object:Callback<ResponseBody>{
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    if (response.code() == 400) {
                        Toast.makeText(this@SignUpActivity, "Такой юзверь уже есть.", Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(this@SignUpActivity, "Успешная регистрация", Toast.LENGTH_LONG).show()
                        token = JSONObject(response.body()!!.string()).getString("token")
                        prefs.edit().putString("token", token).apply() // Сохраняем токен
                        // Log.e("Алярм", response.body()!!.string())
                        val intent = Intent(this@SignUpActivity, MainActivity::class.java)
                        startActivity(intent)
                        this@SignUpActivity.finish()
                        Log.e("Алярм", token)
                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Toast.makeText(this@SignUpActivity, "Ошибка регистрации", Toast.LENGTH_LONG).show()
                    Log.e("Алярм", t.message.toString())
                }
            })
        }
    }
}