package com.example.resub.view

import android.content.Context
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.resub.R
import kotlinx.android.synthetic.main.recycler_list_item_app.view.*

class TestAdapter(private val packageManager: PackageManager, private val context : Context,
                  private var data:List<ResolveInfo>) : RecyclerView.Adapter<TestAdapter.ViewHolder>() {
    init{
        sortData()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.recycler_list_item_app,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val icon = data[position].activityInfo.loadIcon(packageManager)
        holder.app_img.setImageDrawable(icon)
        holder.app_name.text = data[position].loadLabel(packageManager)
        holder.app_info.text = data[position].activityInfo.packageName
    }

    private fun sortData(){
        // 나중에.
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val app_name : TextView = itemView.item_app_name
        val app_info : TextView = itemView.item_app_info
        val app_img : ImageView = itemView.item_app_img
    }

}