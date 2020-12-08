package com.example.resub.view.main

import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.ToggleButton
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.resub.R
import com.example.resub.database.RoomDB
import com.example.resub.model.UserPlanVO
import com.suke.widget.SwitchButton
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.recycler_list_item_user.view.*

class UserAdapter(private val context: Context, private var data : List<UserPlanVO>)
    : RecyclerView.Adapter<UserAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view= LayoutInflater.from(context)
            .inflate(R.layout.recycler_list_item_user, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        // 앱 아이콘
        val packageManager = context.packageManager
        val info : PackageInfo = packageManager.getPackageInfo(data[position].planVO!!.plan_app_package,0)
        try{
            holder.plan_icon.setImageDrawable(info.applicationInfo.loadIcon(packageManager))
        }catch (e : PackageManager.NameNotFoundException){
            Log.d("UserFragment","App Icon 정보 가져오기 실패")
        }

        holder.plan_name.text = data[position].planVO!!.plan_name
        holder.plan_date.text = data[position].register_date
        holder.plan_alarm.isChecked = data[position].alarm

        holder.plan_alarm.setOnCheckedChangeListener { _, isChecked ->
            val roomDB = RoomDB.getInstance(context)
            roomDB.userPlanDAO().updatePlanAlarm(isChecked)
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val plan_card : CardView = itemView.user_plan_card
        val plan_icon : ImageView = itemView.user_plan_app_icon
        val plan_name : TextView = itemView.user_plan_name
        val plan_date : TextView = itemView.user_plan_date
        val plan_alarm : SwitchButton = itemView.user_plan_toggle

    }


}