package com.example.test

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.test.UI.DishActivity
import com.squareup.picasso.Picasso

class RecyclerAdapter (private val dishes: List<DishesClass>):RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val tvNameItem: TextView = itemView.findViewById(R.id.tvNameItem)
        val tvRubItem: TextView = itemView.findViewById(R.id.tvRubItem)
        val imItem: ImageView = itemView.findViewById(R.id.imItem)
        val bgItem: ConstraintLayout = itemView.findViewById(R.id.bgItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item, parent, false)

        val bgItem: ConstraintLayout = itemView.findViewById(R.id.bgItem)
        bgItem.setOnClickListener{
            val intent = Intent(parent.context, DishActivity::class.java)
            intent.putExtra("id", bgItem.tag.toString())
            parent.context.startActivity(intent)
        }
        return ViewHolder(itemView)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dish = dishes[position]
        holder.bgItem.tag = dish.id
        holder.tvRubItem.text = "${dish.pr.toString()} руб."
        holder.tvNameItem.text = dish.namedish
        holder.imItem.clipToOutline = true
        Picasso.get().load(dish.ic).error(R.drawable.noimage).into(holder.imItem)
    }

    override fun getItemCount() = dishes.size
}