package com.example.resub.view.register

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.resub.R
import com.example.resub.model.PlanVO
import kotlinx.android.synthetic.main.recycler_list_item_plan_horizontal.view.*

class PlanAdapter(private val context: Context, private val data: ArrayList<PlanVO>, private val mPlanClickListener : OnPlanItemClick)
    : RecyclerView.Adapter<PlanAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlanAdapter.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.recycler_list_item_plan_horizontal,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: PlanAdapter.ViewHolder, position: Int) {
        holder.plan_name.text = data[position].plan_name
        holder.plan_charge.text = data[position].plan_charge
        holder.plan_period.text = data[position].plan_period
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val plan_card : CardView = itemView.item_plan_card
        val plan_name : TextView = itemView.item_plan_name
        val plan_charge : TextView = itemView.item_plan_charge
        val plan_period : TextView = itemView.item_plan_period

        init{
            plan_card.setOnClickListener {
                plan_card.setBackgroundColor(context.getColor(R.color.lightPurple))
                plan_card.radius = 8f

                mPlanClickListener.onClick(data[adapterPosition])
            }
        }

    }

}