package com.example.resub.view.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.resub.R
import com.example.resub.database.RoomDB
import kotlinx.android.synthetic.main.fragment_user.*

class UserFragment : Fragment(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_user, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val roomDB = RoomDB.getInstance(activity!!.applicationContext)
        user_plan_list.adapter = UserAdapter(activity!!.applicationContext, roomDB.userPlanDAO().getPlanList())

    }
}