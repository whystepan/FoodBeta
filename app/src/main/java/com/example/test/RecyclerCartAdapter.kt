package com.example.test

import CartClass
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.test.UI.DishActivity
import com.example.test.UI.OrdersActivity
import com.squareup.picasso.Picasso
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RecyclerCartAdapter(private val cart: ArrayList<CartClass>): RecyclerView.Adapter<RecyclerCartAdapter.ViewHolder>(){

    class ViewHolder (cartItemView: View) : RecyclerView.ViewHolder(cartItemView){
        val tvNameCart: TextView = cartItemView.findViewById(R.id.tvNameCart)
        val tvPriceCart: TextView = cartItemView.findViewById(R.id.tvPriceCart)
        val imCartItem: ImageView = cartItemView.findViewById(R.id.imCartItem)
        val imCartDel: ImageView = cartItemView.findViewById(R.id.imCartDel)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerCartAdapter.ViewHolder {
        val cartItemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.cart_item, parent, false)
        val imCartDel: ImageView = cartItemView.findViewById(R.id.imCartDel)
       // Log.e("Алярм", parent.context::class.java.toString())
       // Log.e("Алярм", OrdersActivity::class.java.toString())
        if (imCartDel.context::class.java == OrdersActivity::class.java){
            imCartDel.visibility = View.GONE
        } else {
            imCartDel.visibility = View.VISIBLE
        }
        imCartDel.setOnClickListener{
            val retrofitServices: RetrofitServices =
                RetrofitClient.getClient("http://79.137.206.73/") // API Сервер
                    .create(RetrofitServices::class.java)
            val tvNameCart: TextView = cartItemView.findViewById(R.id.tvNameCart)
            retrofitServices.delete(it.tag.toString().toInt(), tvNameCart.tag.toString().toInt()).enqueue(object: Callback<ResponseBody>{
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    cartItemView.visibility = View.GONE
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    TODO("Not yet implemented")
                }

            })
        }
        return ViewHolder(cartItemView)
    }

    override fun onBindViewHolder(holder: RecyclerCartAdapter.ViewHolder, position: Int) {
        val dish = cart[position]
        holder.tvPriceCart.text = "${dish.pr.toString()} руб."
        holder.tvNameCart.text = dish.namedish
        holder.imCartItem.clipToOutline = true
        holder.imCartDel.tag = dish.id
        holder.tvNameCart.tag = dish.token
        Picasso.get().load(dish.ic).error(R.drawable.noimage).into(holder.imCartItem)
    }

    override fun getItemCount() = cart.size
    }