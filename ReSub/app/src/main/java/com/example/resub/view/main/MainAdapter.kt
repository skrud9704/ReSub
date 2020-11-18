package com.example.resub.view.main

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.resub.R
import com.example.resub.model.AppVO
import kotlinx.android.synthetic.main.recycler_list_item_main.view.*

class MainAdapter(private val context: Context, private var data : List<AppVO>)
    : RecyclerView.Adapter<MainAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view= LayoutInflater.from(context)
            .inflate(R.layout.recycler_list_item_main, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        // 마지막은 추가카드 넣기위해
        return data.size+1
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if(position!=data.size) {
            holder.app_name.text = data[position].app_label
            holder.card.setCardBackgroundColor(Color.parseColor(data[position].app_color))
        }else{
            // 카드뷰 배경 투명하게 하려면
            // 1. 투명 set
            holder.card.setCardBackgroundColor(Color.TRANSPARENT)
            // 2. elevation = 0
            holder.card.elevation = 0F

            holder.content.visibility = View.GONE
            holder.no_content.visibility = View.VISIBLE
            holder.no_content.setOnClickListener {
                Toast.makeText(context,"추가",Toast.LENGTH_SHORT).show()
            }
        }

    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val app_name : TextView = itemView.slide_item_name
        val card : CardView = itemView.slide_item_card
        val content : LinearLayout = itemView.slide_item_content
        val no_content : LinearLayout = itemView.slide_item_no_content
    }


}