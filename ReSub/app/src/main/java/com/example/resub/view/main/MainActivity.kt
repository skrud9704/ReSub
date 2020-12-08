package com.example.resub.view.main

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.example.resub.R
import com.example.resub.database.RoomDB
import com.example.resub.view.TestActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.text.DecimalFormat

class MainActivity : FragmentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setMonthlyCharge()
        setFragment()
        setBottomNavigation()
        btn_menu.setOnClickListener {
            val nextIntent = Intent(this,TestActivity::class.java)
            startActivity(nextIntent)
        }

    }

    @SuppressLint("SetTextI18n")
    private fun setMonthlyCharge(){
        val roomDB  = RoomDB.getInstance(this)
        val plans = roomDB.userPlanDAO().getPlanList()
        var totalCharge : Int = 0

        for(plan in plans){
            val app = roomDB.appDAO().getApp(plan.planVO!!.plan_app_package)
            // 무료체험 기간도 없고, 할인 기간도 없음
            if(app.app_free!=1 && app.app_discount!=1){
                val charge = plan.planVO!!.plan_charge.replace(",","")
                totalCharge += charge.toInt()
            }
            // 무료체험 기간은 없는데, 할인 기간은 있음
            else if(app.app_free!=1 && app.app_discount==1){
                // 할인 기간가격을 3900원이라 가정.
                totalCharge += 3900
            }
        }

        val dec = DecimalFormat("#,###")
        this_month_cost.text = "${dec.format(totalCharge)} 원"
    }

    private fun setFragment(){
        replaceFragment(HomeFragment())

        custom_bottom_nav.setOnNavigationItemSelectedListener { item->
            when(item.itemId){
                R.id.action_home->{
                    replaceFragment(HomeFragment())
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.action_user->{
                    replaceFragment(UserFragment())
                    return@setOnNavigationItemSelectedListener true
                }
                else ->{
                    return@setOnNavigationItemSelectedListener false
                }
            }
        }
    }

    private fun replaceFragment(fragment: Fragment){
        val fragmentTransaction  = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout,fragment)
        fragmentTransaction.commit()

    }

    private fun setBottomNavigation(){
        custom_bottom_nav.inflateMenu(R.menu.bottom_navigation_menu)
    }


}